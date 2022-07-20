package com.bizmiz.gulbozor.ui.youtube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.Content
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentYouTubeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class YouTubeFragment : Fragment() {

    private val youTubeVM: YouTubeViewModel by viewModel()
    val args: YouTubeFragmentArgs by navArgs()

    private lateinit var adapter: YouTubeAdapter
    private var _binding: FragmentYouTubeBinding? = null
    private val binding get() = _binding!!

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
        binding.categoryType.text = args.title

        youTubeVM.getYouTubePage()
        adapter = YouTubeAdapter()
        binding.youtubeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.youtubeRecyclerView.adapter = adapter

        windowStatus()
        setListeners()
        announceObserve()

    }

    private fun setListeners() {
        binding.onBackYouTube.setOnClickListener(View.OnClickListener {
            if (args.title == "Barchasi") {
                findNavController().navigate(R.id.nav_on_back_youtube_to_home)
            } else {
                findNavController().navigate(R.id.youtube_to_category)
            }
        })
        binding.categoryU.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.youtube_to_category)
        })
    }

    private fun announceObserve() {
        youTubeVM.announcePage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    adapter.youTubeList = (it.data?.content as ArrayList<Content>?)!!
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                ResourceState.LOADING -> {

                }
            }
        })
    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), com.bizmiz.gulbozor.R.color.gray_main)
    }

}