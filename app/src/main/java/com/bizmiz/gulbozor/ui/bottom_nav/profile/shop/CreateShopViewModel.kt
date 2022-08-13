package com.bizmiz.gulbozor.ui.bottom_nav.profile.shop

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.core.models.shop.CreateShopRequest
import com.bizmiz.gulbozor.core.models.user.edit_user.UserEditRequest
import com.bizmiz.gulbozor.core.utils.Resource

class CreateShopViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getRegion: MutableLiveData<Resource<RegionData>> = MutableLiveData()
    val regionList: LiveData<Resource<RegionData>>
        get() = getRegion
    private val getCity: MutableLiveData<Resource<ArrayList<CityDataItem>>> = MutableLiveData()
    val cityData: LiveData<Resource<ArrayList<CityDataItem>>>
        get() = getCity
    private val createShop: MutableLiveData<Resource<CreateShopRequest>> = MutableLiveData()
    val resultShop: LiveData<Resource<CreateShopRequest>>
        get() = createShop
    private val updateShopId: MutableLiveData<Resource<Any>> = MutableLiveData()
    val resultShopId: LiveData<Resource<Any>>
        get() = updateShopId
    fun getRegion() {
        networkHelper.getRegion({
            getRegion.value = Resource.success(it)
        }, {
            getRegion.value = Resource.error(it)
        })
    }
    fun getCity(id:Int) {
        networkHelper.getCity(id,{
            getCity.value = Resource.success(it)
        }, {
            getCity.value = Resource.error(it)
        })
    }
    fun createShop(createShopRequest: CreateShopRequest) {
        networkHelper.createShop(createShopRequest, {
            createShop.value = Resource.success(it)
        }, {
            createShop.value = Resource.error(it)
        })
    }
    fun updateShopId(
        sellerId: Int,
        userEditRequest: UserEditRequest,
    ) {
        networkHelper.updateShopId(sellerId,userEditRequest, {
            updateShopId.value = Resource.success(it)
        }, {
            updateShopId.value = Resource.error(it)
        })
    }
}