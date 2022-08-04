package com.bizmiz.gulbozor.ui.start.authentication.sms_verify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.utils.Resource
import okhttp3.RequestBody

class SmsVerifyViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val smsResult: MutableLiveData<Resource<String>> = MutableLiveData()
    val result: LiveData<Resource<String>>
        get() = smsResult

    fun smsSend(
        token: String,
        data: RequestBody,
    ) {
        networkHelper.smsSend(token, data, {
            smsResult.value = Resource.success(it)
        }, {
            smsResult.value = Resource.error(it)
        })
    }
}