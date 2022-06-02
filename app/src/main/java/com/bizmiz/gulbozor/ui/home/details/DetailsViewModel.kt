package com.bizmiz.gulbozor.ui.home.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.helper.NetworkHelper
import com.bizmiz.gulbozor.utils.Resource

class DetailsViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getData: MutableLiveData<Resource<ByteArray>> = MutableLiveData()
    val data: LiveData<Resource<ByteArray>>
        get() = getData

    fun getData(
        imageInt: Int
    ) {
        networkHelper.getFlowerImage(imageInt,{
            getData.value = Resource.success(it)
        }, {
            getData.value = Resource.error(it)
        })
    }
}