package com.bizmiz.gulbozor.ui.bottom_nav.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.models.CityDataItem
import com.bizmiz.gulbozor.core.models.RegionData
import com.bizmiz.gulbozor.core.models.SmsRequestData
import com.bizmiz.gulbozor.core.models.sms.SmsResponseData
import com.bizmiz.gulbozor.core.models.youtube.getVideoLinkById.YouTubeLinkID
import com.bizmiz.gulbozor.core.utils.Resource
import okhttp3.RequestBody

class HomeViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getAnnounce: MutableLiveData<Resource<List<AnnounceResponseData>>> = MutableLiveData()
    val announce: LiveData<Resource<List<AnnounceResponseData>>>
        get() = getAnnounce

    private val videoLinkVM: MutableLiveData<Resource<YouTubeLinkID>> = MutableLiveData()
    val getVideoLInkID: LiveData<Resource<YouTubeLinkID>>
        get() = videoLinkVM
    private val getRegion: MutableLiveData<Resource<RegionData>> = MutableLiveData()
    val regionList: LiveData<Resource<RegionData>>
        get() = getRegion
    private val getCity: MutableLiveData<Resource<ArrayList<CityDataItem>>> = MutableLiveData()
    val cityData: LiveData<Resource<ArrayList<CityDataItem>>>
        get() = getCity
    fun getVideoLInkByID() {
        networkHelper.getYouTubeById(id = 1,
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