package com.ygh.org.homnetapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ygh.org.homnetapp.Request.CallbackAfterRequest;
import com.ygh.org.homnetapp.Request.RequestUtil;

import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editID, editPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences saved = SharedPreferenceBase.getInstance().getPreferences();

        String userID = saved.getString("userID", null);
        String userPwd = saved.getString("userPwd", null);

        editID = findViewById(R.id.loginID);
        editPwd = findViewById(R.id.loginPwd);

        editID.setText(userID);
        editPwd.setText(userPwd);


        RequestUtil req = RequestUtil.getInstance();
        Context currentContext = this;
        req.sendRequest("LIST", new Callback<String>(){

            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TAG", response.body());
                if(!response.body().equals("\"LOGOUT\"") && !response.body().equals("\"Object reference not set to an instance of an object.\"")){
                    Intent controlIntent = new Intent(currentContext, ControlActivity.class);
                    startActivity(controlIntent);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "에러: "+t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    protected void onStop(){
        super.onStop();
    }

    public void loginButtonOnClick(View view) {
        String inputID = editID.getText().toString();
        String inputPwd = editPwd.getText().toString();
        Context context = getApplicationContext();
        if(inputID.isEmpty()){
            Toast.makeText(context, "아이디란이 비어있습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        if(inputPwd.isEmpty()){
            Toast.makeText(context, "페스워드란이 비어있습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        RequestUtil req = RequestUtil.getInstance();
        CallbackAfterRequest callbackAfterRequest = (Pair<Boolean, String> result)->{
            Toast.makeText(context, result.second, Toast.LENGTH_LONG).show();
            if(result.first){
                Intent intent = new Intent(this, ControlActivity.class);
                startActivity(intent);
            }
        };
        req.login(inputID, inputPwd, callbackAfterRequest);

    }
}
