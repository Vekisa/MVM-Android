package com.example.mvm.services;

import android.content.Context;

import com.example.mvm.adapter.DatabaseAdapter;
import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.Discussion;
import com.example.mvm.model.Forum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class ForumService {
    public static Forum findByCategoryName(String name){
        Request request = new Request.Builder().url(AppProperties.getInstance().getServerUrl() + "/forum/findByCategoryName/" + name).build();
        Response response = null;
        Forum forum = null;
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                Object obj = new GsonBuilder().create().fromJson(response.body().string(), Object.class);
                LinkedTreeMap treeMap = (LinkedTreeMap) obj;
                forum = new Forum();
                forum.setCategoryId(treeMap.get("categoryId").toString());
                forum.setId(treeMap.get("id").toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return forum;
    }

    public static List<Discussion> getDiscussionsLocal(String categoryName, Context context){
        List<Discussion> discussions = new ArrayList<>();
        DatabaseAdapter adapter = new DatabaseAdapter(context);
        discussions = adapter.getDiscussions();
        return discussions;
    }

    public static List<Discussion> getDiscussions(String categoryName){
        Request request = new Request.Builder().url(AppProperties.getInstance().getServerUrl() + "/forum/getDiscussions/" + categoryName).build();
        Response response = null;
        List<Discussion> discussions = new ArrayList<>();
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                List<Object> objs = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(),  Object[].class));
                for(Object obj : objs){
                    LinkedTreeMap treeMap = (LinkedTreeMap) obj;
                    Discussion dis = new Discussion();
                    dis.setId(treeMap.get("id").toString());
                    dis.setContent(treeMap.get("content").toString());
                    dis.setDateTime(treeMap.get("dateTime").toString());
                    dis.setForumId(treeMap.get("forumId").toString());
                    dis.setTitle(treeMap.get("title").toString());
                    if(treeMap.get("userImage") != null){
                        dis.setUserImage(treeMap.get("userImage").toString());
                    }
                    dis.setUserName(treeMap.get("userName").toString());
                    dis.setUserUsername(treeMap.get("userUsername").toString());
                    discussions.add(dis);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return discussions;
    }
}
