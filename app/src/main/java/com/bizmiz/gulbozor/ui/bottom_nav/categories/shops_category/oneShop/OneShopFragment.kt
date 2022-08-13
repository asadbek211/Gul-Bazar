package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop

import android.os.Bundle
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
import androidx.navigation.fragment.navArgs
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentOneShopBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OneShopFragment : Fragment() {
    val args: OneShopFragmentArgs by navArgs()
    private val oneShopVM: OnShopVM by viewModel()
    private lateinit var adapter: OneShopAdapter
    private var _binding: FragmentOneShopBinding? = null
    private val binding get() = _binding!!
    private var isLastPage: Boolean = false
    private var page: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.position == "customer") {
            oneShopVM.getAnnounceOfCustomer(page)
        } else {
            oneShopVM.getShopIdPage(page, args.position.toInt())
            oneShopVM.getShopNumber(args.position.toInt())
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOneShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OneShopAdapter()
        binding.oneShopRec.adapter = adapter
        binding.backPressed.setOnClickListener(View.OnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_activity_main
                )
            navController.popBackStack()
        })
        if (args.position == "customer") {
            binding.bottomMainTxt.text = "Eksportchi tashkilotlar"
            binding.logoShop.text = "Haridorlar"
        }

        binding.swipeContainer.setOnRefreshListener {
            if (args.position == "customer") {
                adapter.clearAdapter()
                oneShopVM.getAnnounceOfCustomer(0)
                isLastPage = false
            } else {
                adapter.clearAdapter()
                oneShopVM.getShopIdPage(0, args.position.toInt())
                oneShopVM.getShopNumber(args.position.toInt())
                isLastPage = false
            }
        }
        binding.scrollNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                if (!isLastPage) {
                    page++
                    binding.progressBarOneCat.visibility = View.VISIBLE
                    if (args.position == "customer") {
                        oneShopVM.getAnnounceOfCustomer(page)
                    } else {
                        oneShopVM.getShopIdPage(page, args.position.toInt())
                        oneShopVM.getShopNumber(args.position.toInt())
                    }
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
        if (args.position == "customer") {
            oneShopVM.customerPost.observe(viewLifecycleOwner, Observer {
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
        } else {
            oneShopVM.shopIdPage.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.SUCCESS -> {
                        binding.swipeContainer.isRefreshing = false
                        adapter.addOneShopListData(it.data!!.content)
                        if (it.data.empty) {
                            isLastPage = true
                        }
                    }
                    ResourceState.ERROR -> {
                        binding.swipeContainer.isRefreshing = false
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                if (it.data!!.content.isEmpty() && page == 0) {
                    binding.notPostYet.visibility = View.VISIBLE
                }
            })
            oneShopVM.shopNumber.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.SUCCESS -> {
                        binding.swipeContainer.isRefreshing = false
                        binding.pNPlace1.text = it.data!!.`object`.phoneNumber1
                        binding.pNPlace.text = it.data.`object`.phoneNumber2
                        binding.bottomMainTxt.text = "Gul do'koni"
                        binding.logoShop.text = it.data.`object`.shopName
                    }
                    ResourceState.ERROR -> {
                        binding.swipeContainer.isRefreshing = false
                    }
                }
            })

        }

    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
    }
}