package com.bizmiz.gulbozor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.helper.NetworkHelper
import com.bizmiz.gulbozor.ui.model.AnnounceDataResponse
import com.bizmiz.gulbozor.ui.model.FlowerListResponse
import com.bizmiz.gulbozor.utils.Resource


class HomeViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getAnnounce: MutableLiveData<Resource<List<AnnounceDataResponse>>> = MutableLiveData()
    val announce: LiveData<Resource<List<AnnounceDataResponse>>>
        get() = getAnnounce

    fun getAnnounce() {
        networkHelper.getAnnounce({
            getAnnounce.value = Resource.success(it)
        }, {
            getAnnounce.value = Resource.error(it)
        })
    }
}