package com.bizmiz.gulbozor.ui.bottom_nav.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentHomeBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {
    private val homeViewModel: HomeViewModel by viewModel()
    private lateinit var flowersAdapter: FlowersAdapter
    private val binding by viewBinding { FragmentHomeBinding.bind(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getAnnounce()
        homeViewModel.getVideoLInkByID()
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

    private fun setListeners() {

        binding.youtubeOthers.setOnClickListener(View.OnClickListener {
            startActivity(Intent(requireContext(), YouTubeActivity::class.java))
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).destinationId = 0
        flowersAdapter = FlowersAdapter()
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
        binding.homeRecyclerview.adapter = flowersAdapter

        flowersAdapter.onClickListener {
            if (it.department != null) {
                val bundle = bundleOf(
                    "flowerData" to it,
                    "desId" to 0,
                )
                destination(it.department, bundle)
            }
        }

        viewLifecycleOwner.lifecycle.addObserver(binding.youtubePlayerView)
        announceObserve()

        binding.swipeContainer.setOnRefreshListener {
            homeViewModel.getAnnounce()
            homeViewModel.getVideoLInkByID()
        }
        return binding.root

    }

    private fun setListeners(view: View) {

        binding.youtubeOthers.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.navHomeToYouTube("Barchasi")
            Navigation.findNavController(view).navigate(action)
            //todo youtube fragmentga aylantirish kerak
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners(view)

        binding.categoryWithBucket.setOnClickListener(View.OnClickListener {
            val action = HomeFragmentDirections.homeToOne("Buket gullar", "home")
            Navigation.findNavController(view).navigate(action)
        })
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
            //findNavController().navigate(R.id.home_to_shop)
            val action = HomeFragmentDirections.homeToShop("home")
            Navigation.findNavController(view).navigate(action)
        })


    }


    private fun announceObserve() {
        homeViewModel.announce.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    binding.swipeContainer.isRefreshing = false
                    flowersAdapter.flowersList = it.data!!
                }
                ResourceState.ERROR -> {
                    binding.swipeContainer.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        homeViewModel.getVideoLInkID.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    it.let {
                        binding.youtubeTitle.text = it.data!!.videoID.title
                        lifecycle.addObserver(binding.youtubePlayerView)
                        binding.youtubePlayerView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {
                                binding.progressBarYu.visibility = View.GONE
                                binding.swipeContainer.isRefreshing = false
                                youTubePlayer.loadVideo(it.data.videoID.videoLink, 0f)
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
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayerView.release()
    }

}