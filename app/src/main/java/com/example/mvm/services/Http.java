package com.example.mvm.services;

import android.app.Application;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import cz.msebera.android.httpclient.Header;

public class Http {

    private static Http instance = null;
    private AsyncHttpClient client;
    private Object context;
    private Application application;

    public static Http getInstance(Application application){
        if(instance == null) {
            instance = new Http(application);
        }
        return instance;
    }

    private Http(Application application){
        this.application = application;
        client = new AsyncHttpClient();
    }

    public void get(String url) throws IOException {
         client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode == 404)
                    Toast.makeText(application.getBaseContext(),"Ne postoji server!",
                            Toast.LENGTH_SHORT).show();
                else if(responseBody == null)
                    Toast.makeText(application.getBaseContext(),"Neka greska!",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(application.getBaseContext(),new String(responseBody),
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

    public void post(String url, Object object) throws IOException {
        client.addHeader("Accept", "application/json");
        client.addHeader("Content-type", "application/json;charset=utf-8");
        client.post(url, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("On post success");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                if(statusCode == 404)
                    Toast.makeText(application.getBaseContext(),"Ne postoji server!",
                            Toast.LENGTH_SHORT).show();
                else if(responseBody == null)
                    Toast.makeText(application.getBaseContext(),"Neka greska!",
                            Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(application.getBaseContext(),new String(responseBody),
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }
}
