package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mvm.model.Comment;
import com.example.mvm.model.Discussion;
import com.example.mvm.model.User;
import com.example.mvm.services.DiscussionService;
import com.example.mvm.services.ImageService;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

public class DiscussionActivity extends NavigationActivity {

    GridView gridView;
    ImageView userImageView;
    TextView userNameTextView;
    TextView titleTextView;
    TextView contentTextView;
    TextView dateTextView;
    List<Comment> comments = new ArrayList();
    User user;
    Discussion dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toast.makeText(getApplicationContext(), "onCreate called", Toast.LENGTH_LONG).show();

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_discussion, null, false);
        drawer.addView(contentView, 0);

        gridView = (GridView) findViewById(R.id.commentGridView);
        userImageView = (ImageView) findViewById(R.id.imageviewComment);
        userNameTextView = (TextView) findViewById(R.id.userComment);
        titleTextView = (TextView) findViewById(R.id.titleComment);
        contentTextView = (TextView) findViewById(R.id.contentComment);
        dateTextView = (TextView) findViewById(R.id.dateComment);

        dis = (Discussion) getIntent().getExtras().get("discussion");

        comments = DiscussionService.getComments(dis.getId());

        CommentAdapter adapter = new CommentAdapter(this, R.layout.discussion_item, comments);
        gridView.setAdapter(adapter);

        if (dis.getUserImage() != null) {
            userImageView.setImageBitmap(ImageService.String2Bitmap(dis.getUserImage()));
        }
        userNameTextView.setText(dis.getUserName());
        titleTextView.setText(dis.getTitle());
        contentTextView.setText(dis.getContent());
        dateTextView.setText(dis.getDateTime());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(), "onStart called", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(getApplicationContext(), "onResumed called", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getApplicationContext(), "onPause called", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getApplicationContext(), "onStop called", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "onDestroy called", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "onRestart called", Toast.LENGTH_LONG).show();
    }

    public void onNewCommentClick(View view){
        Intent newComment = new Intent(getApplicationContext(), NewCommentActivity.class);
        newComment.putExtra("discussion", dis);
        startActivity(newComment);
    }
}
