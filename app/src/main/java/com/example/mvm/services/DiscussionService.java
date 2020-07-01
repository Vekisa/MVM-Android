package com.example.mvm.services;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.Comment;
import com.example.mvm.model.Discussion;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DiscussionService {
    public static void save(Discussion discussion) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("userImage", discussion.getUserImage());
        json.put("userName", discussion.getUserName());
        json.put("title", discussion.getTitle());
        json.put("content", discussion.getContent());
        json.put("dateTime", discussion.getDateTime());
        json.put("forumId", discussion.getForumId());
        json.put("userUsername", discussion.getUserUsername());
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
        Request request = new Request.Builder().url(AppProperties.getInstance().getServerUrl() + "/discussion/save")
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .post(body)
                .build();
        Response response = null;
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                System.out.println("Diskusija je sacuvana");
            }
            else{
                System.out.println("Greska pri sacuvavanju diskusije");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<Comment> getComments(String discussionId){
        Request request = new Request.Builder().url(AppProperties.getInstance().getServerUrl() + "/discussion/getComments/" + discussionId).build();
        Response response = null;
        List<Comment> comments = new ArrayList<>();
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                List<Object> objs = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(),  Object[].class));
                for(Object obj : objs){
                    LinkedTreeMap treeMap = (LinkedTreeMap) obj;
                    Comment dis = new Comment();
                    dis.setId(treeMap.get("id").toString());
                    dis.setContent(treeMap.get("content").toString());
                    dis.setDateTime(treeMap.get("dateTime").toString());
                    if(treeMap.get("userImage") != null){
                        dis.setUserImage(treeMap.get("userImage").toString());
                    }
                    dis.setUserName(treeMap.get("userName").toString());
                    dis.setUserUsername(treeMap.get("userUsername").toString());
                    comments.add(dis);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comments;
    }
}
