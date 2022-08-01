package com.bizmiz.gulbozor.ui.bottom_nav.categories.shops_category.oneShop.model

data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: Sort,
    val unpaged: Boolean
)