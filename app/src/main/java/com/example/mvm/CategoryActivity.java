package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import android.widget.GridView;

import android.widget.Toast;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.Image;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class CategoryActivity extends NavigationActivity {

    GridView gridview;
    ArrayList<Category> categories = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_category, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_category);


        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        //setContentView(R.layout.activity_category);

        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/category/all")
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();

        try {
            Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                Gson gson = new Gson();
                System.out.println("----------------------------------------------------------------------------------");
                List<Object> list = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(), Object[].class));
                for(Object o : list) {
                    LinkedTreeMap ltm = (LinkedTreeMap) o;
                    getImage("",ltm.get("name").toString());
                    int index = list.indexOf(o);
                    String name = ltm.get("name").toString();
                    categories.add(new Category(name,getImageById(index+1)));

                    //Image image = gson.fromJson(reader, Image.class);
                    //categories.add(new Category(ltm.get("name").toString(),R.drawable.beli_luk));
                    //getImage(image.getPath(),ltm.get("name").toString());
                    //System.out.println(image.getPath());
                }
            }else{
                Toast.makeText(getApplication().getBaseContext (),"Problem pri dobavljanju kategorija.",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        gridview = (GridView) findViewById(R.id.gridview);



        CategoryAdapter adapter = new CategoryAdapter(this, R.layout.gridview_item, categories);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent listViewIntent = new Intent(getApplicationContext(), ProductActivity.class);
                listViewIntent.putExtra("selected_category", ((Category)adapterView.getItemAtPosition(i)).getName());
                startActivity(listViewIntent);
            }
        });
    }

    public void getImage(String path, String name){

        /*Request request = new Request.Builder()
                .url(path)
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();

        try {
            Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                Gson gson = new Gson();
                Object object = gson.fromJson(response.body().toString(),Object.class);
                categories.add(new Category(name,R.id.bubble_image));
            }else{
                Toast.makeText(getApplication().getBaseContext (),"Problem pri dobavljanju kategorija.",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public int getImageById(int id){
        switch (id) {
            case 1:
                return R.drawable.eggs_and_poultry;
            case 2:
                return R.drawable.meat;
            case 3:
                return R.drawable.milk;
            case 4:
                return R.drawable.milk_products;
            case 5:
                return R.drawable.fruit;
            case 6:
                return R.drawable.vegetables;
            case 7:
                return R.drawable.grains;
            default:
                return R.drawable.cow;
        }
    }


}
