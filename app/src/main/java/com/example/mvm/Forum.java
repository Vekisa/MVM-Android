package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.LauncherActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Date;

public class Forum extends NavigationActivity {

    GridView gridview;
    ArrayList<Discussion> discussions = new ArrayList();

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

        gridview = (GridView) findViewById(R.id.gridview);

        discussions.add(new Discussion("Kako okopati beli lukac",R.drawable.profile, new Date(),"Mihajlo Levarski"));
        discussions.add(new Discussion("Prskanje u jesen",R.drawable.profile, new Date(),"Milica Matijevic"));
        discussions.add(new Discussion("Teljenje krave",R.drawable.profile, new Date(),"Veljko Mosorinski"));

        CategoryAdapter adapter = new CategoryAdapter(this, R.layout.gridview_item, discussions);
        gridview.setAdapter(adapter);
    }
}
