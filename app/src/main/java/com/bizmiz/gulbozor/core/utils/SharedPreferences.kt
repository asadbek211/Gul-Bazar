package com.bizmiz.gulbozor.core.utils

import android.content.Context

class SharedPreferences(context: Context) {
    private val pref = context.getSharedPreferences("key", Context.MODE_PRIVATE)

    fun isSavedLogin(isSavedLogin: Boolean) {
        val editor = pref.edit()
        editor.putBoolean("isSavedLogin", isSavedLogin)
        editor.apply()
    }

    fun getSavedLogin(): Boolean {
        return pref.getBoolean("isSavedTextInstance", false)
    }

    fun isSavedPhoneNumber(isSavedPhone: String) {
        val editor = pref.edit()
        editor.putString("isSavedPhone", isSavedPhone)
        editor.apply()
    }

    fun getSavedPhoneNumber(): String? {
        return pref.getString("isSavedPhone", null)
    }
}