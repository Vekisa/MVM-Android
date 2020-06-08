package com.example.mvm.services;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.User;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class UserService {
    public User findLoggedIn(){
        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/auth/current_user")
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();
        Response response;
        User currentUser = null;
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            currentUser = new GsonBuilder().create().fromJson(response.body().string(), User.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return currentUser;
    }
}
