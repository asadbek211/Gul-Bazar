package com.bizmiz.gulbozor.ui.bottom_nav.home.details.fetilizers_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.FlowerTypeDataItem
import com.bizmiz.gulbozor.core.utils.Resource

class FetilizersDetailsViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val flowerTypeData: MutableLiveData<Resource<FlowerTypeDataItem>> = MutableLiveData()
    val flowerType: LiveData<Resource<FlowerTypeDataItem>>
        get() = flowerTypeData

    fun getFlowerType(id:Int) {
        networkHelper.getFlowerTypeById(id,{
            flowerTypeData.value = Resource.success(it)
        }, {
            flowerTypeData.value = Resource.error(it)
        })
    }
}