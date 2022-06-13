package com.bizmiz.gulbozor.core.caches;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginHelper {

    private static LoginHelper helper;
    private SharedPreferences preferences;

    private LoginHelper(Context context) {
        //context
        preferences = context.getSharedPreferences("savePhone", Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (helper == null) {
            helper = new LoginHelper(context);
        }
    }

    public static LoginHelper getHelper() {
        return helper;
    }

    public Boolean getLogin() {
        return preferences.getBoolean("login", false);
    }

    public void setLogin(Boolean phoneNumber) {
        preferences.edit().putBoolean("login", phoneNumber).apply();
    }
}
