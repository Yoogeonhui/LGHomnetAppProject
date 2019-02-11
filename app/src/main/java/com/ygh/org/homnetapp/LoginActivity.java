package com.ygh.org.homnetapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText editID, editPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        String preference_name = getResources().getString(R.string.preference_name);
        SharedPreferences saved = getSharedPreferences(preference_name, 0);
        String sessionID = saved.getString("sessionID", null);
        String userID = saved.getString("userID", null);
        String userPwd = saved.getString("userPwd", null);

        if(sessionID != null){
            RequestUtil req = RequestUtil.getInstance();
            req.setSession(sessionID);
            Pair<Boolean, String> res = req.isValid();
            if(res.first){
                //로그인 완료되어있는 상태

            }else{
                Toast.makeText(getApplicationContext(), res.second, Toast.LENGTH_LONG).show();
            }
        }

        editID = findViewById(R.id.loginID);
        editPwd = findViewById(R.id.loginPwd);

        editID.setText(userID);
        editPwd.setText(userPwd);
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
        Pair<Boolean, String> res = req.login(inputID, inputPwd);

    }
}
