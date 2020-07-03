package com.example.mvm.services;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.Comment;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentService {
    public static String save(Comment comment) throws JSONException, IOException {
        JSONObject json = new JSONObject();
        json.put("userImage", comment.getUserImage());
        json.put("userName", comment.getUserName());
        json.put("content", comment.getContent());
        json.put("dateTime", comment.getDateTime());
        json.put("userUsername", comment.getUserUsername());
        json.put("discussionId", comment.getDiscussionId());
        json.put("id", comment.getId());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Request request = new Request.Builder().url(AppProperties.getInstance().getServerUrl() + "/comment/save")
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .post(body)
                .build();
        Response response = null;
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                System.out.println("Komentar je sacuvan");
            }
            else{
                System.out.println("Greska pri sacuvavanju komentara");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return response.body().string();
    }

    public static List<String> getImages(String id){
        Request request = new Request.Builder().url(AppProperties.getInstance().getServerUrl() + "/comment/getImages/" + id).build();
        Response response = null;
        List<String> images = new ArrayList<>();
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                images = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(),  String[].class));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return images;
    }
}
