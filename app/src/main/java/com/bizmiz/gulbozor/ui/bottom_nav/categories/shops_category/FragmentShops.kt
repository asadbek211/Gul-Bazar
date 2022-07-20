package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentShopsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FragmentShops : Fragment() {
    val args: FragmentShopsArgs by navArgs()
    private var _binding: FragmentShopsBinding? = null
    val binding get() = _binding!!

    private val shopsViewModel: ShopsViewModel by viewModel()

    private val shopAdapter = ShopsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shopsViewModel.getShopsList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        announceObserve()
        onBackHomePressed()

        binding.onBackPressed.setOnClickListener(View.OnClickListener {
            if (args.onBack == "home") {
                findNavController().navigate(R.id.nav_shops_to_home)
            } else {
                findNavController().navigate(R.id.nav_shops_to_category)
            }
        })
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)

        binding.shopsRecycler.adapter = shopAdapter
        binding.shopsRecycler.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun announceObserve() {
        shopsViewModel.shops.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    shopAdapter.data = it.data!!
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        shopAdapter.setOnItemClickListener(object : ShopsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val action = FragmentShopsDirections.shopsToShop((position + 1).toString())
                Navigation.findNavController(view!!).navigate(action)
            }

        })
    }


    private fun onBackHomePressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (args.onBack == "home") {
                    findNavController().navigate(R.id.nav_shops_to_home)
                } else {
                    findNavController().navigate(R.id.nav_shops_to_category)
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }


}