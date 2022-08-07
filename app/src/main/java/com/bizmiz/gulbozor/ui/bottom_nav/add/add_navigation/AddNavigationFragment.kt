package com.bizmiz.gulbozor.ui.bottom_nav.add.add_navigation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentAddNavigationBinding

class AddNavigationFragment : Fragment(R.layout.fragment_add_navigation) {
    private var isSeller: Boolean? = null
    private val binding by viewBinding { FragmentAddNavigationBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSeller.setOnClickListener {
            navigation()
            isSeller = false
        }
        binding.btnShopping.setOnClickListener {
            navigation()
            isSeller = true
        }
    }

    private fun navigation() {
        val bundle = bundleOf(
            "isSeller" to isSeller
        )
        val navController =
            Navigation.findNavController(
                requireActivity(),
                R.id.addContainer
            )
        navController.navigate(R.id.action_navigation_add_to_categoryAddFlowerFragment, bundle)
    }
}