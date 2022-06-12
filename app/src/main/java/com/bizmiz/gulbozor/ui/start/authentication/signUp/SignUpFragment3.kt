package com.bizmiz.gulbozor.ui.start.authentication.signUp

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.PhoneNumberHelper
import com.bizmiz.gulbozor.databinding.FragmentSignUp3Binding
import com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP.RegistrationMVP
import com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP.RegistrationPresenter

class SignUpFragment3 : Fragment(), RegistrationMVP.View {

    private var mIsShowPass = false
    private var mIsShowPass1 = false

    private var _binding: FragmentSignUp3Binding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: RegistrationMVP.Presenter

    private val phoneNumber: String = PhoneNumberHelper.getHelper().phoneNumber

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
        presenter = RegistrationPresenter(this)
        windowStatus()
        binding.phoneNumber.setOnClickListener(View.OnClickListener {
            Toast.makeText(requireContext(), phoneNumber, Toast.LENGTH_LONG).show()
        })
        loadViews()

        isItFillOrNot()
    }


    private fun isItFillOrNot() {
        val username = binding.username.text
        binding.registrationDone.setOnClickListener(View.OnClickListener {
            if (username.length >= 5) {
                if ((binding.etPass1.text.contains(binding.etPass.text))
                    && (binding.etPass1.text.length == binding.etPass.text.length)
                    && (binding.etPass.text.length >= 5)
                ) {
                    binding.registrationDone.visibility = View.INVISIBLE
                    binding.registrationDone.isEnabled = false
                    binding.progressBarr.visibility = View.VISIBLE

                    presenter.sendRegisterData(
                        phoneNumber = phoneNumber,
                        userName = username.toString(),
                        password = binding.etPass.text.toString()
                    )

                } else if ((!binding.etPass1.text.contains(binding.etPass.text))
                    || (binding.etPass1.text.length != binding.etPass.text.length)
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Parolni tug'ri tasdiqlang ${binding.etPass1.text}",
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
                    Toast.makeText(
                        requireContext(),
                        binding.etPass.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(
                        requireContext(),
                        binding.etPass1.text.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "OOPS", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Ismingizni kiriting", Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), username, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadViews() {
        binding.signUpToLogin.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_signUpFragment3_to_loginFragment)
        })
        binding.phoneNumber.text = phoneNumber

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

    override fun isRegister(isLoading: Boolean) {
        if (isLoading) {
            findNavController().navigate(R.id.action_signUpFragment3_to_signUpFragment4)

        } else {
            Toast.makeText(context, "Bunday hisob mavjud!", Toast.LENGTH_SHORT).show()
            binding.registrationDone.isEnabled = true
            binding.registrationDone.visibility = View.VISIBLE
            binding.progressBarr.visibility = View.INVISIBLE
        }
    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}