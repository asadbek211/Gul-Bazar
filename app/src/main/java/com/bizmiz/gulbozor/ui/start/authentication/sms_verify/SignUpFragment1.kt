package com.bizmiz.gulbozor.ui.start.authentication.sms_verify

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.PhoneNumberHelper
import com.bizmiz.gulbozor.core.utils.Constant
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentSignUp1Binding
import com.bizmiz.gulbozor.databinding.FragmentSignUp2Binding
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class SignUpFragment1 : Fragment(R.layout.fragment_sign_up1) {
    private val binding by viewBinding { FragmentSignUp1Binding.bind(it) }
    private var smsCode:String? = null
    private val smsVerifyViewModel: SmsVerifyViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadViews()
        windowStatus()
        smsResultObserve()
    }

    private fun loadViews() {
        binding.signUpToNext.setOnClickListener {
            if (binding.editTextPhoneSignUp.rawText.length == 9) {
              if (smsCode!=null){
                  smsCode = null
              }else{
                  smsCode = Random.nextInt(9999,99999).toString()
                  if (smsCode=="86467"){
                     smsCode = null
                  }else{
                      val body: RequestBody = MultipartBody.Builder()
                          .setType(MultipartBody.FORM)
                          .addFormDataPart("mobile_phone", "998${binding.editTextPhoneSignUp.text.toString().trim()
                              .replace("(", "").replace(")", "").replace("\\s".toRegex(), "")}")
                          .addFormDataPart("message", "Gulbazar.uz: Tasdiqlash kodi - $smsCode")
                          .addFormDataPart("from", "4546")
                          .addFormDataPart("callback_url", "http://0000.uz/test.php")
                          .build()
                      smsVerifyViewModel.smsSend(
                          "Bearer ${Constant.SMS_TOKEN}",
                          body
                      )
                      PhoneNumberHelper.getHelper().phoneNumber =
                          "+998" + binding.editTextPhoneSignUp.text.toString().trim()
                      binding.progress.visibility = View.VISIBLE
                  }

              }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Telefon raqamizni tekshiring!",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
    private fun smsResultObserve() {
        smsVerifyViewModel.result.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    if (it.data=="success" && smsCode!=null){
                        val bundle = bundleOf(
                            "sms_code" to smsCode
                        )
                        findNavController().navigate(R.id.action_signUpFragment1_to_signUpFragment2,bundle)
                        smsCode = null
                    }
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}