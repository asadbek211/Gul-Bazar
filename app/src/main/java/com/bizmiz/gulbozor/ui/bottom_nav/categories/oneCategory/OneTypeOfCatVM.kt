package com.bizmiz.gulbozor.ui.bottom_nav.categories.oneCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bizmiz.gulbozor.core.helper.NetworkHelper
import com.bizmiz.gulbozor.core.models.category.ByCategoryID
import com.bizmiz.gulbozor.core.models.slideReklama.ReklamaImages
import com.bizmiz.gulbozor.core.utils.Resource

class OneTypeOfCatVM(private val networkHelper: NetworkHelper) : ViewModel() {
    private val getParentCategory: MutableLiveData<Resource<ByCategoryID>> =
        MutableLiveData()
    val parentCategory: LiveData<Resource<ByCategoryID>> get() = getParentCategory

    private val byDepartment: MutableLiveData<Resource<ByCategoryID>> =
        MutableLiveData()
    val department: LiveData<Resource<ByCategoryID>>
        get() = byDepartment

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


    fun getByCategoryID(id: Int, page: Int) {
        networkHelper.getByCategoryIDList(
            categoryId = id, page = page, {
                getParentCategory.value = Resource.success(it)
            }, {
                getParentCategory.value = Resource.error(it)
            }
        )
    }

    fun getDepartment(departmentId: Int, page: Int) {
        networkHelper.getByDepartmentList(
            departmentId,
            page = page, {
                byDepartment.value = Resource.success(it)
            }, {
                byDepartment.value = Resource.error(it)
            })
    }

}