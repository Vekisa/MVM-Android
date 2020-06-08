package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mvm.authentication.AppProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class Forum extends NavigationActivity {

    GridView gridview;
    List<Discussion> discussions = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_forum, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_forum);

        //setContentView(R.layout.activity_forum);

        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/discussion/all")
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();

        try {
            Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                Gson gson = new Gson();
                System.out.println("----------------------------------------------------------------------------------");
                List<Object> disc = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(), Object[].class));

                for (Object o:disc){

                    Discussion discussion = (Discussion) o;

                    System.out.println(discussion.getTitle());

                }

            }else{
                Toast.makeText(getApplication().getBaseContext (),"Discussion problem!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        gridview = (GridView) findViewById(R.id.gridview);

//        discussions.add(new Discussion("Kako okopati beli luk",R.drawable.profile, new Date(),"Mihajlo Levarski"));
//        discussions.add(new Discussion("Krompir",R.drawable.profile2, new Date(),"Milica Matijevic"));
//       discussions.add(new Discussion("Teljenje krave",R.drawable.profile3, new Date(),"Veljko Mosorinski"));

        DiscussionAdapter adapter = new DiscussionAdapter(this, R.layout.discussion_item, discussions);
        gridview.setAdapter(adapter);
    }
}
