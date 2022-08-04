package com.bizmiz.gulbozor.ui.start.authentication.new_auth.sms_verify

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bizmiz.gulbozor.BaseFragment
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.Constant
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentNewSmsVerifyBinding
import com.bizmiz.gulbozor.ui.start.authentication.sms_verify.SmsVerifyViewModel
import com.bizmiz.gulbozor.ui.start.onBoard.MiddleActivity
import com.poovam.pinedittextfield.PinField
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class NewSmsVerifyFragment : BaseFragment(R.layout.fragment_new_sms_verify) {
    private val binding by viewBinding { FragmentNewSmsVerifyBinding.bind(it) }
    private val viewModel: SmsVerifyViewModel by viewModel()
    private var phoneNumber: String = ""
    private var smsCode: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catchPhoneNumber()
        sendSmsCode()
        nextBtnManager()
        loadSms()
    }

    private fun nextBtnManager() {
        binding.nextSignUp.click {
            checkSMS(smsCode)
        }
    }

    private fun loadSms() {
        val linePinField = binding.squareFieldCode
        linePinField.onTextCompleteListener = object : PinField.OnTextCompleteListener {

            override fun onTextComplete(enteredText: String): Boolean {
                checkSMS(enteredText)
                binding.nextSignUp.click {
                    checkSMS(enteredText)
                }
                return true // Return false to keep the keyboard open else return true to close the keyboard
            }
        }
    }

    private fun checkSMS(code: String) {
        if (smsCode != "" && code == smsCode) {
            startActivity(Intent(requireContext(), MiddleActivity::class.java))
        } else {
            Toast.makeText(
                requireContext(),
                "SMS kodni yaxshilab tekshiring.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun sendSmsCode() {
        smsCode = Random.nextInt(9999, 99999).toString()
        val body: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "mobile_phone",
                "998${phoneNumber.trim().replace("\\s".toRegex(), "")}"
            )
            .addFormDataPart("message", "Gulbazar.uz: Tasdiqlash kodi - $smsCode")
            .addFormDataPart("from", "4546")
            .addFormDataPart("callback_url", "http://0000.uz/test.php")
            .build()
        viewModel.smsSend(
            "Bearer ${Constant.SMS_TOKEN}",
            body
        )
        sharedPref.isSavedPhoneNumber("+998" + phoneNumber.trim())
    }

    private fun catchPhoneNumber() {
        phoneNumber = requireArguments().getString("key1")!!
    }
}