package com.bizmiz.gulbozor.ui.start.authentication.signUp

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.core.caches.PhoneNumberHelper
import com.bizmiz.gulbozor.databinding.FragmentSignUp3Binding
import com.bizmiz.gulbozor.ui.start.authentication.login.MVP.LoginMVP
import com.bizmiz.gulbozor.ui.start.authentication.login.MVP.LoginPresenter
import com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP.RegistrationMVP
import com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP.RegistrationPresenter

class SignUpFragment3 : Fragment(), RegistrationMVP.View , LoginMVP.View{
    private var _binding: FragmentSignUp3Binding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: RegistrationMVP.Presenter
    private lateinit var loginPresenter: LoginMVP.Presenter
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
        isItFillOrNot()
        binding.phoneNumber.text = phoneNumber
    }


    private fun isItFillOrNot() {
        val username = binding.username.text.trim()
        val userSurname = binding.userSurname.text.trim()
        binding.registrationDone.setOnClickListener(View.OnClickListener {
            if (username.length >= 3) {
                if (userSurname.length >= 3
                ) {
                    if (phoneNumber.isNotEmpty()){
                        binding.registrationDone.visibility = View.INVISIBLE
                        binding.registrationDone.isEnabled = false
                        binding.progressBarr.visibility = View.VISIBLE
                        presenter.sendRegisterData(
                            phoneNumber = phoneNumber.trim().replace("\\s".toRegex(), "")
                                .replace("(", "").replace(")", ""),
                            userName = username.toString(),
                            userSurname = userSurname.toString()
                        )
                    }else{
                        Toast.makeText(
                            requireContext(),
                            "Telefon raqam tasdiqlanmadi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Familiya kiriting",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "Ism kiriting", Toast.LENGTH_SHORT).show()
                Toast.makeText(requireContext(), username, Toast.LENGTH_SHORT).show()
            }
        })
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

    override fun isRegister(isLoading: Boolean) {
        if (isLoading) {
            loginPresenter = LoginPresenter(this)
            loginPresenter.loginWithPhoneNumber(
                phoneNumber = phoneNumber.trim().replace("\\s".toRegex(), "")
                    .replace("(", "").replace(")", "")
            )
        } else {
            Toast.makeText(context, "Bunday hisob mavjud!", Toast.LENGTH_SHORT).show()
            binding.registrationDone.isEnabled = true
            binding.registrationDone.visibility = View.VISIBLE
            binding.progressBarr.visibility = View.INVISIBLE
        }
    }
    override fun isLoading(isLoading: Boolean) {

    }

    override fun onError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun setData(message: String) {
        if (message == "successful") {
            LoginHelper.getHelper().login = true
            findNavController().navigate(R.id.action_signUpFragment3_to_signUpFragment4)
        } else {
            Toast.makeText(context, "Qandaydir xatolik yuz berdi qayta urinib ko'ring", Toast.LENGTH_SHORT).show()
            binding.progressBarr.visibility = View.INVISIBLE
            binding.registrationDone.isEnabled = true
            binding.registrationDone.visibility = View.VISIBLE
            binding.progressBarr.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        presenter.cancelRequest()
        super.onDestroy()
    }
}