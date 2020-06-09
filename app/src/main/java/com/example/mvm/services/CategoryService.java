package com.example.mvm.services;

import android.widget.Toast;

import com.example.mvm.Category;
import com.example.mvm.authentication.AppProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class CategoryService {
    public List<Category> findAll() {
        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/category/all")
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();
        List<Category> list = new ArrayList<>();
        try {
            Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if (response.code() == 200) {
                Gson gson = new Gson();
                System.out.println("----------------------------------------------------------------------------------");
                list = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(), Category[].class));
                /*for(Object o : list) {
                    LinkedTreeMap ltm = (LinkedTreeMap) o;
                    arraySpinner.add(ltm.get("name").toString());
                }
                System.out.println(list);*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
