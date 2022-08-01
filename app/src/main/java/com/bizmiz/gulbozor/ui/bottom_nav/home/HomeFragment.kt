package com.bizmiz.gulbozor.ui.bottom_nav.home

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentHomeBinding
import com.bumptech.glide.Glide
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var flowersAdapter: FlowersAdapter
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }

    private val slideModels: ArrayList<SlideModel> = ArrayList()
    private var page: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getAnnounce(page)
        homeViewModel.getVideoLInkByID(1)
        homeViewModel.getReklamaImages(1)
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
        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener {
                v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                binding.progressBarHome.visibility = View.VISIBLE
                homeViewModel.getAnnounce(page)
            }
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners(view)
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
        flowersAdapter.onClickListener {
            if (it.department != null) {
                val bundle = bundleOf(
                    "flowerData" to it,
                    "desId" to 0,
                )
                destination(it.department, bundle)
            }
        }
//        flowersAdapter.deleteItemById()
        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayerView)
        announceObserve()

        binding.swipeContainer.setOnRefreshListener {
            homeViewModel.getAnnounce(page)
            homeViewModel.getVideoLInkByID(2)
        }
        binding.categoryWithBucket.setOnClickListener {
//            val action = HomeFragmentDirections.homeToOne("Buket gullar", "home")
//            Navigation.findNavController(view).navigate(action)
        }
        binding.homeMadeFlowerCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToOne("Xonaki gullar", "home")
            Navigation.findNavController(view).navigate(action)
        })
        binding.treeFlowerCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToOne("Daraxtlar", "home")
            Navigation.findNavController(view).navigate(action)
        })
        binding.potFlowerCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToOne("Tuvak va o'g'itlar", "home")
            Navigation.findNavController(view).navigate(action)
        })
        binding.customersCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToOne("Haridorlar", "home")
            Navigation.findNavController(view).navigate(action)
        })
        binding.shopsCat.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToShop("home")
            Navigation.findNavController(view).navigate(action)
        })


    }

    private fun onImageClick() {
        binding.imgYoutube.setOnClickListener {
            homeViewModel.getVideoLInkID.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.SUCCESS -> {
                        it.let {
                            binding.youtubeTitle.text = it.data!!.`object`.title
                            lifecycle.addObserver(binding.youtubePlayerView)
                            binding.youtubePlayerView.addYouTubePlayerListener(object :
                                AbstractYouTubePlayerListener() {
                                override fun onReady(youTubePlayer: YouTubePlayer) {
                                    binding.progressBarYu.visibility = View.GONE
                                    binding.swipeContainer.isRefreshing = false
                                    youTubePlayer.loadVideo(it.data.`object`.videoLink, 0f)
                                }
                            })
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
            binding.imgYoutube.visibility = View.GONE
            binding.iconYoutube.visibility = View.GONE
        }
    }


    private fun announceObserve() {
        homeViewModel.announce.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    binding.swipeContainer.isRefreshing = false
                    flowersAdapter.flowersList =
                        (it.data?.content as ArrayList<AnnounceResponseData>?)!!
                    binding.progressBarHome.visibility = View.GONE
                }
                ResourceState.ERROR -> {
                    binding.swipeContainer.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBarHome.visibility = View.VISIBLE
                }
            }
        })
        homeViewModel.getReklamaId.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    binding.swipeContainer.isRefreshing = false
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
                        Glide.with(binding.imgYoutube).load(it.data.`object`.imageUrl)
                            .into(binding.imgYoutube)
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
}