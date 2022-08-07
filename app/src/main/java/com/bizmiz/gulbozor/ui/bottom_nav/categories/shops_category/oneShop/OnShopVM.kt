package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.category.ByCategoryID
import com.bizmiz.gulbozor.core.utils.Resource
import com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model.ShopPhoneNumber

class OnShopVM(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getShopPage: MutableLiveData<Resource<ByCategoryID>> = MutableLiveData()
    val shopIdPage: LiveData<Resource<ByCategoryID>>
        get() = getShopPage

    private val getCustomerPost: MutableLiveData<Resource<ByCategoryID>> = MutableLiveData()
    val customerPost: LiveData<Resource<ByCategoryID>>
        get() = getCustomerPost

    private val getShopNumber: MutableLiveData<Resource<ShopPhoneNumber>> = MutableLiveData()
    val shopNumber: LiveData<Resource<ShopPhoneNumber>>
        get() = getShopNumber

    fun getShopIdPage(page: Int, shopId: Int) {
        networkHelper.getOneShopList(page = page, shopId = shopId, {
            getShopPage.value = Resource.success(it)
        },
            {
                getShopPage.value = Resource.error(it)
            })
    }

    fun getAnnounceOfCustomer(page: Int) {
        networkHelper.getCustomerPost(
            page, {
                getCustomerPost.value = Resource.success(it)
            }, {
                getCustomerPost.value = Resource.error(it)
            }
        )
    }

    fun getShopNumber(shopId: Int) {
        networkHelper.getOneShopNumber(id = shopId, {
            getShopNumber.value = Resource.success(it)
        },
            {
                getShopNumber.value = Resource.error(it)
            })
    }
}