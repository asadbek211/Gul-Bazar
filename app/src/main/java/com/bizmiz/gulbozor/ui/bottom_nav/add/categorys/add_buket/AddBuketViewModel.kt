package com.bizmiz.gulbozor.ui.bottom_nav.add.categorys.add_buket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.utils.Resource
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.*
import com.bizmiz.gulbozor.core.models.category.ByParentIDItem
import com.bizmiz.gulbozor.ui.model.ImageResponseData
import okhttp3.MultipartBody


class AddBuketViewModel(private val networkHelper: NetworkHelper) : ViewModel() {
    private val setImage: MutableLiveData<Resource<ImageResponseData>> = MutableLiveData()
    val result: LiveData<Resource<ImageResponseData>>
        get() = setImage
    private val setAnnounce: MutableLiveData<Resource<AnnounceBaseResponse>> = MutableLiveData()
    val resultAnnounce: LiveData<Resource<AnnounceBaseResponse>>
        get() = setAnnounce
    private val getRegion: MutableLiveData<Resource<RegionData>> = MutableLiveData()
    val regionList: LiveData<Resource<RegionData>>
        get() = getRegion
    private val getCity: MutableLiveData<Resource<ArrayList<CityDataItem>>> = MutableLiveData()
    val cityData: LiveData<Resource<ArrayList<CityDataItem>>>
        get() = getCity
    private val getParentCategory: MutableLiveData<Resource<List<ByParentIDItem>>> = MutableLiveData()
    val parentCategory: LiveData<Resource<List<ByParentIDItem>>> get() = getParentCategory
    fun addFlower(
        img1: MultipartBody.Part?,
        img2: MultipartBody.Part?,
        img3: MultipartBody.Part?,
        img4: MultipartBody.Part?,
        img5: MultipartBody.Part?,
        img6: MultipartBody.Part?,
        img7: MultipartBody.Part?,
        img8: MultipartBody.Part?,
    ) {
        networkHelper.addFlowerImage(img1,img2,img3,img4, img5, img6, img7, img8, {
            setImage.value = Resource.success(it)
        }, {
            setImage.value = Resource.error(it)
        })
    }
    fun setAnnounce(announceRequestData: AnnounceRequestData) {
        networkHelper.setAnnounce(announceRequestData, {
            setAnnounce.value = Resource.success(it)
        }, {
            setAnnounce.value = Resource.error(it)
        })
    }
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
    fun getParentCatByID(id: Int) {
        networkHelper.getByParentCatID(
            id = id, {
                getParentCategory.value = Resource.success(it)
            }, {
                getParentCategory.value = Resource.error(it)
            }
        )
    }
}