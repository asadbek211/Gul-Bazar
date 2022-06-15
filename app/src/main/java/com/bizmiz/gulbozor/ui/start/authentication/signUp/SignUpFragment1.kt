package com.bizmiz.gulbozor.ui.start.authentication.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.PhoneNumberHelper
import com.bizmiz.gulbozor.databinding.FragmentSignUp1Binding

class SignUpFragment1 : Fragment() {
    private var _binding: FragmentSignUp1Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUp1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadViews()
        windowStatus()
    }

    private fun loadViews() {
        binding.flagUzb.setOnClickListener(View.OnClickListener {


            Toast.makeText(
                requireContext(),
                PhoneNumberHelper.getHelper().phoneNumber,
                Toast.LENGTH_LONG
            ).show()
        })
        binding.signUpToNext.setOnClickListener(View.OnClickListener {
            if (binding.editTextPhoneSignUp.rawText.length == 9) {
                findNavController().navigate(R.id.action_signUpFragment1_to_signUpFragment2)
                PhoneNumberHelper.getHelper().phoneNumber =
                    "+998" + binding.editTextPhoneSignUp.text.toString()

            } else {
                Toast.makeText(
                    requireContext(),
                    "Telefon raqamizni tekshiring!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun windowStatus() {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
    }
}