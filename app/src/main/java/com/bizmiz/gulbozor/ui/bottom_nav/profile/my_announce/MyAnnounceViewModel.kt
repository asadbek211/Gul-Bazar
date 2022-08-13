package com.bizmiz.gulbozor.ui.bottom_nav.profile.my_announce

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.category.ByCategoryID
import com.bizmiz.gulbozor.core.utils.Resource
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model.ShopPhoneNumber

class MyAnnounceViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val myAnnounceList: MutableLiveData<Resource<ByCategoryID>> = MutableLiveData()
    val myAnnounce: LiveData<Resource<ByCategoryID>>
        get() = myAnnounceList
    fun getMyAnnounce(
        sellerId: Int,
        page: Int) {
        networkHelper.getMyAnnounce(
            sellerId,page, {
                myAnnounceList.value = Resource.success(it)
            }, {
                myAnnounceList.value = Resource.error(it)
            }
        )
    }
}