package com.bizmiz.gulbozor.ui.start.signUp

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.PhoneNumberHelper
import com.bizmiz.gulbozor.databinding.FragmentSignUp3Binding

class SignUpFragment3 : Fragment() {

    private var mIsShowPass = false
    private var mIsShowPass1 = false

    private var _binding: FragmentSignUp3Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUp3Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        windowStatus()

        loadViews(view)

        isItFillOrNot(view)
    }

    private fun isItFillOrNot(view: View) {
        binding.registrationDone.setOnClickListener(View.OnClickListener {
            if (binding.username.text.length >= 2) {
                if ((binding.etPass1.text.contains(binding.etPass.text))
                    && (binding.etPass1.text.length == binding.etPass.text.length)
                    && (binding.etPass.text.length >= 5)
                    && (binding.phoneNumber.text.contains(PhoneNumberHelper.getHelper().phoneNumber))
                    && (binding.phoneNumber.text.length == PhoneNumberHelper.getHelper().phoneNumber.length)
                ) {
                    // TODO: check also phone number
                    Navigation.findNavController(view)
                        .navigate(R.id.action_signUpFragment3_to_signUpFragment4)
                } else if ((!binding.etPass1.text.contains(binding.etPass.text))
                    || (binding.etPass1.text.length != binding.etPass.text.length)
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Parolni tug'ri tasdiqlang",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if ((binding.etPass1.text.contains(binding.etPass.text))
                    && (binding.etPass.text.length < 5)
                    && (binding.etPass1.text.length == binding.etPass.text.length)
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Parol 5 ta harfdan oshsin!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if ((!binding.phoneNumber.text.contains(PhoneNumberHelper.getHelper().phoneNumber))
                    || (binding.phoneNumber.text.length != PhoneNumberHelper.getHelper().phoneNumber.length)
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Telefon raqamni tekshiring",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "OOPS", Toast.LENGTH_SHORT).show()
                    Toast.makeText(
                        requireContext(),
                        PhoneNumberHelper.getHelper().phoneNumber,
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(requireContext(), binding.phoneNumber.text, Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "Ismingizni kiriting", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadViews(view: View) {
        binding.signUpToLogin.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_signUpFragment3_to_loginActivity)
        })
        binding.phoneNumber.setText(PhoneNumberHelper.getHelper().phoneNumber)

        binding.ivShowHidePass1.setOnClickListener {
            mIsShowPass1 = !mIsShowPass1
            showPassword1(mIsShowPass1)
        }
        binding.ivShowHidePass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }
    }

    private fun showPassword1(isShow: Boolean) {
        if (isShow) {
            binding.etPass1.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.ivShowHidePass1.setImageResource(R.drawable.visibility_on)
        } else {
            binding.etPass1.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.ivShowHidePass1.setImageResource(R.drawable.ic_eye_off)
        }
        binding.etPass1.setSelection(binding.etPass1.text.toString().length)
    }

    private fun showPassword(isShow: Boolean) {
        if (isShow) {
            binding.etPass.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.ivShowHidePass.setImageResource(R.drawable.visibility_on)
        } else {
            binding.etPass.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.ivShowHidePass.setImageResource(R.drawable.ic_eye_off)
        }
        binding.etPass.setSelection(binding.etPass.text.toString().length)
    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
    }
}