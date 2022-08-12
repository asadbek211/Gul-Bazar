package com.bizmiz.gulbozor.ui.bottom_nav.profile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding { FragmentProfileBinding.bind(it) }
    private val profileViewModel: ProfileViewModel by viewModel()
    private var shopId = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireActivity(), R.color.white)
        binding.editProfile.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.navigate(R.id.bottomNavFragment_to_editProfile)
        }
        binding.shop.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.navigate(R.id.bottomNavFragment_to_createShop)
        }
        profileViewModel.getUserData(AppCache.getHelper().userId)
        userDataObserve()
    }

    /* private fun onBackPressed() {
         val callBack = object : OnBackPressedCallback(true) {
             override fun handleOnBackPressed() {
                 findNavController().navigate(R.id.nav_profile_to_home)
             }
         }
         requireActivity().onBackPressedDispatcher.addCallback(callBack)
     }*/
    private fun userDataObserve() {
        profileViewModel.userData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    if (it.data?.shopId != null) {
                        shopId = it.data.shopId
                    }
                    binding.txtNameSurname.text = it.data?.username
                    binding.txtPhoneNumber.text = it.data?.phoneNumber
                    if (it.data?.shopId != null && it.data.shopId > 0) {
                        binding.txtShop.text = "Mening do‘konim"
                    } else {
                        binding.txtShop.text = "Do‘kon ochish"
                    }
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}