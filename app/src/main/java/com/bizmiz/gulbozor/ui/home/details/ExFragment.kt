package com.bizmiz.gulbozor.ui.home.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentExBinding
import com.bizmiz.gulbozor.ui.home.FlowersAdapter
import com.bizmiz.gulbozor.ui.home.HomeViewModel
import com.bizmiz.gulbozor.ui.home.core.adapter.GroupAdapter
import com.bizmiz.gulbozor.ui.home.core.model.GroupItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExFragment : Fragment() {
    private var _binding: FragmentExBinding? = null
    private val binding get() = _binding!!

    private val groupAdapter = GroupAdapter()
    private var layoutManager: LinearLayoutManager? = null


    private val homeViewModel: HomeViewModel by viewModel()

    //private lateinit var flowersAdapter: FlowersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getAnnounce()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = LinearLayoutManager(requireContext())
        binding.groupList.adapter = groupAdapter
        binding.groupList.layoutManager = layoutManager
        announceObserve()
        loadData()
        homeViewModel.announce
    }

    private fun loadData() {
        val groupItems: ArrayList<GroupItem> = ArrayList()

        val item = GroupItem(
            recImage = R.drawable.rec_image,
            txtCategories = "Kategoriyalar",
            txtAll = "Barchasi",
            txtYouTubeTitle = "Atirgullarni qanday va qachon ekish kerak?",
            imgYouTube = R.drawable.img_youtube,
            othersYouTube = R.drawable.youtube_others,
            allPosts = "Barcha e'lonlar",
            flowerList = FlowersAdapter().flowersList
        )

        groupItems.add(item)

        groupAdapter.setGroupItems(groupItems)
    }

    private fun announceObserve() {
        homeViewModel.announce.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    Log.d("TAGAdapter", "Success")
                    //binding.swipeContainer.isRefreshing = false
                    FlowersAdapter().flowersList = it.data!!
                }
                ResourceState.ERROR -> {
                    Log.d("TAGAdapter", "Error")
                    //binding.swipeContainer.isRefreshing = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}