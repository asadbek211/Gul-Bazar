package com.bizmiz.gulbozor.ui.bottom_nav.categories.mainCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.category.ByParentIDItem
import com.bizmiz.gulbozor.core.utils.Resource

class CategoryVM(private val networkHelper: NetworkHelper) : ViewModel() {

    private val getChildCat: MutableLiveData<Resource<List<ByParentIDItem>>> = MutableLiveData()
    val childCat: LiveData<Resource<List<ByParentIDItem>>>
        get() = getChildCat

    private val getChildCat1: MutableLiveData<Resource<List<ByParentIDItem>>> = MutableLiveData()
    val childCat1: LiveData<Resource<List<ByParentIDItem>>>
        get() = getChildCat1

    private val getChildCat2: MutableLiveData<Resource<List<ByParentIDItem>>> = MutableLiveData()
    val childCat2: LiveData<Resource<List<ByParentIDItem>>>
        get() = getChildCat2

    private val getChildCat3: MutableLiveData<Resource<List<ByParentIDItem>>> = MutableLiveData()
    val childCat3: LiveData<Resource<List<ByParentIDItem>>>
        get() = getChildCat3

    private val getChildCat4: MutableLiveData<Resource<List<ByParentIDItem>>> = MutableLiveData()
    val childCat4: LiveData<Resource<List<ByParentIDItem>>>
        get() = getChildCat4

    private val getChildCat5: MutableLiveData<Resource<List<ByParentIDItem>>> = MutableLiveData()
    val childCat5: LiveData<Resource<List<ByParentIDItem>>>
        get() = getChildCat5

    private val getChildCat6: MutableLiveData<Resource<List<ByParentIDItem>>> = MutableLiveData()
    val childCat6: LiveData<Resource<List<ByParentIDItem>>>
        get() = getChildCat6

    fun getByPArentCatId(id: Int) {
        networkHelper.getByParentCatID(
            id = id,
            {
                getChildCat.value = Resource.success(it)
            }, {
                getChildCat.value = Resource.error(it)
            }
        )
    }

    fun getByPArentCatId1(id: Int) {
        networkHelper.getByParentCatID(
            id = id,
            {
                getChildCat1.value = Resource.success(it)
            }, {
                getChildCat1.value = Resource.error(it)
            }
        )
    }

    fun getByPArentCatId2(id: Int) {
        networkHelper.getByParentCatID(
            id = id,
            {
                getChildCat2.value = Resource.success(it)
            }, {
                getChildCat2.value = Resource.error(it)
            }
        )
    }

    fun getByPArentCatId3(id: Int) {
        networkHelper.getByParentCatID(
            id = id,
            {
                getChildCat3.value = Resource.success(it)
            }, {
                getChildCat3.value = Resource.error(it)
            }
        )
    }

    fun getByPArentCatId4(id: Int) {
        networkHelper.getByParentCatID(
            id = id,
            {
                getChildCat4.value = Resource.success(it)
            }, {
                getChildCat4.value = Resource.error(it)
            }
        )
    }

    fun getByPArentCatId5(id: Int) {
        networkHelper.getByParentCatID(
            id = id,
            {
                getChildCat5.value = Resource.success(it)
            }, {
                getChildCat5.value = Resource.error(it)
            }
        )
    }

    fun getByPArentCatId6(id: Int) {
        networkHelper.getByParentCatID(
            id = id,
            {
                getChildCat6.value = Resource.success(it)
            }, {
                getChildCat6.value = Resource.error(it)
            }
        )
    }

}