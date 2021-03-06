package com.example.mvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import android.widget.GridView;

import android.widget.Toast;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.Category;
import com.example.mvm.model.Image;
import com.example.mvm.services.CategoryService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
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

    BroadcastReceiver receiver;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        builder = new AlertDialog.Builder(this);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String messsage = intent.getStringExtra("message");
                String title = intent.getStringExtra("title");
                builder.setMessage(messsage).setTitle(title).setPositiveButton("Prikaži", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent offerInt = new Intent(getApplicationContext(), OfferActivity.class);
                        startActivity(offerInt);
                    }
                })
                        .create().show();
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SEND);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver,filter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}
