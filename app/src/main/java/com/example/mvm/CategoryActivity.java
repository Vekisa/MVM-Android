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
import com.example.mvm.model.Category;
import com.example.mvm.model.Image;
import com.example.mvm.services.CategoryService;
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
    List<Category> categories = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_category, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_category);

        categories = CategoryService.findAll();

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
}
