package com.bizmiz.gulbozor.ui.start.authentication.login

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.caches.LoginHelper
import com.bizmiz.gulbozor.databinding.FragmentLoginBinding
import com.bizmiz.gulbozor.ui.start.authentication.login.MVP.LoginMVP
import com.bizmiz.gulbozor.ui.start.authentication.login.MVP.LoginPresenter

class LoginFragment : Fragment(), LoginMVP.View {
    private var mIsShowPass = false

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var presenter: LoginMVP.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!LoginHelper.getHelper().login) {
            presenter = LoginPresenter(this)
            setListeners()
        } else {
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        }
    }

    private fun setListeners() {
        binding.logoGulbazar.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
        })

        binding.ivShowHidePass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }

        binding.signUp.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment1)
        })

        binding.startLogin.setOnClickListener(View.OnClickListener {
            binding.progressBarr.visibility = View.VISIBLE
            binding.startLogin.visibility = View.INVISIBLE
            binding.startLogin.isEnabled = false
            presenter.loginWithPhoneNumber(
                phoneNumber = binding.etPhoneNumber.text.toString(),
                password = binding.etPass.text.toString()
            )
        })
        binding.justForToast.setOnClickListener(View.OnClickListener {
            Toast.makeText(
                requireContext(),
                binding.etPhoneNumber.text.toString() + binding.etPass.text.toString(),
                Toast.LENGTH_LONG
            ).show()
        })

    }

    override fun isLoading(isLoading: Boolean) {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun setData(message: String) {
        if (message == "successful") {

            Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_loginFragment_to_mainActivity)
            LoginHelper.getHelper().login = true
            AppCache.getHelper().password = binding.etPass.text.toString()
            requireActivity().finish()


        } else {
            Toast.makeText(context, "Telefon raqam yoki parol xato", Toast.LENGTH_SHORT).show()
            binding.progressBarr.visibility = View.INVISIBLE
            binding.startLogin.visibility = View.VISIBLE
            binding.startLogin.isEnabled = true
        }

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

    override fun onDestroy() {
        super.onDestroy()
        presenter.cancelRequest()
    }
}