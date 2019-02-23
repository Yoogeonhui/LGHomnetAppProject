package com.ygh.org.homnetapp.Device;

import com.ygh.org.homnetapp.Request.RequestUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Boiler implements Device{
    private String deviceID;

    public Boiler(String deviceID){
        this.deviceID = deviceID;
    }

    public void setTemp(int temp){

    }

    @Override
    public void getInfo() {
        RequestUtil req = RequestUtil.getInstance();
        req.sendRequest("mtig&"+deviceID, new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public String getType() {
        return "Boiler";
    }
}
