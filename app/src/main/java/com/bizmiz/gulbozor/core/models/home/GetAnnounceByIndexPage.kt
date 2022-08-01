package com.bizmiz.gulbozor.core.models.home

import com.bizmiz.gulbozor.core.models.AnnounceResponseData

data class GetAnnounceByIndexPage(
    val content: List<AnnounceResponseData>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)