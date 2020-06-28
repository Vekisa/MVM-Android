package com.example.mvm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.example.mvm.model.Discussion;

import java.util.ArrayList;
import java.util.Date;

public class ForumActivity extends NavigationActivity {

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

        gridview = (GridView) findViewById(R.id.gridview);

        /*discussions.add(new Discussion("Kako okopati beli lukac",R.drawable.profile, new Date(),"Mihajlo Levarski"));
        discussions.add(new Discussion("Prskanje u jesen",R.drawable.profile2, new Date(),"Milica Matijevic"));
        discussions.add(new Discussion("Teljenje krave",R.drawable.profile3, new Date(),"Veljko Mosorinski"));
*/
        DiscussionAdapter adapter = new DiscussionAdapter(this, R.layout.discussion_item, discussions);
        gridview.setAdapter(adapter);
    }

    public void onNewDiscussionClick(View view){
        Intent newDiscussion = new Intent(getApplicationContext(), NewDiscussionActivity.class);
        startActivity(newDiscussion);
    }
}
