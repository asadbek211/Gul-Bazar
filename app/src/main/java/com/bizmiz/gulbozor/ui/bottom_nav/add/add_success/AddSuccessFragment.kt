package com.bizmiz.gulbozor.ui.bottom_nav.add.add_success

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentAddSuccessBinding

class AddSuccessFragment : Fragment(R.layout.fragment_add_success) {
    private val binding by viewBinding { FragmentAddSuccessBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
        binding.btnHome.setOnClickListener {
            requireActivity().finish()
        }
    }
}