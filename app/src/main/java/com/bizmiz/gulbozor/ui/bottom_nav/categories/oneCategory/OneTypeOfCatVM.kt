package com.bizmiz.gulbozor.ui.bottom_nav.categories.oneCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.category.ByParentIDItem
import com.bizmiz.gulbozor.core.models.home.GetAnnounceByIndexPage
import com.bizmiz.gulbozor.core.models.slideReklama.ReklamaImages
import com.bizmiz.gulbozor.core.utils.Resource

class OneTypeOfCatVM(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getParentCategory: MutableLiveData<Resource<List<ByParentIDItem>>> =
        MutableLiveData()
    val parentCategory: LiveData<Resource<List<ByParentIDItem>>> get() = getParentCategory

    private val getAnnounce: MutableLiveData<Resource<GetAnnounceByIndexPage>> =
        MutableLiveData()
    val announce: LiveData<Resource<GetAnnounceByIndexPage>>
        get() = getAnnounce

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


    fun getParentCatByID(id: Int) {
        networkHelper.getByParentCatID(
            id = id, {
                getParentCategory.value = Resource.success(it)
            }, {
                getParentCategory.value = Resource.error(it)
            }
        )
    }

    /*fun getAnnounce(page: Int) {
        networkHelper.getAnnounceByPage(
            page = page, {
                getAnnounce.value = Resource.success(it)
            }, {
                getAnnounce.value = Resource.error(it)
            })
    }*/

}