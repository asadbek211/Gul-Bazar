package com.bizmiz.gulbozor.ui.youtube

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.slideReklama.ReklamaImages
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkPage.YouTubeLinkPage
import com.bizmiz.gulbozor.core.utils.Resource

class YouTubeViewModel(private val networkHelper: NetworkHelper) : ViewModel() {

    private val getYouTubePage: MutableLiveData<Resource<YouTubeLinkPage>> = MutableLiveData()
    val announcePage: LiveData<Resource<YouTubeLinkPage>>
        get() = getYouTubePage

    private val reklamaVM: MutableLiveData<Resource<ReklamaImages>> = MutableLiveData()
    val getReklamaId: LiveData<Resource<ReklamaImages>>
        get() = reklamaVM

    fun getReklamaImages(id: Int) {
        networkHelper.getReklamaById(
            id = id,
            {
                reklamaVM.value = Resource.success(it!!)
            }, {
                reklamaVM.value = Resource.error(it)
            }
        )
    }

    fun getYouTubePage(page: Int) {
        networkHelper.getYouTubePage(page = page, {
            getYouTubePage.value = Resource.success(it)
        }, {
            getYouTubePage.value = Resource.error(it)
        })
    }
}