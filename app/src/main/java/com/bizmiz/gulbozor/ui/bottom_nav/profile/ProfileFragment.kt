package com.bizmiz.gulbozor.ui.bottom_nav.profile

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding { FragmentProfileBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
        //        BottomNavFragment().visibility()
        binding.editProfile.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.navigate(R.id.bottomNavFragment_to_editProfile)
        }
        binding.shop.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.navigate(R.id.bottomNavFragment_to_createShop)
        }
    }

    private fun onBackPressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.nav_profile_to_home)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

}