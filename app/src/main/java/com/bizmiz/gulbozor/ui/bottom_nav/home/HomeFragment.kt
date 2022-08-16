package com.bizmiz.gulbozor.ui.bottom_nav.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentHomeBinding
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var flowersAdapter: FlowersAdapter
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }
    private val slideModels: ArrayList<SlideModel> = ArrayList()
    private var currentPage: Int = 0
    private var totalPage:Int = 0
    //private var totalAvailablePages=0

    private var isLoading = false
    private var isLastPage = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getAnnounce(currentPage)
        homeViewModel.getVideoLInkByID(2)
        homeViewModel.getReklamaImages(1)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basic()
        setListeners(view)
        announceObserve()
        onlyData()
        scrollSwipe()

        //onScrolled()

        flowersAdapter.onClickListener {
            if (it.department != null) {
                val bundle = bundleOf(
                    "flowerData" to it,
                    "desId" to 0,
                )
                destination(it.department, bundle)
            }
        }
//        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayerView)

    }

    private fun onScrolled() {

        val mLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.homeRecyclerview.layoutManager = mLayoutManager
        var loading = true
        var pastVisibleItems = 0
        var visibleItemCount: Int
        var totalItemCount: Int

        binding.homeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager.childCount
                    totalItemCount = mLayoutManager.itemCount
                    val positions = mLayoutManager.findFirstVisibleItemPositions(null)
                    if (positions != null && positions.size > 0) pastVisibleItems = positions[0]

                    if (loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading = false
                            Toast.makeText(requireContext(), "Paging", Toast.LENGTH_SHORT).show()
                            loading = true
                        }
                    }
                }
            }
        })
    }

    private fun scrollSwipe() {
        binding.swipeContainer.setOnRefreshListener {
            flowersAdapter.clearAdapter()
            currentPage = 0
            homeViewModel.getAnnounce(currentPage)
            homeViewModel.getVideoLInkByID(2)
            isLastPage = false
        }
        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY >= v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (!isLastPage && currentPage + 1 < totalPage) {
                    binding.progressBarHome.visibility = View.VISIBLE
                    homeViewModel.getAnnounce(currentPage + 1)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Boshqa elonlar mavjud emas",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBarHome.visibility = View.GONE
                }

            }
        })

    }

    private fun destination(categoryId: Int, bundle: Bundle) {
        val navController =
            Navigation.findNavController(
                requireActivity(),
                R.id.mainContainer
            )
        when (categoryId) {
            1 -> {
                navController.navigate(R.id.home_to_buketDetails, bundle)
            }
            2 -> {
                navController.navigate(R.id.home_to_flowerDetails, bundle)
            }
            3 -> {
                navController.navigate(R.id.home_to_treeDetails, bundle)
            }
            4 -> {
                navController.navigate(R.id.home_to_potDetails, bundle)
            }
            5 -> {
                navController.navigate(R.id.home_to_fetilizersDetails, bundle)
            }
        }

    }


    private fun setListeners(view: View) {

        binding.youtubeOthers.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.navHomeToYouTube("Barchasi", "home")
            Navigation.findNavController(view).navigate(action)
        })
        binding.categoryWithBucket.setOnClickListener {
            val action = HomeFragmentDirections.homeToOne("Buket gullar", "home", "1")
            Navigation.findNavController(view).navigate(action)
        }
        binding.homeMadeFlowerCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToOne("Xonaki gullar", "home", "2")
            Navigation.findNavController(view).navigate(action)
        })
        binding.treeFlowerCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToOne("Daraxtlar", "home", "3")
            Navigation.findNavController(view).navigate(action)
        })
        binding.potFlowerCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToOne("Tuvak va o'g'itlar", "home", "5")
            Navigation.findNavController(view).navigate(action)
        })
        binding.customersCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToCustomers("customer")
            Navigation.findNavController(view).navigate(action)
        })
        binding.shopsCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToShop("home")
            Navigation.findNavController(view).navigate(action)
        })

    }




    private fun onImageClick() {
//        binding.imgYoutube.setOnClickListener {
            homeViewModel.getVideoLInkID.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.SUCCESS -> {
                        binding.swipeContainer.isRefreshing = false
                        binding.progressBarYu.visibility = View.GONE
                        binding.youtubePlayerView.apply {
                            settings.pluginState = WebSettings.PluginState.ON
                            webChromeClient = WebChromeClient()
                            settings.javaScriptEnabled = true
                            settings.setAppCacheEnabled(true)
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.loadWithOverviewMode = true
                            settings.builtInZoomControls = true
                            settings.displayZoomControls  =true
                            settings.loadsImagesAutomatically = true
                            settings.setSupportZoom(true)
                            settings.domStorageEnabled = true
                            loadUrl("https://www.youtube.com/embed/${it.data?.`object`?.videoLink}")
                        }
                    }
                    ResourceState.ERROR -> {
                        binding.progressBarYu.visibility = View.GONE
                        binding.swipeContainer.isRefreshing = false
                        Toast.makeText(
                            requireContext(),
                            "Youtube Error" + it.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    ResourceState.LOADING -> {
                        binding.progressBarYu.visibility = View.VISIBLE
                    }
                }
            })
//            binding.imgYoutube.visibility = View.GONE
//            binding.iconYoutube.visibility = View.GONE
    }


    private fun announceObserve() {
        homeViewModel.getReklamaId.observe(viewLifecycleOwner, Observer {
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
                    Toast.makeText(
                        requireContext(),
                        "Reklama error" + it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
        homeViewModel.getVideoLInkID.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    it.let {
                        binding.youtubeTitle.text = it.data!!.`object`.title
//                        Glide.with(binding.imgYoutube).load(it.data.`object`.imageUrl)
//                            .into(binding.imgYoutube)
                        onImageClick()
                    }
                }
                ResourceState.ERROR -> {
                    binding.progressBarYu.visibility = View.GONE
                    binding.swipeContainer.isRefreshing = false
                    Toast.makeText(
                        requireContext(),
                        "Youtube Error" + it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                ResourceState.LOADING -> {
                    binding.progressBarYu.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun onlyData() {
        homeViewModel.announce.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    binding.swipeContainer.isRefreshing = false
                    if (it.data !=null){
                        currentPage = it.data.pageable.pageNumber
                        totalPage = it.data.totalPages
                    }
                    flowersAdapter.addData(it.data!!.content)
                    binding.progressBarHome.visibility = View.GONE
                    if (it.data.empty) {
                        isLastPage = true
                    }
                }
                ResourceState.ERROR -> {
                    binding.swipeContainer.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBarHome.visibility = View.VISIBLE
                }
            }
        })
    }

    private fun basic() {

        (activity as MainActivity).destinationId = 0
        flowersAdapter = FlowersAdapter()
        binding.homeRecyclerview.adapter = flowersAdapter

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        requireActivity().window.setFlags(
            0,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }


}