package com.example.mvm.services;

import android.widget.Toast;

import com.example.mvm.CategoryActivity;
import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.Category;
import com.example.mvm.model.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class CategoryService {
    public static List<Category> findAll() {
        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/category/all")
                .build();
        Response response = null;
        List<Category> categories = new ArrayList<>();
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if (response.code() == 200) {
                List<Object> list = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(), Object[].class));
                for (Object o : list) {
                    Category category = new Category();
                    LinkedTreeMap ltm = (LinkedTreeMap) o;
                    category.setName(ltm.get("name").toString());
                    category.setId(ltm.get("id").toString());

                    Image image = new Image();
                    image.setCategoryId(category.getId());
                    String content = ImageService.obtain(image).getContent();
                    if(content != null){
                        category.setImage(ImageService.String2Bitmap(content));
                    }

                    categories.add(category);
                }
            }else {
                //Toast.makeText(getApplicationContext(), "Problem pri dobavljanju kategorija.", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return categories;
    }

    public static Category findSuggestion(Integer first, Integer second) {
        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/mocked/production_suggestion?first=" + first + "&second=" + second)
                .build();
        Response response = null;
        Category category = new Category();
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if (response.code() == 200) {
                Object o = new GsonBuilder().create().fromJson(response.body().string(), Object.class);

                    LinkedTreeMap ltm = (LinkedTreeMap) o;
                    category.setName(ltm.get("name").toString());
                    category.setId(ltm.get("id").toString());

                    Image image = new Image();
                    image.setCategoryId(category.getId());
                    String content = ImageService.obtain(image).getContent();
                    if(content != null){
                        category.setImage(ImageService.String2Bitmap(content));
                    }
            }else {
                //Toast.makeText(getApplicationContext(), "Problem pri dobavljanju kategorije.", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return category;
    }
}
