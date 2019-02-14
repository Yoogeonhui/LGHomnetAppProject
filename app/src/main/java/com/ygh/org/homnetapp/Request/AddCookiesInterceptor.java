package com.ygh.org.homnetapp.Request;

import android.content.SharedPreferences;

import com.ygh.org.homnetapp.SharedPreferenceBase;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        SharedPreferences pref = SharedPreferenceBase.getInstance().getPreferences();
        Set<String> set_pref = pref.getStringSet("Cookie", new HashSet<String>());
        for (String cookie: set_pref){
            builder.addHeader("Cookie", cookie);
        }
        return chain.proceed(builder.build());
    }
}
