package com.bizmiz.gulbozor.ui.bottom_nav.payment.success_pay

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentSuccessPayBinding

class SuccessPayFragment : Fragment(R.layout.fragment_success_pay) {
    private val binding by viewBinding { FragmentSuccessPayBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.setOnClickListener {
            requireActivity().finish()
        }
    }
}