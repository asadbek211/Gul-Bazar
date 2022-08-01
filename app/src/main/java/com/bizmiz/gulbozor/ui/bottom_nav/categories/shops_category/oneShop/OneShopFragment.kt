package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentOneShopBinding
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model.Content
import org.koin.androidx.viewmodel.ext.android.viewModel

class OneShopFragment : Fragment() {
    val args: OneShopFragmentArgs by navArgs()


    private val oneShopVM: OnShopVM by viewModel()
    private lateinit var adapter: OneShopAdapter

    private var _binding: FragmentOneShopBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        oneShopVM.getShopIdPage(0, args.position!!.toInt())
        oneShopVM.getShopNumber(args.position!!.toInt())
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
            findNavController().navigate(R.id.shop_to_shops)
        })

        windowStatus()

        announceObserve()
    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
    }

    private fun announceObserve() {
        oneShopVM.shopIdPage.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    adapter.oneShopList = (it.data?.content as ArrayList<Content>?)!!
                }
                ResourceState.ERROR -> {
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
                    binding.pNPlace1.text = it.data!!.`object`.phoneNumber1
                    binding.pNPlace.text = it.data.`object`.phoneNumber2
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun onBackPressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.shop_to_shops)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

}