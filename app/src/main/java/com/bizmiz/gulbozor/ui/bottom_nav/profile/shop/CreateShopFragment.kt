package com.bizmiz.gulbozor.ui.bottom_nav.profile.shop

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentCreateShopBinding

class CreateShopFragment : Fragment(R.layout.fragment_create_shop) {
    private val binding by viewBinding { FragmentCreateShopBinding.bind(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.popBackStack()
        }
    }
}