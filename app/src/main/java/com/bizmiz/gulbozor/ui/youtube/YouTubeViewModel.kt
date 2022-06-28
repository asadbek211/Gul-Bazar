package com.bizmiz.gulbozor.ui.youtube

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.YouTubeLinkPage
import com.bizmiz.gulbozor.core.utils.Resource

class YouTubeViewModel(private val networkHelper: NetworkHelper) : ViewModel() {

    private val getYouTubePage: MutableLiveData<Resource<List<YouTubeLinkPage>>> = MutableLiveData()
    val announcePage: LiveData<Resource<List<YouTubeLinkPage>>>
        get() = getYouTubePage

    fun getYouTubePage() {
        networkHelper.getYouTubePage(page = 0, {
            getYouTubePage.value = Resource.success(it)
        }, {
            getYouTubePage.value = Resource.error(it)
        })
    }
}