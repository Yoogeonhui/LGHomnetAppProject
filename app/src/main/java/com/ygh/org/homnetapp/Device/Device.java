package com.ygh.org.homnetapp.Device;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ygh.org.homnetapp.App;
import com.ygh.org.homnetapp.Request.CallbackMap;
import com.ygh.org.homnetapp.Request.RequestUtil;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class Device {
    String deviceName;
    CardView cardView;
    TextView textView;
    LinearLayout linearLayout;

    public Device(Context activityContext){
        cardView = new CardView(activityContext);
        linearLayout = new LinearLayout(activityContext);
        cardView.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, CardView.LayoutParams.WRAP_CONTENT));
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView = new TextView(activityContext);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(textView);
        cardView.addView(linearLayout);
    }

    public void sendRequest(Callback<String> callback){
        RequestUtil req = RequestUtil.getInstance();
        req.sendRequest("mtig&"+deviceName, callback);
    }

    public void updateDevice(CallbackMap callbackMap){
        sendRequest(new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                if(res.charAt(0)=='\"')
                    res=res.substring(1);
                if(res.charAt(res.length()-1) == '\"')
                    res=res.substring(0, res.length()-1);
                Log.d("MESSAGE", res);
                Map<String, String> map = new HashMap<>();
                for(String s:res.split("&")){
                    if(!s.contains("=")) continue;
                    String[] splited = s.split("=");
                    map.put(splited[0], splited[1]);
                }
                callbackMap.onResponse(map);
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(App.get().getApplicationContext(), "에러: "+t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public abstract String getType();
}
