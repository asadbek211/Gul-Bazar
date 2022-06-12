package com.bizmiz.gulbozor.core.caches;

import android.content.Context;
import android.content.SharedPreferences;

public class AppCache {
    //Keys
    private final static String IS_FIRST_OPEN = "isFirstOpen";
    private final static String USER_TOKEN = "token";
    private final static String USER_PASSWORD = "password";
    private final static String USER_ID = "id";
    private static AppCache appCache;
    private SharedPreferences preferences;

    private AppCache(Context context) {
        //context
        preferences = context.getSharedPreferences("AppCache", Context.MODE_PRIVATE);
    }

    public static void init(Context context) {
        if (appCache == null) {
            appCache = new AppCache(context);
        }
    }

    public static AppCache getHelper() {
        return appCache;
    }


    public String getToken() {
        return preferences.getString(USER_TOKEN, "");
    }

    public void setToken(String token) {

        preferences.edit().putString(USER_TOKEN, token).apply();

    }

    public int getUserId() {
        return preferences.getInt(USER_ID, 0);
    }

    public void setUserId(int id) {
        preferences.edit().putInt(USER_ID, id).apply();

    }

    public String getPassword() {
        return preferences.getString(USER_PASSWORD, "");
    }

    public void setPassword(String password) {
        preferences.edit().putString(USER_PASSWORD, password).apply();
    }

    public String getPhoneNumber() {
        return preferences.getString("phone", "1234567");
    }

    public void setPhoneNumber(String s) {
        preferences.edit().putString("login", s).apply();
    }
}