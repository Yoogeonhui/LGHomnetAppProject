package com.ygh.org.homnetapp;


import android.app.Activity;
import android.content.SharedPreferences;

public class SharedPreferenceBase {
    private SharedPreferences preferences;
    private final static String PREFERENCE_NAME = "HomnetPref";

    private static class LazyHolder {
        private static final SharedPreferenceBase ourInstance = new SharedPreferenceBase();
    }
    public static SharedPreferenceBase getInstance() {
        return SharedPreferenceBase.LazyHolder.ourInstance;
    }
    private SharedPreferenceBase(){
        preferences = App.get().getSharedPreferences(PREFERENCE_NAME, Activity.MODE_PRIVATE);
    }

    public SharedPreferences getPreferences(){
        return preferences;
    }
    public SharedPreferences.Editor getEditor(){
        return preferences.edit();
    }
}
