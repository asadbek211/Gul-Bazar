package com.bizmiz.gulbozor.ui.start.authentication.sms_verify

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.MainActivity
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.core.caches.SetUpHelper
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentSignUp2Binding
import com.bizmiz.gulbozor.core.models.LoginRequest
import com.poovam.pinedittextfield.PinField
import org.koin.androidx.viewmodel.ext.android.viewModel


class SignUpFragment2 : Fragment(R.layout.fragment_sign_up2) {
    private var smsCode:String? = null
    private var number:String? = null
    private val binding by viewBinding { FragmentSignUp2Binding.bind(it) }
    private val smsVerifyViewModel:SmsVerifyViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smsCode = requireArguments().getString("sms_code")
        number = requireArguments().getString("number")
        loadView(view)
        windowStatus()
        checkPhoneNumberObserve()
        binding.progress.setOnClickListener {  }
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
            if (number!=null){
                binding.progress.visibility = View.VISIBLE
                smsVerifyViewModel.checkPhoneNumber(LoginRequest(number!!))
            }
        }
        else {
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
    private fun checkPhoneNumberObserve() {
        smsVerifyViewModel.checkNumberResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                        AppCache.getHelper().token = it.data?.token
                        AppCache.getHelper().userId = it.data?.user_id!!.toInt()
                    SetUpHelper.getHelper().board = true
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                    requireActivity().finish()

                }
                ResourceState.ERROR -> {
                    if (it.message=="unregistered"){
                        findNavController().navigate(R.id.action_signUpFragment2_to_signUpFragment3)
                    }else{
                        binding.progress.visibility = View.GONE
                        Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}