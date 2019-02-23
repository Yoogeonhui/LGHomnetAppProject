package com.ygh.org.homnetapp.Request;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface HomnetService {
    @Headers("Ajax-method: CallAjaxMonitoring")
    @POST("/ajaxpro/LGHomNetSite.Main,App_Web_x-3hidpr.ashx")
    Call<String> sendRequest(@Body Map<String, String> param);

    @FormUrlEncoded
    @POST("/Member/Login.aspx")
    Call<String> sendLogin(
        @Field("TextBoxId") String userID,
        @Field("TextBoxPwd") String userPwd,
        @Field("__VIEWSTATEGENERATOR") String viewStateGenerator,
        @Field("__EVENTVALIDATION") String eventValidation,
        @Field("__VIEWSTATE") String viewState,
        @Field("ImageButton1.x") String imageButtonX,
        @Field("ImageButton1.y") String imageButtonY
    );

    @POST("/Member/Login.aspx")
    Call<String> sendMain();

    @POST("/Member/Logout.aspx")
    Call<String> sendLogout();
}
