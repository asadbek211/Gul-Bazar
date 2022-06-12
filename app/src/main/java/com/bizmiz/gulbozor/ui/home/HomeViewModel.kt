package com.bizmiz.gulbozor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.FlowerListResponse
import com.bizmiz.gulbozor.core.utils.Resource


class HomeViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getAnnounce: MutableLiveData<Resource<List<FlowerListResponse>>> = MutableLiveData()
    val announce: LiveData<Resource<List<FlowerListResponse>>>
        get() = getAnnounce

    fun getAnnounce() {
        networkHelper.getAnnounce({
            getAnnounce.value = Resource.success(it)
        }, {
            getAnnounce.value = Resource.error(it)
        })
    }
}