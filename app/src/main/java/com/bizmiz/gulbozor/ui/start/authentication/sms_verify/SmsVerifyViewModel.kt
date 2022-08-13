package com.bizmiz.gulbozor.ui.start.authentication.sms_verify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.LoginResponse
import com.bizmiz.gulbozor.core.utils.Resource
import com.bizmiz.gulbozor.core.models.LoginRequest

class SmsVerifyViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val checkNumber: MutableLiveData<Resource<LoginResponse>> = MutableLiveData()
    val checkNumberResult: LiveData<Resource<LoginResponse>>
        get() = checkNumber
    fun checkPhoneNumber(
        loginRequest: LoginRequest
    ) {
        networkHelper.checkPhoneNumber(loginRequest, {
            checkNumber.value = Resource.success(it)
        }, {
            checkNumber.value = Resource.error(it)
        })
    }
}