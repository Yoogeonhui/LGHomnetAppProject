package com.ygh.org.homnetapp.Device;

import android.content.Context;
import android.widget.Toast;

import com.ygh.org.homnetapp.App;
import com.ygh.org.homnetapp.Request.RequestUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Boiler extends Device{

    public void updateDevice(){
        sendRequest(new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(App.get().getApplicationContext(), "에러: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public Boiler(String deviceName, Context activityContext){
        super(activityContext);
        this.deviceName = deviceName;
    }

    public void setTemp(int temp){

    }

    @Override
    public String getType() {
        return "Boiler";
    }
}
