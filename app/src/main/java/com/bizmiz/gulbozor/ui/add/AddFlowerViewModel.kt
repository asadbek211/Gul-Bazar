package com.bizmiz.gulbozor.ui.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.helper.NetworkHelper
import com.bizmiz.gulbozor.ui.model.AnnounceResponse
import com.bizmiz.gulbozor.ui.model.FlowerListResponse
import com.bizmiz.gulbozor.utils.Resource
import okhttp3.MultipartBody


class AddFlowerViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val setImage: MutableLiveData<Resource<Int>> = MutableLiveData()
    val result: LiveData<Resource<Int>>
        get() = setImage
    private val setAnnounce: MutableLiveData<Resource<AnnounceResponse>> = MutableLiveData()
    val resultAnnounce: LiveData<Resource<AnnounceResponse>>
        get() = setAnnounce
    fun addFlower(partBody: MultipartBody.Part) {
        networkHelper.addFlowerImage(partBody, {
            setImage.value = Resource.success(it)
        }, {
            setImage.value = Resource.error(it)
        })
    }
    fun setAnnounce(flowerListResponse: FlowerListResponse) {
        networkHelper.setAnnounce(flowerListResponse, {
            setAnnounce.value = Resource.success(it)
        }, {
            setAnnounce.value = Resource.error(it)
        })
    }
}