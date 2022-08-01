package com.bizmiz.gulbozor.core.models

data class SmsRequestData(
    val mobile_phone: String,
    val message: String,
    val from: String,
    val callback_url: String
)