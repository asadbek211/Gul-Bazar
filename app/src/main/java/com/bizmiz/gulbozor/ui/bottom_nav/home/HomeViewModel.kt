package com.bizmiz.gulbozor.ui.bottom_nav.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.AnnounceData
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById.YoutubeLinkID
import com.bizmiz.gulbozor.core.utils.Resource

class HomeViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getAnnounce: MutableLiveData<Resource<List<AnnounceData>>> = MutableLiveData()
    val announce: LiveData<Resource<List<AnnounceData>>>
        get() = getAnnounce

    private val videoLinkVM: MutableLiveData<Resource<YoutubeLinkID>> = MutableLiveData()
    val getVideoLInkID: LiveData<Resource<YoutubeLinkID>>
        get() = videoLinkVM

    fun getVideoLInkByID() {
        networkHelper.getYouTubeById(id = 6,
            {
                videoLinkVM.value = Resource.success(it!!)
            }, {
                videoLinkVM.value = Resource.error(it)
            }
        )
    }


    fun getAnnounce() {
        networkHelper.getAnnounce({
            getAnnounce.value = Resource.success(it)
        }, {
            getAnnounce.value = Resource.error(it)
        })
    }
}