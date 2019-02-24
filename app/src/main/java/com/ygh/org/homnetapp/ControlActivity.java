package com.ygh.org.homnetapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.ygh.org.homnetapp.Device.Boiler;
import com.ygh.org.homnetapp.Device.Device;
import com.ygh.org.homnetapp.Device.Light;
import com.ygh.org.homnetapp.Device.Valve;
import com.ygh.org.homnetapp.Device.Vent;
import com.ygh.org.homnetapp.Request.RequestUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControlActivity extends AppCompatActivity {

    List<Device> deviceList;
    RequestUtil req;
    Context context;

    private void parseAndAddView(){
        req.sendRequest("LIST", new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TAG",response.body());

                String response_str = response.body();
                Log.d("ResponseSTR", response_str);
                String[] response_split = response_str.split("\\|");
                for(int i=1;i<response_split.length;i++){
                    String current = response_split[i];
                    String[] current_split = current.split(";");
                    String deviceName = current_split[0];
                    if(!current_split[1].equals("0")){
                        int j;
                        for(j=0;j<deviceName.length();j++){
                            char current_char = deviceName.charAt(j);
                            if('0'<= current_char&& current_char<='9'){
                                break;
                            }
                        }
                        Device deviceToAdd = null;
                        String deviceType = deviceName.substring(0, j).toLowerCase();
                        switch(deviceType){
                            case "lt":
                                deviceToAdd = new Light(deviceName, context);
                                break;
                            case "dt":
                                deviceToAdd = new Boiler(deviceName, context);
                                break;
                            case "gv":
                                deviceToAdd = new Valve(deviceName, context);
                                break;
                            case "vent":
                                deviceToAdd = new Vent(deviceName, context);
                                break;
                        }
                        deviceList.add(deviceToAdd);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "에러: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        context = this;
        deviceList = new ArrayList<>();
        req = RequestUtil.getInstance();
        parseAndAddView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
