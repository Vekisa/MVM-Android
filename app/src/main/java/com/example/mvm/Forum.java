package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Date;

public class Forum extends AppCompatActivity {

    GridView gridview;
    ArrayList<Discussion> discussions = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        gridview = (GridView) findViewById(R.id.gridview);

        discussions.add(new Discussion("Kako okopati beli lukac",R.drawable.profile, new Date(),"Mihajlo Levarski"));
        discussions.add(new Discussion("Prskanje u jesen",R.drawable.profile, new Date(),"Milica Matijevic"));
        discussions.add(new Discussion("Teljenje krave",R.drawable.profile, new Date(),"Veljko Mosorinski"));

        CategoryAdapter adapter = new CategoryAdapter(this, R.layout.gridview_item, discussions);
        gridview.setAdapter(adapter);
    }
}
