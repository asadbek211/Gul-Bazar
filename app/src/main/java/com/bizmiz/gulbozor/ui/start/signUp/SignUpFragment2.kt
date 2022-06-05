package com.bizmiz.gulbozor.ui.start.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bizmiz.gulbozor.databinding.FragmentSignUp2Binding
import com.poovam.pinedittextfield.PinField


class SignUpFragment2 : Fragment() {
    private var _binding: FragmentSignUp2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUp2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadView()
    }

    private fun loadView() {
        val linePinField = binding.squareFieldCode
        linePinField.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String): Boolean {
                Toast.makeText(requireContext(), enteredText, Toast.LENGTH_SHORT).show()
                return true // Return false to keep the keyboard open else return true to close the keyboard
            }
        }
    }


}