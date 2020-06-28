package com.example.mvm.authentication;

import okhttp3.OkHttpClient;

public class AppProperties {

    private static AppProperties instance = null;

    //samo ybog cice mice
    public String token = null;
    public String serverIp = "http://192.168.1.21";
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
}
