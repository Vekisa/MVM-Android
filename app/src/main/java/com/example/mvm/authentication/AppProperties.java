package com.example.mvm.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mvm.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
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
    public SharedPreferences.Editor editor;

    //samo ybog cice mice
    public String token = null;
    public String serverIp = "http://192.168.0.11";
    public String serverPort = "8081";
    public String fcmToken = null;

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

    public Request.Builder getRequest(){
        return new Request.Builder()
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==");
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
                editor = sharedPreferences.edit();

                editor.putString("access_token",token);
                editor.putString("username",username);

                editor.apply();



                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        fcmToken = instanceIdResult.getToken();
                        Log.d("FIREBASE", "FCM Registration Token: " + fcmToken);





                    Request request2 = new Request.Builder()
                                .url(AppProperties.getInstance().getServerUrl() + "/auth/sub?token="+fcmToken)
                                .build();

                        Response response2 = null;
                        try {
                            response2 = getHttpClient().newCall(request2).execute();
                            if (response2.code()==200){
                                Log.i("Firebase ","Sub ");

                                editor.putBoolean("sub",true);
                                editor.apply();
                            }
                            Log.i("Firebase ",response2.body().string());
                            Log.i("Firebase ", String.valueOf(response2.code()));



                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
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

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                fcmToken = instanceIdResult.getToken();
                Log.d("FIREBASE", "FCM Registration Token: " + fcmToken);


                Request request = new Request.Builder()
                        .url(AppProperties.getInstance().getServerUrl() + "/auth/unSub?token="+fcmToken)
                        .build();

                Response response = null;
                try {
                    response = getHttpClient().newCall(request).execute();
                    if (response.code()==200){
                        Log.i("Firebase ","Un Sub ");
                    }
                    Log.i("Firebase ",response.body().string());
                    Log.i("Firebase ", String.valueOf(response.code()));

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });






    }

}
