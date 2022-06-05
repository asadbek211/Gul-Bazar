package com.bizmiz.gulbozor.core.caches;

import android.content.Context;
import android.content.SharedPreferences;

public class PhoneNumberHelper {

    private static PhoneNumberHelper helper;
    private SharedPreferences preferences;

    private PhoneNumberHelper(Context context) {
        //context
        preferences = context.getSharedPreferences("savePhone", Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (helper == null) {
            helper = new PhoneNumberHelper(context);
        }
    }

    public static PhoneNumberHelper getHelper() {
        return helper;
    }

    public String getPhoneNumber() {
        return preferences.getString("number", "");
    }

    public void setPhoneNumber(String phoneNumber) {
        preferences.edit().putString("number", phoneNumber).apply();
    }
}
