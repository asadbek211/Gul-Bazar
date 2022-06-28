package com.bizmiz.gulbozor.ui.bottom_nav.payment.success_pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bizmiz.gulbozor.databinding.FragmentSuccessPayBinding

class SuccessPayFragment : Fragment() {
    private var _binding: FragmentSuccessPayBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessPayBinding.inflate(inflater, container, false)
        binding.btnNext.setOnClickListener {
            requireActivity().finish()
        }
        return binding.root
    }
}