package com.bizmiz.gulbozor.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.helper.NetworkHelper
import com.bizmiz.gulbozor.utils.Resource
import okhttp3.MultipartBody


class AddFlowerViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val setImage: MutableLiveData<Resource<Int>> = MutableLiveData()
    val result: LiveData<Resource<Int>>
        get() = setImage
    fun addFlower(partBody: MultipartBody.Part) {
        networkHelper.addFlowerImage(partBody, {
            setImage.value = Resource.success(it)
        }, {
            setImage.value = Resource.error(it)
        })
    }
}