package com.bizmiz.gulbozor.ui.bottom_nav.profile.my_announce

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentMyAnnounceBinding
import com.bizmiz.gulbozor.databinding.FragmentOneShopBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyAnnounceFragment : Fragment() {
    private val myAnnounceViewModel: MyAnnounceViewModel by viewModel()
    private lateinit var adapter: MyAnnounceAdapter
    private var _binding: FragmentMyAnnounceBinding? = null
    private val binding get() = _binding!!
    private var isLastPage: Boolean = false
    private var page: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myAnnounceViewModel.getMyAnnounce(AppCache.getHelper().userId,page)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyAnnounceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MyAnnounceAdapter()
        binding.muAnnounceRec.adapter = adapter
        binding.backPressed.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_activity_main
                )
            navController.popBackStack()
        }
        binding.swipeContainer.setOnRefreshListener {
                myAnnounceViewModel.getMyAnnounce(AppCache.getHelper().userId,0)
                isLastPage = false
        }
        binding.scrollNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (!isLastPage) {
                    page++
                    binding.progressBarOneCat.visibility = View.VISIBLE
                        myAnnounceViewModel.getMyAnnounce(AppCache.getHelper().userId,page)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Boshqa elonlar mavjud emas",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBarOneCat.visibility = View.GONE
                }

            }
        })

        windowStatus()

        announceObserve()

        intent()
    }

    private fun intent() {
        adapter.onClickListener {
            if (it.department != null) {
                val bundle = bundleOf(
                    "flowerData" to it,
                    "desId" to 0,
                )
                destination(it.department, bundle)
            }
        }
    }

    private fun destination(categoryId: Int, bundle: Bundle?) {
        val navController = Navigation.findNavController(
            requireActivity(), R.id.mainContainer
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


    private fun announceObserve() {
            myAnnounceViewModel.myAnnounce.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.SUCCESS -> {
                        binding.swipeContainer.isRefreshing = false
                        if (it.data!!.empty) {
                            isLastPage = true
                        }
                        adapter.addOneShopListData(it.data.content)

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
            ContextCompat.getColor(requireActivity(), R.color.white)
    }
}