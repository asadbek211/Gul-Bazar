package com.bizmiz.gulbozor.ui.bottom_nav.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bizmiz.gulbozor.R
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.bizmiz.gulbozor.core.app.App
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentProfileBinding
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.FragmentShopsArgs
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.FragmentShopsDirections
import com.bizmiz.gulbozor.ui.start.authentication.signUp.SignUpActivity
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding { FragmentProfileBinding.bind(it) }
    private val profileViewModel: ProfileViewModel by viewModel()
    private var userShopId = 0
    private var shopId:Int? = null
    private var phoneNumber:String? = null
    private var username:String? = null
    private var surname:String? = null
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
        binding.myAnnounce.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.nav_host_fragment_activity_main
                )
            navController.navigate(R.id.action_navigation_user_to_myAnnounceFragment)
        }
        binding.shop.setOnClickListener {
            if (userShopId>0){
                val action = ProfileFragmentDirections.actionNavigationUserToOneShopFragment((shopId.toString()))
                Navigation.findNavController(requireView()).navigate(action)
            }else{
                val bundle = bundleOf(
                    "phone_number" to phoneNumber,
                    "username" to username,
                    "surname" to surname,
                )
                val navController =
                    Navigation.findNavController(
                        requireActivity(),
                        R.id.mainContainer
                    )
                navController.navigate(R.id.bottomNavFragment_to_createShop,bundle)
            }

        }
        binding.imgLogout.setOnClickListener {
            AppCache.getHelper().token = null
            AppCache.getHelper().userId = 0
            val intent = Intent(requireActivity(), SignUpActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
        profileViewModel.getUserData(AppCache.getHelper().userId)
        userDataObserve()
        shopIdObserve()
    }
    private fun userDataObserve() {
        profileViewModel.userData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    phoneNumber = it.data?.phoneNumberTest
                    username = it.data?.username
                    surname = it.data?.surname
                    if (it.data?.shopId!=null){
                        userShopId = it.data.shopId
                    }
              binding.txtNameSurname.text = "${it.data?.username}  ${it.data?.surname}"
              binding.txtPhoneNumber.text = it.data?.phoneNumberTest
                    if (it.data?.shopId!=null && it.data.shopId>0){
                        binding.txtShop.text = "Mening do‘konim"
                        profileViewModel.getShopId()
                    }else{
                        binding.txtShop.text = "Do‘kon ochish"
                    }
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun shopIdObserve(){
        profileViewModel.shopId.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                   shopId = it.data
                    if (it.data!=null)
                    AppCache.getHelper().shopId = it.data
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}