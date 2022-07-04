package com.bizmiz.gulbozor.core.models.category

data class ByParentIDItem(
    val id: Int,
    val name: String,
    val parentCategoryId: Int
)