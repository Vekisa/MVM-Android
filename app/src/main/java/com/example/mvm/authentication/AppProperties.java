package com.example.mvm.authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.mvm.CategoryActivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AppProperties {

    private static AppProperties instance = null;

    private final String prefName = "MyPref";


    public SharedPreferences sharedPreferences = null;

    //samo ybog cice mice
    public String token = null;
    public String serverIp = "http://192.168.0.11";
    public String serverPort = "8081";

    private OkHttpClient http;

    protected AppProperties(){
        http = new OkHttpClient.Builder()
                .addNetworkInterceptor(new AuthInterceptor())
                .addInterceptor(new AuthInterceptor()).build();
    }

    public static synchronized AppProperties getInstance() {
        if(null == instance){
            instance = new AppProperties();
        }
        return instance;
    }

    public String getServerUrl(){
        return serverIp + ":" + serverPort;
    }

    public OkHttpClient getHttpClient(){
        return http;
    }

    public Response login(Context context, String username, String pass){

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username)
                .addFormDataPart("password", pass)
                .addFormDataPart("grant_type", "password")
                .build();

        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/oauth/token")
                .post(requestBody)
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();

        Response response = null;
        try {
            response = getHttpClient().newCall(request).execute();

            if(response.code() == 200){
                Gson gson = new Gson();
                LoginResponse responseResult =gson.fromJson(response.body().string(), LoginResponse.class);
                token = responseResult.getAccess_token();

                if(sharedPreferences==null)
                    sharedPreferences = context.getApplicationContext().getSharedPreferences(prefName,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("access_token",token);
                System.out.println(token);
                editor.apply();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;

    }

    public boolean checkSaveCred(Context context){

        if(sharedPreferences==null)
            sharedPreferences = context.getApplicationContext().getSharedPreferences(prefName,Context.MODE_PRIVATE);

        String tempToken = sharedPreferences.getString("access_token","");

        System.out.println(tempToken);

        if ( tempToken!="" ){
            token = tempToken;
            System.out.println(tempToken);
            return true;
        }

        return false;


    }

    public void reset(Context context){
        if(sharedPreferences==null)
            sharedPreferences = context.getApplicationContext().getSharedPreferences(prefName,Context.MODE_PRIVATE);

        token = null;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
