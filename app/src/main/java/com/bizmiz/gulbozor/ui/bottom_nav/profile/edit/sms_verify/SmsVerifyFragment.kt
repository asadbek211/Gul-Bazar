package com.bizmiz.gulbozor.ui.bottom_nav.profile.edit.sms_verify

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.models.user.edit_user.UserEditRequest
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentSmsVerifyBinding
import com.bizmiz.gulbozor.ui.bottom_nav.profile.edit.EditProfileViewModel
import com.bizmiz.gulbozor.ui.start.authentication.sms_verify.SmsVerifyViewModel
import com.poovam.pinedittextfield.PinField
import org.koin.androidx.viewmodel.ext.android.viewModel


class SmsVerifyFragment : Fragment(R.layout.fragment_sms_verify) {
    private var phoneNumber: String? = null
    private var username: String? = null
    private var surname: String? = null
    private var shopId: Int? = null
    private var smsCode:String? = null
    private val binding by viewBinding { FragmentSmsVerifyBinding.bind(it) }
    private val editProfileViewModel: EditProfileViewModel by viewModel()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        smsCode = requireArguments().getString("sms_code")
        phoneNumber = requireArguments().getString("phone_number")
        username = requireArguments().getString("username")
        surname = requireArguments().getString("surname")
        shopId = requireArguments().getInt("shopId")
        loadView(view)
        windowStatus()
        binding.progress.setOnClickListener {  }
        userDataObserve()
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
            binding.progress.visibility = View.VISIBLE
            editProfileViewModel.updateUser(
                AppCache.getHelper().userId, UserEditRequest(
                phoneNumber.toString()
                , shopId!!,surname.toString(),username.toString()
            )
            )
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
    private fun userDataObserve() {
        editProfileViewModel.resultUser.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    val navController =
                        Navigation.findNavController(
                            requireActivity(),
                            R.id.mainContainer
                        )
                    navController.navigate(R.id.action_smsVerifyFragment_to_profileEditSuccessFragment)
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    val navController =
                        Navigation.findNavController(
                            requireActivity(),
                            R.id.mainContainer
                        )
                    navController.popBackStack()
                }
            }
        })
    }
}