package com.bizmiz.gulbozor.ui.bottom_nav.categories.oneCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.AnnounceResponseData
import com.bizmiz.gulbozor.core.models.category.ByParentIDItem
import com.bizmiz.gulbozor.core.utils.Resource

class OneTypeOfCatVM(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getParentCategory: MutableLiveData<Resource<List<ByParentIDItem>>> = MutableLiveData()
    val parentCategory: LiveData<Resource<List<ByParentIDItem>>> get() = getParentCategory

    private val getAnnounce: MutableLiveData<Resource<List<AnnounceResponseData>>> = MutableLiveData()
    val announce: LiveData<Resource<List<AnnounceResponseData>>>
        get() = getAnnounce

    fun getParentCatByID(id: Int) {
        networkHelper.getByParentCatID(
            id = id, {
                getParentCategory.value = Resource.success(it)
            }, {
                getParentCategory.value = Resource.error(it)
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