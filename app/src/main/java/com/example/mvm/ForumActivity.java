package com.example.mvm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.example.mvm.model.Discussion;
import com.example.mvm.model.User;
import com.example.mvm.services.ForumService;
import com.example.mvm.services.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForumActivity extends NavigationActivity{

    GridView gridview;
    List<Discussion> discussions = new ArrayList();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        registerReceiver(new InternetReceiver(), new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_forum, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_forum);

        gridview = (GridView) findViewById(R.id.gridview);

        user = UserService.findLoggedIn();
        discussions = ForumService.getDiscussions(user.getCategory());

        DiscussionAdapter adapter = new DiscussionAdapter(this, R.layout.discussion_item, discussions);
        gridview.setAdapter(adapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Discussion selectedItem = (Discussion) adapterView.getItemAtPosition(i);
                Intent discussionIntent = new Intent(getApplicationContext(), DiscussionActivity.class);
                discussionIntent.putExtra("discussion", selectedItem);
                startActivity(discussionIntent);
            }
        });

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev)
    {
        return true;
    }

    public void onNewDiscussionClick(View view){
        Intent newDiscussion = new Intent(getApplicationContext(), NewDiscussionActivity.class);
        startActivity(newDiscussion);
    }

}
