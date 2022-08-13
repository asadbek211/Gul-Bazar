package com.bizmiz.gulbozor.ui.start.authentication.get_phone_number

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.PhoneNumberHelper
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentSignUp1Binding
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment1 : Fragment(R.layout.fragment_sign_up1) {
    private val binding by viewBinding { FragmentSignUp1Binding.bind(it) }
    private var smsCode:String? = null
    private var isClick = false
    private val getPhoneNumberViewModel: GetPhoneNumberViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadViews()
        windowStatus()
        smsResultObserve()
        smsTokenDataObserve()
        binding.progress.setOnClickListener {  }
    }

    private fun loadViews() {
        binding.signUpToNext.setOnClickListener {
            if (binding.editTextPhoneSignUp.rawText.length == 9) {
                isClick = true
                getPhoneNumberViewModel.getSmsData(4343245366788986756,1)
                binding.progress.visibility = View.VISIBLE
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
        getPhoneNumberViewModel.result.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    if (it.data=="success" && smsCode!=null){
                        val bundle = bundleOf(
                            "sms_code" to smsCode,
                            "number" to "+998${
                                binding.editTextPhoneSignUp.text.toString().trim()
                                    .replace("(", "").replace(")", "").replace("\\s".toRegex(), "")
                            }"
                        )
                        findNavController().navigate(R.id.action_signUpFragment1_to_signUpFragment2,bundle)
                        smsCode = null
                        isClick = false
                    }
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun smsTokenDataObserve() {
        getPhoneNumberViewModel.smsData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    if (isClick){
                        it.data?.`object`?.let { it1 -> smsSend(it1, it.data.number.toString())}
                    }
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun smsSend(token:String,smsKod:String){
        smsCode = smsKod
        val body: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("mobile_phone", "998${binding.editTextPhoneSignUp.text.toString().trim()
                .replace("(", "").replace(")", "").replace("\\s".toRegex(), "")}")
            .addFormDataPart("message", "Gulbazar.uz: Tasdiqlash kodi - $smsKod")
            .addFormDataPart("from", "4546")
            .addFormDataPart("callback_url", "http://0000.uz/test.php")
            .build()
        getPhoneNumberViewModel.smsSend(
            "Bearer $token",
            body
        )
        PhoneNumberHelper.getHelper().phoneNumber =
            "+998" + binding.editTextPhoneSignUp.text.toString().trim()
    }
}