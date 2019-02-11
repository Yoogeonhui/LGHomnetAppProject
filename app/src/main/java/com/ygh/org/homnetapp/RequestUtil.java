package com.ygh.org.homnetapp;

import android.content.Context;
import android.util.Pair;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestUtil {
    private static class LazyHolder {
        private static final RequestUtil ourInstance = new RequestUtil();
    }

    public String session = null, request_url = "http://14.37.21.38:8501/ajaxpro/LGHomNetSite.Main,App_Web_x-3hidpr.ashx", login_url = "http://14.37.21.38:8501/Member/Login.aspx";

    public void setSession(String session){
        if(session != null)
            this.session = session;
    }

    public Pair<Boolean, String> login(String id, String password){

    }

    public Pair<Boolean, String> isValid(){
        if(session == null) return Pair.create(false, "세션이 입력되지 않았습니다.");
        try {
            String res = sendRequest("LIST");
            if(res.equals("\"LOGOUT\"")){
                return Pair.create(false, "로그인 되어있지 않습니다.");
            }
        }catch(Exception e){
            return Pair.create(false,  "에러가 발생하였습니다 : "+e.toString());
        }
        return Pair.create(true,"성공적으로 로그인 되었습니다.");
    }

    public String sendRequest(String msg) throws Exception{
        if(session == null) throw new Exception("세션입력(로그인)되지 않은 상태에서 요청을 전송하고 있습니다.");
        URL url = new URL(request_url);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Cookie", session);
        con.setRequestProperty("Ajax-method", "CallAjaxMonitoring");
        JSONObject strMsg = new JSONObject();
        strMsg.put("strMsg", msg);
        OutputStream os = con.getOutputStream();
        os.write(strMsg.toString().getBytes("UTF-8"));
        os.close();

        StringBuilder sb = new StringBuilder();
        int HttpResult = con.getResponseCode();
        if(HttpResult == HttpURLConnection.HTTP_OK){
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
        }
        return sb.toString();
    }
    public static RequestUtil getInstance() {
        return LazyHolder.ourInstance;
    }

    private RequestUtil() { }
}
