package com.bizmiz.gulbozor.ui.start.authentication.get_phone_number

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.sms.SmsTokenResponse
import com.bizmiz.gulbozor.core.utils.Resource
import okhttp3.RequestBody

class GetPhoneNumberViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val smsResult: MutableLiveData<Resource<String>> = MutableLiveData()
    val result: LiveData<Resource<String>>
        get() = smsResult
    private val smsTokenData: MutableLiveData<Resource<SmsTokenResponse>> = MutableLiveData()
    val smsData: LiveData<Resource<SmsTokenResponse>>
        get() = smsTokenData
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
    fun getSmsData(
        key: Long,
        id: Int
    ) {
        networkHelper.getSmsToken(key, id, {
            smsTokenData.value = Resource.success(it)
        }, {
            smsTokenData.value = Resource.error(it)
        })
    }
}