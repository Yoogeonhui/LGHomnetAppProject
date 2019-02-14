package com.ygh.org.homnetapp;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ygh.org.homnetapp.Request.RequestUtil;

public class LoginActivity extends AppCompatActivity {

    EditText editID, editPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences saved = SharedPreferenceBase.getInstance().getPreferences();

        String sessionID = saved.getString("sessionID", null);
        String userID = saved.getString("userID", null);
        String userPwd = saved.getString("userPwd", null);

        if(sessionID != null){
            RequestUtil req = RequestUtil.getInstance();

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
        //Pair<Boolean, String> res = req.login(inputID, inputPwd);

    }
}
