package com.ygh.org.homnetapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ygh.org.homnetapp.Request.RequestUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ControlActivity extends AppCompatActivity {

    RequestUtil req;
    private void parseAndAddView(){
        req.sendRequest("LIST", new Callback<String>(){
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TAG",response.body());

                String response_str = response.body();
                String[] response_split = response_str.split("|");
                for(int i=1;i<response_split.length;i++){
                    String current = response_split[i];
                    String[] current_split = current.split(";");
                    for(int j=0;j<current_split.length;j++){

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
        req = RequestUtil.getInstance();
        parseAndAddView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
