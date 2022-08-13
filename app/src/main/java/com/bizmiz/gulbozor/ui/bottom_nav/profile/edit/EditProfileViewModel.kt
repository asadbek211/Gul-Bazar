package com.bizmiz.gulbozor.ui.bottom_nav.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.core.models.shop.CreateShopRequest
import com.bizmiz.gulbozor.core.models.user.UserDataResponse
import com.bizmiz.gulbozor.core.models.user.edit_user.UserEditRequest
import com.bizmiz.gulbozor.core.utils.Resource

class EditProfileViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val updateUser: MutableLiveData<Resource<Any>> = MutableLiveData()
    val resultUser: LiveData<Resource<Any>>
        get() = updateUser
    private val getUserData: MutableLiveData<Resource<UserDataResponse>> = MutableLiveData()
    val userData: LiveData<Resource<UserDataResponse>>
        get() = getUserData
    fun getUserData(
        userId:Int
    ) {
        networkHelper.getUserData(userId,{
            getUserData.value = Resource.success(it)
        }, {
            getUserData.value = Resource.error(it)
        })
    }
    fun updateUser(
        sellerId: Int,
        userEditRequest: UserEditRequest,
    ) {
        networkHelper.updateShopId(sellerId,userEditRequest, {
            updateUser.value = Resource.success(it)
        }, {
            updateUser.value = Resource.error(it)
        })
    }

}