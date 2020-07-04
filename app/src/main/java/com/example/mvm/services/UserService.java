package com.example.mvm.services;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserService {
    public static User findLoggedIn(){
        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/auth/current_user")
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();
        Response response;
        Object currentUser = null;
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            Gson gson = new Gson();
            currentUser = gson.fromJson(response.body().string(), Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();
        LinkedTreeMap map = (LinkedTreeMap) currentUser;
        user.setId(map.get("id").toString());
        user.setName(map.get("name").toString());
        user.setCategory(map.get("category").toString());
        user.setPassword(map.get("password").toString());
        user.setUsername(map.get("username").toString());
        return user;
    }

    public static User findById(String id){
        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/auth/findById/" + id)
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();
        Response response;
        Object currentUser = null;
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            Gson gson = new Gson();
            currentUser = gson.fromJson(response.body().string(), Object.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        User user = new User();
        LinkedTreeMap map = (LinkedTreeMap) currentUser;
        user.setId(map.get("id").toString());
        user.setName(map.get("name").toString());
        user.setCategory(map.get("category").toString());
        user.setPassword(map.get("password").toString());
        user.setUsername(map.get("username").toString());
        return user;
    }

    public static void save(User user){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", user.getUsername());
            jsonObject.put("name", user.getName());
            jsonObject.put("password", user.getPassword());
            jsonObject.put("category", user.getCategory());
            jsonObject.put("id", user.getId());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/auth/save")
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .post(body)
                .build();
        try {
            Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
