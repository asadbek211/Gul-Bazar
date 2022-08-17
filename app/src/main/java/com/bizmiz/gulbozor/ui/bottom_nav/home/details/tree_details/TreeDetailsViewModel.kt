package com.bizmiz.gulbozor.ui.bottom_nav.home.details.tree_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.FlowerTypeDataItem
import com.bizmiz.gulbozor.core.utils.Resource

class TreeDetailsViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val flowerTypeData: MutableLiveData<Resource<FlowerTypeDataItem>> = MutableLiveData()
    val flowerType: LiveData<Resource<FlowerTypeDataItem>>
        get() = flowerTypeData
    private val deleteAnnounce: MutableLiveData<Resource<Any>> = MutableLiveData()
    val deleteAnnounceResult: LiveData<Resource<Any>>
        get() = deleteAnnounce
    fun getFlowerType(id:Int) {
        networkHelper.getFlowerTypeById(id,{
            flowerTypeData.value = Resource.success(it)
        }, {
            flowerTypeData.value = Resource.error(it)
        })
    }
    fun deleteAnnounceById(announceId:Int) {
        networkHelper.deleteAnnounceById(announceId, {
            deleteAnnounce.value = Resource.success(it)
        }, {
            deleteAnnounce.value = Resource.error(it)
        })
    }
}