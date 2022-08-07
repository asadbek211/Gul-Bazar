package com.bizmiz.gulbozor.ui.start.authentication.new_auth.login

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.BaseFragment
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentNewLoginBinding

class NewLoginFragment : BaseFragment(R.layout.fragment_new_login) {
    private val binding by viewBinding { FragmentNewLoginBinding.bind(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextBtnManager()
    }


    private fun nextBtnManager() {
        binding.apply {
            signUpToNext.click {
                if (editTextPhoneSignUp.text!!.length == 12) {
                    val bundle = Bundle()
                    bundle.putString("key1", editTextPhoneSignUp.text.toString())
                    findNavController().navigate(
                        R.id.action_newLoginFragment_to_newSmsVerifyFragment,
                        bundle
                    )
                } else {
                    tst("Telfon raqamingizni tekshiring!")
                }
            }
        }
    }
}