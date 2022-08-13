package com.bizmiz.gulbozor.core.models.sms

data class SmsTokenResponse(
    val massage: Any,
    val number: Int,
    val `object`: String,
    val success: Boolean
)