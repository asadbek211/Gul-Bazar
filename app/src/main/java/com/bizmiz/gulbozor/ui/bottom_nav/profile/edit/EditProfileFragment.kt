package com.bizmiz.gulbozor.ui.bottom_nav.profile.edit

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentEditProfileBinding
import com.bizmiz.gulbozor.databinding.FragmentProfileBinding

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var mIsShowPass = false
    private var mIsShowPass1 = false
    private var mIsShowPass2 = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        binding.ivShowHidePass1.setOnClickListener {
            mIsShowPass1 = !mIsShowPass1
            showPassword(binding.etPass1,binding.ivShowHidePass1,mIsShowPass1)
        }
        binding.ivShowHidePass.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(binding.etPass,binding.ivShowHidePass,mIsShowPass)
        }
        binding.ivShowHidePass2.setOnClickListener {
            mIsShowPass2 = !mIsShowPass2
            showPassword(binding.etPass2,binding.ivShowHidePass2,mIsShowPass2)
        }
        binding.btnNext.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.navigate(R.id.action_editProfile_to_profileEditSuccessFragment)
        }
        binding.btnBack.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.popBackStack()
        }
        return binding.root
    }
    private fun showPassword(et:EditText,img:ImageView,isShow: Boolean) {
        if (isShow) {
            et.transformationMethod = HideReturnsTransformationMethod.getInstance()
            img.setImageResource(R.drawable.visibility_on)
        } else {
            et.transformationMethod = PasswordTransformationMethod.getInstance()
            img.setImageResource(R.drawable.ic_eye_off)
        }
        et.setSelection(binding.etPass1.text.toString().length)
    }
}