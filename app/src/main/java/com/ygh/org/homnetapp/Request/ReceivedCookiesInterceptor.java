package com.ygh.org.homnetapp.Request;

import android.content.SharedPreferences;

import com.ygh.org.homnetapp.SharedPreferenceBase;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));
            // Preference에 cookies를 넣어주는 작업을 수행
            SharedPreferences.Editor pref = SharedPreferenceBase.getInstance().getEditor();
            pref.putStringSet("Cookie", cookies);
            pref.apply();
        }
        return originalResponse;
    }
}

