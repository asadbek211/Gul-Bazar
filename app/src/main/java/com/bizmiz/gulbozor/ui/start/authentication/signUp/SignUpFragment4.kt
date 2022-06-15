package com.bizmiz.gulbozor.ui.start.authentication.signUp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.databinding.FragmentSignUp4Binding
import com.bizmiz.gulbozor.ui.start.onBoard.MiddleActivity

class SignUpFragment4 : Fragment() {
    private var _binding: FragmentSignUp4Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUp4Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (LoginHelper.getHelper().login) {
            startActivity(Intent(requireContext(), MiddleActivity::class.java))
        } else {
            windowStatus()
            binding.startAfterReg.setOnClickListener(View.OnClickListener {
                //LoginHelper.getHelper().login = true
                val intent = Intent(requireContext(), MiddleActivity::class.java)
                startActivity(intent)
                // onDestroy()
            })
        }

    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
    }
}