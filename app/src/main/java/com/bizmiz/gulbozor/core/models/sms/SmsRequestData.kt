package com.bizmiz.gulbozor.core.models.sms

data class SmsRequestData(
    val country: String,
    val message_id: String,
    val phone_number: String,
    val sms_count: String,
    val status: String,
    val status_date: String,
    val user_sms_id: String
)