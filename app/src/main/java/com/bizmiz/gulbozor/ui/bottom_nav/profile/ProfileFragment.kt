package com.bizmiz.gulbozor.ui.bottom_nav.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.bizmiz.gulbozor.BuildConfig
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentProfileBinding
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.decorView.windowInsetsController?.setSystemBarsAppearance(
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
            )
        }
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
            // TODO: AlertDialog
            showDialog()
            /*AppCache.getHelper().token = null
            AppCache.getHelper().userId = 0
            val intent = Intent(requireActivity(), SignUpActivity::class.java)
            startActivity(intent)
            requireActivity().finish()*/
        }
        binding.privacyPolicy.setOnClickListener {
            val browserIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.freeprivacypolicy.com/live/66044b9a-bde6-4d18-8a5c-2748d47cbec7")
            )
            startActivity(browserIntent)
        }
        binding.share.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Tavsiya qilaman: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
        binding.support.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/danko_98"))
            startActivity(browserIntent)
        }
        binding.appInfo.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.navigate(R.id.action_bottomNavFragment_to_aboutFragment)
        }
        profileViewModel.getUserData(AppCache.getHelper().userId)
        userDataObserve()
        shopIdObserve()
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Ishonchingiz komilmi?")
        builder.setMessage("Bu dasturdan chiqib ketmoqchimisiz?")
        builder.setPositiveButton("Ha", { dialogInterface: DialogInterface, i: Int ->
            AppCache.getHelper().token = null
            AppCache.getHelper().userId = 0
            /*val intent = Intent(requireActivity(), SignUpActivity::class.java)
            startActivity(intent)*/
            requireActivity().finish()
            //Toast.makeText(requireContext(),"Finish",Toast.LENGTH_SHORT).show()
        })
        builder.setNegativeButton("Yo'q", { dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }

    private fun userDataObserve() {
        profileViewModel.userData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    phoneNumber = it.data?.username
                    username = it.data?.usernameTest
                    surname = it.data?.surname
                    if (it.data?.shopId != null) {
                        userShopId = it.data.shopId
                    }
              binding.txtNameSurname.text = "${it.data?.usernameTest}  ${it.data?.surname}"
              binding.txtPhoneNumber.text = it.data?.username
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