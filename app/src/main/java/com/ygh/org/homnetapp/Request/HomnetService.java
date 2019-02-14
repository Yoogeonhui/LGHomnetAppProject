package com.ygh.org.homnetapp.Request;

import com.ygh.org.homnetapp.Request.Data;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface HomnetService {
    @Headers("Ajax-method: CallAjaxMonitoring")
    @POST("/ajaxpro/LGHomNetSite.Main,App_Web_x-3hidpr.ashx")
    Call<String> sendRequest(@Body Data param);

    @POST("/Member/Login.aspx")
    Call<String> sendLogin(
        @Field("TextBoxId") String userID,
        @Field("TextBoxPwd") String userPwd
    );
}