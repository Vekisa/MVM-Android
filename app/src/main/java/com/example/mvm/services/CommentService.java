package com.example.mvm.services;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.Comment;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CommentService {
    public static void save(Comment comment) throws JSONException {
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
    }
}
