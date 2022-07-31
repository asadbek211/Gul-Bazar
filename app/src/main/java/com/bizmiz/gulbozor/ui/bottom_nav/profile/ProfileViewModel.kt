package com.bizmiz.gulbozor.ui.bottom_nav.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.core.models.shop.CreateShopRequest
import com.bizmiz.gulbozor.core.models.user.UserDataResponse
import com.bizmiz.gulbozor.core.utils.Resource

class ProfileViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
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
}