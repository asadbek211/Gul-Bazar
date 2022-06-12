package com.bizmiz.gulbozor.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.utils.Resource
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.AnnounceData

class HomeViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getAnnounce: MutableLiveData<Resource<List<AnnounceData>>> = MutableLiveData()
    val announce: LiveData<Resource<List<AnnounceData>>>
        get() = getAnnounce

    fun getAnnounce() {
        networkHelper.getAnnounce({
            getAnnounce.value = Resource.success(it)
        }, {
            getAnnounce.value = Resource.error(it)
        })
    }
}