package com.ygh.org.homnetapp.Request;

import android.util.Pair;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RequestUtil {
    OkHttpClient client;
    Retrofit retrofit;
    HomnetService homnetService;
    String request_url = "http://14.37.21.38:8501";

    private RequestUtil() {
        client = new OkHttpClient();
        client.interceptors().add(new AddCookiesInterceptor());
        client.interceptors().add(new ReceivedCookiesInterceptor());

        retrofit = new Retrofit.Builder().baseUrl(request_url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        homnetService = retrofit.create(HomnetService.class);
    }

    public void login(String id, String password, CallbackAfterRequest callback){

    }

    public void sendRequest(String msg, Callback<String> callback){
        Data param = new Data(msg);
        Call<String> response = homnetService.sendRequest(param);
        response.enqueue(
            callback
        );
    }

    public static RequestUtil getInstance() {
        return LazyHolder.ourInstance;
    }

    private static class LazyHolder {
        private static final RequestUtil ourInstance = new RequestUtil();

    }

}
