package com.bizmiz.gulbozor.ui.start.authentication.signUp

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.BuildConfig
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.core.caches.PhoneNumberHelper
import com.bizmiz.gulbozor.core.caches.SetUpHelper
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.databinding.FragmentSignUp3Binding
import com.bizmiz.gulbozor.core.models.LoginRequest
import com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP.RegistrationMVP
import com.bizmiz.gulbozor.ui.start.authentication.signUp.MVP.RegistrationPresenter
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class SignUpFragment3 : Fragment(), RegistrationMVP.View {
    private var _binding: FragmentSignUp3Binding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: RegistrationMVP.Presenter
    private val signUpFragment3ViewModel: SignUpFragment3ViewModel by viewModel()
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
        checkPhoneNumberObserve()
    }


    private fun isItFillOrNot() {
        binding.registrationDone.setOnClickListener {
            val username = binding.username.text?.trim().toString()
            val userSurname = binding.userSurname.text?.trim().toString()
            if (username.length >= 3) {
                if (userSurname.length >= 3
                ) {
                    if (phoneNumber.isNotEmpty()) {
                        binding.registrationDone.visibility = View.INVISIBLE
                        binding.registrationDone.isEnabled = false
                        binding.progressBarr.visibility = View.VISIBLE
                        presenter.sendRegisterData(
                            phoneNumber.trim().replace("\\s".toRegex(), "")
                                .replace("(", "").replace(")", ""),
                            0,
                            userSurname,
                            username
                        )
                    } else {
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

    override fun isRegister(isLoading: Boolean) {
        if (isLoading) {
            signUpFragment3ViewModel.checkPhoneNumber(
                LoginRequest(phoneNumber = phoneNumber.trim().replace("\\s".toRegex(), "")
                .replace("(", "").replace(")", ""))
            )
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
    private fun checkPhoneNumberObserve() {
        signUpFragment3ViewModel.checkNumberResult.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    AppCache.getHelper().token = it.data?.token
                    val properties = Properties()
                    properties.
                    AppCache.getHelper().userId = it.data?.user_id!!.toInt()
                    SetUpHelper.getHelper().board = true
                    findNavController().navigate(R.id.action_signUpFragment3_to_signUpFragment4)
                }
                ResourceState.ERROR -> {
                    binding.progressBarr.visibility = View.INVISIBLE
                    binding.registrationDone.isEnabled = true
                    binding.registrationDone.visibility = View.VISIBLE
                    binding.progressBarr.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    override fun onDestroy() {
        presenter.cancelRequest()
        super.onDestroy()
    }
}