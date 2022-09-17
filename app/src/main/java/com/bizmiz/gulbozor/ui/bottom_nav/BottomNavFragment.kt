package com.bizmiz.gulbozor.ui.bottom_nav

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.networkCheck
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentBottomNavBinding
import com.bizmiz.gulbozor.ui.bottom_nav.add.AddAnnounceActivity

class BottomNavFragment : Fragment(R.layout.fragment_bottom_nav) {
    private val binding by viewBinding { FragmentBottomNavBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main)
        binding.bottomNavView.background = null
        binding.bottomNavView.setupWithNavController(navController)
        binding.fab.setOnClickListener {
            if (!networkCheck(requireContext())){
                (activity as MainActivity).checkConnect()
            }
            else{
                startActivity(Intent(requireActivity(), AddAnnounceActivity::class.java))
            }
        }
    }
}