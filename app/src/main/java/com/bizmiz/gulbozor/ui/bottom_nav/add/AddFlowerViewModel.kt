package com.bizmiz.gulbozor.ui.bottom_nav.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.AnnounceData
import com.bizmiz.gulbozor.core.models.AnnounceResponse
import com.bizmiz.gulbozor.core.utils.Resource
import com.bizmiz.gulbozor.ui.model.ImageResponseData
import okhttp3.MultipartBody


class AddFlowerViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val setImage: MutableLiveData<Resource<ImageResponseData>> = MutableLiveData()
    val result: LiveData<Resource<ImageResponseData>>
        get() = setImage
    private val setAnnounce: MutableLiveData<Resource<AnnounceResponse>> = MutableLiveData()
    val resultAnnounce: LiveData<Resource<AnnounceResponse>>
        get() = setAnnounce

    fun addFlower(
        img1: MultipartBody.Part?,
        img2: MultipartBody.Part?,
        img3: MultipartBody.Part?,
        img4: MultipartBody.Part?,
        img5: MultipartBody.Part?,
        img6: MultipartBody.Part?,
        img7: MultipartBody.Part?,
        img8: MultipartBody.Part?,
    ) {
        networkHelper.addFlowerImage(img1, img2, img3, img4, img5, img6, img7, img8, {
            setImage.value = Resource.success(it)
        }, {
            setImage.value = Resource.error(it)
        })
    }

    fun setAnnounce(announceData: AnnounceData) {
        networkHelper.setAnnounce(announceData, {
            setAnnounce.value = Resource.success(it)
        }, {
            setAnnounce.value = Resource.error(it)
        })
    }
}