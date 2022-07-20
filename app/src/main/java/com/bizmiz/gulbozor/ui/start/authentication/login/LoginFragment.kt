package com.bizmiz.gulbozor.ui.start.authentication.login

import android.content.Intent
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
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentLoginBinding
import com.bizmiz.gulbozor.ui.start.authentication.login.MVP.LoginMVP
import com.bizmiz.gulbozor.ui.start.authentication.login.MVP.LoginPresenter
import com.bizmiz.gulbozor.ui.start.onBoard.MiddleActivity

class LoginFragment : Fragment(R.layout.fragment_login), LoginMVP.View {
    private var mIsShowPass = false

    private val binding by viewBinding { FragmentLoginBinding.bind(it) }

    private lateinit var presenter: LoginMVP.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!LoginHelper.getHelper().login) {
            presenter = LoginPresenter(this)
            setListeners()
        } else {
            val intent = Intent(requireContext(), MiddleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setListeners() {
        /*binding.logoGulbazar.setOnClickListener(View.OnClickListener {
            val intent = Intent(requireContext(), MiddleActivity::class.java)
            startActivity(intent)
        })
*/
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
                phoneNumber = binding.etPhoneNumber.text.toString().replace("\\s".toRegex(), "")
                    .replace("(", "").replace(")", ""),
                password = binding.etPass.text.toString()
            )
        })
        /*
            binding.justForToast.setOnClickListener(View.OnClickListener {
                Toast.makeText(
                    requireContext(),
                    binding.etPhoneNumber.text?.trim().toString()
                        .replace(" ".toRegex(), "") + binding.etPass.text.toString(),
                    Toast.LENGTH_LONG
                ).show()
            })*/

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
            val intent = Intent(requireContext(), MiddleActivity::class.java)
            startActivity(intent)
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
        presenter.cancelRequest()
        super.onDestroy()
    }
}