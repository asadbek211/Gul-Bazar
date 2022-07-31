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
import com.bizmiz.gulbozor.core.utils.Constant
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentSignUp2Binding
import com.bizmiz.gulbozor.ui.start.authentication.sms_verify.SmsVerifyViewModel
import com.poovam.pinedittextfield.PinField
import okhttp3.MultipartBody

import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment2 : Fragment(R.layout.fragment_sign_up2) {
    private var smsCode:String? = null
    private val binding by viewBinding { FragmentSignUp2Binding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smsCode = requireArguments().getString("sms_code")
        loadView(view)
        windowStatus()
        binding.signUpToLogin.setOnClickListener {
            findNavController().navigate(R.id.sign_up2_login)
        }

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