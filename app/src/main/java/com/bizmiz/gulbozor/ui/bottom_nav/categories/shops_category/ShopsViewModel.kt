package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.slideReklama.ReklamaImages
import com.bizmiz.gulbozor.core.utils.Resource

class ShopsViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getShops: MutableLiveData<Resource<List<ShopsListItem>>> = MutableLiveData()
    val shops: LiveData<Resource<List<ShopsListItem>>>
        get() = getShops

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

    fun getShopsList() {
        networkHelper.getShopsList(
            {
                getShops.value = Resource.success(it)
            },
            {
                getShops.value = Resource.error(it)
            }
        )
    }
}