package com.bizmiz.gulbozor.ui.start.authentication.signUp

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentSignUp2Binding
import com.poovam.pinedittextfield.PinField


class SignUpFragment2 : Fragment(R.layout.fragment_sign_up2) {
    private var smsCode:String? = null
    private val binding by viewBinding { FragmentSignUp2Binding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smsCode = requireArguments().getString("sms_code")
        loadView(view)
        windowStatus()
    }

    private fun loadView(view: View) {
        val linePinField = binding.squareFieldCode
        linePinField.onTextCompleteListener = object : PinField.OnTextCompleteListener {

            override fun onTextComplete(enteredText: String): Boolean {
                checkSMS(enteredText)
                binding.nextSignUp.setOnClickListener {
                    checkSMS(enteredText)
                }
                return true
            }
        }
    }

    private fun checkSMS(code: String) {
        if (smsCode!=null && code == smsCode) {
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
    }

}