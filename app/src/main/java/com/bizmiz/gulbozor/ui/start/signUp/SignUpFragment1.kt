package com.bizmiz.gulbozor.ui.start.signUp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
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
        loadViews(view)
    }

    private fun loadViews(view: View) {
        binding.signUpToNext.setOnClickListener(View.OnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_signUpFragment1_to_signUpFragment2)
        })
    }
}