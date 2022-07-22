package com.bizmiz.gulbozor.core.models

data class BaseResponse<T>(
    val massage: String,
    val `object`: T,
    val success: Boolean
)