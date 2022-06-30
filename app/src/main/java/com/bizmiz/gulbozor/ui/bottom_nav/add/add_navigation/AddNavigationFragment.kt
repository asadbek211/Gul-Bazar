package com.bizmiz.gulbozor.ui.bottom_nav.add.add_navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentAddNavigationBinding
import com.bizmiz.gulbozor.databinding.FragmentCategoryAddFlowerBinding
import com.bizmiz.gulbozor.ui.bottom_nav.add.AddAnnounceActivity
import com.bizmiz.gulbozor.ui.bottom_nav.add.category_add_flower.CategoryAddFlowerFragment

class AddNavigationFragment : Fragment(R.layout.fragment_add_navigation) {
    private var _binding: FragmentAddNavigationBinding? = null
    private var isSeller:Boolean? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddNavigationBinding.bind(view)
       binding.btnSeller.setOnClickListener {
           navigation()
           isSeller = true
       }
        binding.btnShopping.setOnClickListener {
            navigation()
            isSeller = false
        }
    }
    private fun navigation(){
        val bundle = bundleOf(
            "isSeller" to isSeller
        )
        val navController =
            Navigation.findNavController(
                requireActivity(),
                R.id.addContainer
            )
        navController.navigate(R.id.action_navigation_add_to_categoryAddFlowerFragment,bundle)
    }
}