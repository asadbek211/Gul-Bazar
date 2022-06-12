package com.bizmiz.gulbozor.ui.start.authentication.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentSignUp4Binding

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
        windowStatus()
        binding.startAfterReg.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signUpFragment4_to_mainActivity)
        })
    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
    }
}