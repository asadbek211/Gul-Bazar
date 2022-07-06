package com.bizmiz.gulbozor.ui.bottom_nav.profile.edit.success

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentAddSuccessBinding
import com.bizmiz.gulbozor.databinding.FragmentSuccessEditProfileBinding
import com.bizmiz.gulbozor.ui.bottom_nav.add.AddAnnounceActivity

class ProfileEditSuccessFragment : Fragment(R.layout.fragment_success_edit_profile) {

    private var _binding: FragmentSuccessEditProfileBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
        _binding = FragmentSuccessEditProfileBinding.bind(view)
        binding.btnNext.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.popBackStack()
        }
    }
}