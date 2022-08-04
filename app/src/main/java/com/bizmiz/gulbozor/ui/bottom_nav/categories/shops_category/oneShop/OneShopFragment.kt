package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
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
        onBackPressed()
        binding.backPressed.setOnClickListener(View.OnClickListener {
            if (args.position == "customer") {
                findNavController().navigate(R.id.shop_to_home)
            } else {
                findNavController().navigate(R.id.shop_to_shops)
            }
        })
        if (args.position == "customer") {
            binding.bottomMainTxt.text = "Eksportchi tashkilotlar"
            binding.logoShop.text = "Haridorlar"
        }

        binding.swipeContainer.setOnRefreshListener {
            if (args.position == "customer") {
                oneShopVM.getAnnounceOfCustomer(0)
            } else {
                oneShopVM.getShopIdPage(0, args.position.toInt())
                oneShopVM.getShopNumber(args.position.toInt())
            }
        }
        binding.scrollNested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                page++
                binding.progressBarOneCat.visibility = View.VISIBLE
                if (args.position == "customer") {
                    oneShopVM.getAnnounceOfCustomer(page)
                } else {
                    oneShopVM.getShopIdPage(page, args.position.toInt())
                    oneShopVM.getShopNumber(args.position.toInt())
                }
            }
        })

        windowStatus()

        announceObserve()
    }


    private fun announceObserve() {
        if (args.position == "customer") {
            oneShopVM.customerPost.observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    ResourceState.SUCCESS -> {
                        binding.swipeContainer.isRefreshing = false
                        if (it.data!!.content.isEmpty()) {
                            binding.progressBarOneCat.visibility = View.GONE
                        }
                        adapter.oneShopList =
                            it.data.content as ArrayList<com.bizmiz.gulbozor.core.models.category.Content>

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
                        adapter.oneShopList =
                            it.data!!.content as ArrayList<com.bizmiz.gulbozor.core.models.category.Content>
                        if (it.data.content.isEmpty()) {
                            binding.progressBarOneCat.visibility = View.GONE
                        }
                    }
                    ResourceState.ERROR -> {
                        binding.swipeContainer.isRefreshing = false
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
                if (it.data!!.content.isEmpty()) {
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

    private fun onBackPressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (args.position == "customer") {
                    findNavController().navigate(R.id.shop_to_home)
                } else {
                    findNavController().navigate(R.id.shop_to_shops)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

}