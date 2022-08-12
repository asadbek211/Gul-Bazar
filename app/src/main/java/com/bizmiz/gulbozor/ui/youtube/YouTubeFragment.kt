package com.bizmiz.gulbozor.ui.youtube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentYouTubeBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class YouTubeFragment : Fragment() {

    private val slideModels: ArrayList<SlideModel> = ArrayList()

    private val youTubeVM: YouTubeViewModel by viewModel()
    val args: YouTubeFragmentArgs by navArgs()
    private var page: Int = 0
    private var isLastPage = false

    private lateinit var adapter: YouTubeAdapter
    private var _binding: FragmentYouTubeBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        youTubeVM.getYouTubePage(page)
        youTubeVM.getReklamaImages(2)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYouTubeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basic()
        windowStatus()
        setListeners()
        announceObserve()
        swipeScroll()


    }

    private fun swipeScroll() {
        binding.swipeContainer.setOnRefreshListener {
            adapter.clearAdapter()
            youTubeVM.getYouTubePage(0)
            isLastPage = false
        }

        binding.scrollNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (!isLastPage) {
                    page++
                    binding.progressBarHome.visibility = View.VISIBLE
                    youTubeVM.getYouTubePage(page)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Boshqa videolar mavjud emas",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBarHome.visibility = View.GONE
                }

            }
        })
    }

    private fun basic() {
        windowStatus()
        binding.categoryType.text = args.title

        adapter = YouTubeAdapter()
        binding.youtubeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.youtubeRecyclerView.adapter = adapter

        adapter.MyAdapter(requireActivity(), binding.categoryType.context)
    }

    private fun setListeners() {
        binding.onBackYouTube.setOnClickListener(View.OnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_activity_main
                )
            navController.popBackStack()
        })
        binding.categoryU.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.youtube_to_category)
        })


    }

    private fun announceObserve() {
        youTubeVM.announcePage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    binding.swipeContainer.isRefreshing = false
                    adapter.addYouTubeListData(it.data?.content!!)
                    binding.progressBarHome.visibility = View.GONE
                    if (it.data.empty) {
                        isLastPage = true
                    }
                }
                ResourceState.ERROR -> {
                    binding.swipeContainer.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceState.LOADING -> {

                }
            }
        })
        youTubeVM.getReklamaId.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    binding.swipeContainer.isRefreshing = false
                    slideModels.clear()
                    slideModels.add(SlideModel(it.data!!.`object`.image1, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image2, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image3, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image4, ScaleTypes.FIT))
                    slideModels.add(SlideModel(it.data.`object`.image5, ScaleTypes.FIT))
                    binding.imageSlider.setImageList(slideModels, ScaleTypes.FIT)
                }
                ResourceState.ERROR -> {
                    binding.swipeContainer.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), com.bizmiz.gulbozor.R.color.white)
    }


}