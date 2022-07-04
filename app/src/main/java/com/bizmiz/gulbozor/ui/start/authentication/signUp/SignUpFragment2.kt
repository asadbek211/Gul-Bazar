package com.bizmiz.gulbozor.ui.start.authentication.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentSignUp2Binding
import com.poovam.pinedittextfield.PinField


class SignUpFragment2 : Fragment() {
    private var _binding: FragmentSignUp2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUp2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView(view)
        windowStatus()
        binding.signUpToLogin.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.sign_up2_login)
        })
    }

    private fun loadView(view: View) {
        val linePinField = binding.squareFieldCode
        linePinField.onTextCompleteListener = object : PinField.OnTextCompleteListener {

            override fun onTextComplete(enteredText: String): Boolean {
                checkSMS(enteredText, view)
                binding.nextSignUp.setOnClickListener(View.OnClickListener {
                    checkSMS(enteredText, view)
                })
                return true // Return false to keep the keyboard open else return true to close the keyboard
            }
        }
    }

    private fun checkSMS(code: String, view: View) {
        if (code == "00000") {
            findNavController().navigate(R.id.action_signUpFragment2_to_signUpFragment3)
        } else {
            Toast.makeText(
                requireContext(),
                "SMS kodni yaxshilab tekshiring.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
    }
}