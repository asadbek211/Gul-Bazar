package com.bizmiz.gulbozor.ui.bottom_nav.profile.about

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.WindowInsetsController
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {
    private val binding by viewBinding { FragmentAboutBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
        binding.btnBack.setOnClickListener {
            val navController =
                Navigation.findNavController(requireActivity(), R.id.mainContainer)
            navController.popBackStack()
        }
        binding.ivTelegram.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/gulbazaruz"))
            startActivity(browserIntent)
        }
        binding.ivSite.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://Gulbazar.uz/"))
            startActivity(browserIntent)
        }
        binding.ivInstagram.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://instagram.com/gulbazaruz"))
            startActivity(browserIntent)
        }
        binding.ivYoutube.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/channel/UC9g4Rb_7rmJ--x0w1Gu3ULw"))
            startActivity(browserIntent)
        }
    }
}