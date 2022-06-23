package com.bizmiz.gulbozor.ui.bottom_nav.add.add_success

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentAddSuccessBinding
import com.bizmiz.gulbozor.ui.bottom_nav.add.AddAnnounceActivity

class AddSuccessFragment : Fragment(R.layout.fragment_add_success) {

    private var _binding: FragmentAddSuccessBinding? = null
    private val binding get() = _binding!!
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
        (activity as AddAnnounceActivity).destinationId = 1
        _binding = FragmentAddSuccessBinding.bind(view)
        binding.btnHome.setOnClickListener {
            startActivity(Intent(requireActivity(),MainActivity::class.java))
            requireActivity().finish()
        }
    }
}