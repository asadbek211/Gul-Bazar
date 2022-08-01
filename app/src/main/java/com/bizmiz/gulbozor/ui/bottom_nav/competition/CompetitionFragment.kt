package com.bizmiz.gulbozor.ui.bottom_nav.competition

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentCompetitionBinding

class CompetitionFragment : Fragment(R.layout.fragment_competition) {
    private val binding by viewBinding { FragmentCompetitionBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
    }

    private fun onBackPressed() {
        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.nav_competition_to_home)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }
}
