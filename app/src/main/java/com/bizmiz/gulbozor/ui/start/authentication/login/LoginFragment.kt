package com.bizmiz.gulbozor.ui.start.authentication.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
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

        setListeners(view)
    }

    private fun setListeners(view: View) {
        binding.signUp.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_signUpFragment1)
        })
    }
}