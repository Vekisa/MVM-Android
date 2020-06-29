package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.mvm.model.Discussion;
import com.example.mvm.model.User;
import com.example.mvm.services.DiscussionService;
import com.example.mvm.services.ForumService;
import com.example.mvm.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewDiscussionActivity extends NavigationActivity {

    private TextInputEditText title;
    private TextInputEditText content;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_new_discussion, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_forum);

        title = findViewById(R.id.newDiscussionTitle);
        content = findViewById(R.id.newDiscussionContent);
        user = UserService.findLoggedIn();
    }

    public void onSaveDiscussionClick(View view) throws JSONException {
        Discussion discussion = new Discussion();
        discussion.setTitle(title.getText().toString());
        discussion.setContent(content.getText().toString());
        discussion.setDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
        discussion.setForumId(ForumService.findByCategoryName(user.getCategory()).getCategoryId());
        discussion.setUserUsername(user.getUsername());
        DiscussionService.save(discussion);

        Intent forum = new Intent(getApplicationContext(), ForumActivity.class);
        startActivity(forum);
    }
}
