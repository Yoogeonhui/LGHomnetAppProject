package com.ygh.org.homnetapp.Request;

import android.util.Log;
import android.util.Pair;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Selector;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RequestUtil {
    OkHttpClient client;
    Retrofit retrofit;
    HomnetService homnetService;
    String request_url = "http://14.37.21.38:8501";

    private RequestUtil() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new AddCookiesInterceptor())
                .addInterceptor(new ReceivedCookiesInterceptor())
                .build();
        retrofit = new Retrofit.Builder().baseUrl(request_url)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        homnetService = retrofit.create(HomnetService.class);
    }

    public void login(String id, String password, CallbackAfterRequest callback){
        Call<String> response = homnetService.sendMain();

        Callback<String> login_page = new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    return;
                }
                String body = response.body();
                boolean found = body.contains("location.href=\'../MAIN_Version.aspx\'");
                if(found){
                    callback.OnResponse(new Pair<>(true, "로그인 성공"));
                    return;
                }
                if(body.contains("아이디 또는 비밀번호가 일치하지 않습니다")){
                    callback.OnResponse(new Pair<>(false, "아이디 또는 비밀번호가 일치하지 않습니다"));
                    return;
                }
                callback.OnResponse(new Pair<>(false, "모르겠어요"));
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.OnResponse(new Pair<>(false, "로그인 페이지 오류입니다 " + t.getMessage()));
            }
        };

        Callback<String> main_page = new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    return;
                }

                if(response.body().contains("ActiveX 초기화")){
                    callback.OnResponse(new Pair<>(true, "벌써 로그인 되어있습니다."));
                    return;
                }

                Document doc = Jsoup.parse(response.body());
                Log.d("Response Body", response.body());
                Element elem_event_validation = Selector.selectFirst("#__EVENTVALIDATION", doc);
                String str_event_validation = elem_event_validation.attr("value");

                Element elem_view_state = Selector.selectFirst("#__VIEWSTATE", doc);
                String str_view_state = elem_view_state.attr("value");

                Element elem_view_state_generator = Selector.selectFirst("#__VIEWSTATEGENERATOR", doc);
                String str_view_state_generator = elem_view_state_generator.attr("value");

                Call<String> second_call = homnetService.sendLogin(
                        id,
                        password,
                        str_view_state_generator,
                        str_event_validation,
                        str_view_state,
                        "0","0"
                );

                second_call.enqueue(login_page);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                callback.OnResponse(new Pair<>(false, "메인 페이지 오류입니다 " + t.getMessage()));
            }
        };
        response.enqueue(main_page);
    }

    public void sendRequest(String msg, Callback<String> callback){
        Map<String,String> map = new HashMap<>();
        map.put("strMsg", msg);
        Call<String> response = homnetService.sendRequest(map);
        response.enqueue(callback);
    }

    public static RequestUtil getInstance() {
        return LazyHolder.ourInstance;
    }

    private static class LazyHolder {
        private static final RequestUtil ourInstance = new RequestUtil();
    }

}
