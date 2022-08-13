package com.bizmiz.gulbozor.ui.bottom_nav.profile.edit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bizmiz.gulbozor.R
import com.bizmiz.gulbozor.core.caches.AppCache
import com.bizmiz.gulbozor.core.caches.PhoneNumberHelper
import com.bizmiz.gulbozor.core.models.user.edit_user.UserEditRequest
import com.bizmiz.gulbozor.core.utils.ResourceState
import com.bizmiz.gulbozor.core.utils.networkCheck
import com.bizmiz.gulbozor.core.utils.showSoftKeyboard
import com.bizmiz.gulbozor.core.utils.viewBinding
import com.bizmiz.gulbozor.databinding.FragmentEditProfileBinding
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model.ShopPhoneNumber
import com.bizmiz.gulbozor.ui.bottom_nav.profile.ProfileViewModel
import com.bizmiz.gulbozor.ui.start.authentication.get_phone_number.GetPhoneNumberViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {
    private var phoneNumber: String? = null
    private var username: String? = null
    private var surname: String? = null
    private var shopId: Int? = null
    private var smsCode:String? = null
    private var isClick = false
    private val binding by viewBinding { FragmentEditProfileBinding.bind(it) }
    private val getPhoneNumberViewModel: GetPhoneNumberViewModel by viewModel()
    private val editProfileViewModel: EditProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        editProfileViewModel.getUserData(AppCache.getHelper().userId)
        smsResultObserve()
        smsTokenDataObserve()
        userDataObserve()
        userEditDataObserve()
        binding.progress.setOnClickListener {  }
        binding.btnNext.setOnClickListener {
            if (checkUserData()){
                val number1 = binding.phoneNumber.text?.trim().toString().replace("\\s".toRegex(), "")
                val number2 = phoneNumber.toString().replace("+998","")
                if (number1 == number2){
                    isClick = true
                    binding.progress.visibility = View.VISIBLE
                    editProfileViewModel.updateUser(AppCache.getHelper().userId, UserEditRequest(
                        "string","+998${binding.phoneNumber.text?.trim().toString().replace("\\s".toRegex(), "")}"
                        , shopId!!,binding.userSurname.text.trim().toString(),binding.username.text.trim().toString()
                    ))
                }else{
                    binding.progress.visibility = View.VISIBLE
                    isClick = true
                    getPhoneNumberViewModel.getSmsData(4343245366788986756,1)
                }
            }
        }
        binding.btnBack.setOnClickListener {
            val navController =
                Navigation.findNavController(
                    requireActivity(),
                    R.id.mainContainer
                )
            navController.popBackStack()
        }
        binding.username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.username.text.toString().trim().isEmpty()) {
                    binding.username.error = "Ism kiriting"
                } else {
                    userCheck()
                }
            }

        })
        binding.userSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.userSurname.text.toString().trim().isEmpty()) {
                    binding.userSurname.error = "Familiya kiriting"
                } else {
                    userCheck()
                }
            }

        })
        binding.phoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (binding.phoneNumber.text.toString().trim().isEmpty()) {
                    binding.phoneNumber.error = "Tel raqam kiriting"
                } else {
                    userCheck()
                }
            }

        })
    }

    private fun userCheck() {
        var number2 = phoneNumber.toString().replace("+998","")
        if (username == binding.username.text.trim().toString()  &&
            surname == binding.userSurname.text.trim().toString() &&
            binding.phoneNumber.text.toString().trim().replace("\\s".toRegex(), "") == number2){
            binding.btnNext.isEnabled = false
            binding.btnNext.setBackgroundResource(R.drawable.button_bg_noenabled)
        }else{
            binding.btnNext.isEnabled = true
            binding.btnNext.setBackgroundResource(R.drawable.button_bg)
        }
    }

    private fun smsResultObserve() {
        getPhoneNumberViewModel.result.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    if (it.data=="success" && smsCode!=null){
                        val bundle = bundleOf(
                            "phone_number" to "+998${binding.phoneNumber.text?.trim().toString().replace("\\s".toRegex(), "")}",
                            "username" to binding.username.text.trim().toString(),
                            "surname" to binding.userSurname.text.trim().toString(),
                            "shopId" to shopId,
                            "sms_code" to smsCode
                        )
                        val navController =
                            Navigation.findNavController(
                                requireActivity(),
                                R.id.mainContainer
                            )
                        navController.navigate(R.id.action_editProfile_to_smsVerifyFragment,bundle)
                        smsCode = null
                        isClick = false
                    }
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun smsTokenDataObserve() {
        getPhoneNumberViewModel.smsData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    userCheck()
                    if (isClick){
                        it.data?.`object`?.let { it1 -> smsSend(it1, it.data.number.toString())}
                    }
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun userDataObserve() {
        editProfileViewModel.resultUser.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    if (isClick){
                        val navController =
                            Navigation.findNavController(
                                requireActivity(),
                                R.id.mainContainer
                            )
                        navController.navigate(R.id.action_editProfile_to_profileEditSuccessFragment)
                        isClick = false
                    }
                }
                ResourceState.ERROR -> {
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    private fun smsSend(token:String,smsKod:String){
        Log.d("natija","smsSend : $smsCode & $smsKod")
        smsCode = smsKod
        val body: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("mobile_phone", "998${binding.phoneNumber.text.toString().trim()
                .replace("(", "").replace(")", "").replace("\\s".toRegex(), "")}")
            .addFormDataPart("message", "Gulbazar.uz: Tasdiqlash kodi - $smsKod")
            .addFormDataPart("from", "4546")
            .addFormDataPart("callback_url", "http://0000.uz/test.php")
            .build()
        getPhoneNumberViewModel.smsSend(
            "Bearer $token",
            body
        )
        PhoneNumberHelper.getHelper().phoneNumber =
            "+998" + binding.phoneNumber.text.toString().trim()
    }
    private fun checkUserData(): Boolean {
        return when {
            binding.username.text?.isEmpty() == true -> {
                binding.username.error = "Ism kiriting"
                binding.username.showSoftKeyboard()
                false
            }
            binding.userSurname.text?.isEmpty() == true -> {
                binding.userSurname.error = "Familiya kiriting"
                binding.userSurname.showSoftKeyboard()
                false
            }
            binding.phoneNumber.text?.isEmpty() == true -> {
                binding.phoneNumber.error = "Tel raqam kiriting"
                binding.phoneNumber.showSoftKeyboard()
                false
            }
            binding.phoneNumber.text?.trim().toString().replace("\\s".toRegex(), "").length != 9->{
                binding.phoneNumber.error = "Tel raqamni to'liq kiriting"
                binding.phoneNumber.showSoftKeyboard()
                false
            }
            !networkCheck(requireContext()) -> {
                Toast.makeText(requireActivity(), "Internet aloqasi yo'q", Toast.LENGTH_SHORT)
                    .show()
                false
            }
            else -> {
                true
            }
        }
    }
    private fun userEditDataObserve() {
        editProfileViewModel.userData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResourceState.SUCCESS-> {
                    phoneNumber = it.data?.phoneNumberTest
                    username = it.data?.username
                    surname = it.data?.surname
                    if (it.data?.shopId!=null){
                        shopId = it.data.shopId
                    }
                    binding.username.setText(username)
                    binding.userSurname.setText(surname)
                    binding.phoneNumber.setText(phoneNumber.toString().replace("+998",""))
                    userCheck()
                }
                ResourceState.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}