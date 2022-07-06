package com.bizmiz.gulbozor.ui.bottom_nav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.databinding.FragmentBottomNavBinding

class BottomNavFragment : Fragment(R.layout.fragment_bottom_nav) {
    private lateinit var binding: FragmentBottomNavBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBottomNavBinding.bind(view)
        val navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
        binding.navView.setupWithNavController(navController)
        LoginHelper.getHelper().login = true
    }

}