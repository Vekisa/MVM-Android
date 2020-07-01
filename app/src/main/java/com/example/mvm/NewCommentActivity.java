package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mvm.model.Comment;
import com.example.mvm.model.Discussion;
import com.example.mvm.model.User;
import com.example.mvm.services.CommentService;
import com.example.mvm.services.DiscussionService;
import com.example.mvm.services.ForumService;
import com.example.mvm.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewCommentActivity extends NavigationActivity {

    TextInputEditText content;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_new_comment, null, false);
        drawer.addView(contentView, 0);

        content = findViewById(R.id.newCommentContent);
        user = UserService.findLoggedIn();
    }

    public void onSaveCommentClick(View view) throws JSONException {
        Comment comment = new Comment();
        comment.setContent(content.getText().toString());
        comment.setDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
        comment.setUserUsername(user.getUsername());
        comment.setDiscussionId(((Discussion) getIntent().getExtras().get("discussion")).getId());
        CommentService.save(comment);

        Intent discussionIntent = new Intent(getApplicationContext(), DiscussionActivity.class);
        discussionIntent.putExtra("discussion", (Discussion) getIntent().getExtras().get("discussion"));
        startActivity(discussionIntent);
    }
}
