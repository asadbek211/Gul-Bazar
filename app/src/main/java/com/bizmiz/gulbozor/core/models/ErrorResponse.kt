package com.bizmiz.gulbozor.core.models

data class ErrorResponse(
    val massage: String,
    val success: Boolean,
    val token: Any,
    val user_id: Any
)