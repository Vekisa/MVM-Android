package com.example.mvm.adapter;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.example.mvm.helper.DatabaseHelper;
import com.example.mvm.model.Comment;
import com.example.mvm.model.Discussion;
import com.example.mvm.model.Forum;
import com.example.mvm.model.Image;
import com.example.mvm.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseAdapter {

    DatabaseHelper helper;
    static final String DB_NAME = "mvm";
    static final String TABLE_DISCUSION_NAME = "discussion";

    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }

    public long insertForum(Forum forum){
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", forum.getId());
        contentValues.put("category_id", forum.getCategoryId());
        return dbb.insert(DB_NAME, null , contentValues);
    }

    public long insertImage(Image image){
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", image.getId());
        contentValues.put("path", image.getPath());
        contentValues.put("category_id", image.getCategoryId());
        contentValues.put("user_id", image.getUserId());
        contentValues.put("discussion_id", image.getDiscussionId());
        contentValues.put("comment_id", image.getCommentId());
        return dbb.insert(DB_NAME, null , contentValues);
    }

    public long insertUser(User user){
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", user.getId());
        contentValues.put("name", user.getName());
        return dbb.insert(DB_NAME, null , contentValues);
    }

    public long insertDiscussion(Discussion discussion) throws ParseException {
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", discussion.getId());
        contentValues.put("title", discussion.getTitle());
        contentValues.put("content", discussion.getContent());
        contentValues.put("date_time", discussion.getDateTime());
        contentValues.put("forum_id", discussion.getForumId());
        contentValues.put("user_id", discussion.getUserId());
        return dbb.insertOrThrow(TABLE_DISCUSION_NAME, null , contentValues);
    }

    public long insertComment(Comment comment){
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", comment.getId());
        contentValues.put("content", comment.getContent());
        contentValues.put("date_time", comment.getDateTime());
        contentValues.put("discussion_id", comment.getDiscussionId());
        contentValues.put("user_id", comment.getUserId());
        long id = dbb.insert(DB_NAME, null , contentValues);
        dbb.close();
        return id;
    }

    public List<Discussion> getDiscussions()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {"_id", "title", "content", "date_time", "forum_id", "user_id"};
        Cursor cursor = db.query(TABLE_DISCUSION_NAME, columns,null,null,null,null,null);
        List<Discussion> discussions = new ArrayList<>();
        System.out.println("pre get-a i move-a: " + cursor.getPosition());
        cursor.moveToFirst();
        System.out.println("pre get-a a posle move-a: " + cursor.getPosition());
        while (cursor.moveToNext())
        {
            Discussion dis = new Discussion();
            dis.setId(cursor.getString(0));
            dis.setTitle(cursor.getString(1));
            dis.setContent(cursor.getString(2));
            dis.setDateTime(cursor.getString(3));
            dis.setForumId(cursor.getString(4));
            dis.setUserId(cursor.getString(5));
            discussions.add(dis);
        }
        System.out.println("posle close-a " + cursor.getPosition());
        cursor.close();
        return discussions;
    }

    public List<Comment> geComments()
    {
        SQLiteDatabase db = helper.getWritableDatabase();
        String[] columns = {"_id", "content", "date_time", "discussion_id", "user_id"};
        Cursor cursor =db.query(DB_NAME, columns,null,null,null,null,null);
        List<Comment> discussions = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Comment dis = new Comment();
            dis.setId(cursor.getString(cursor.getColumnIndex("_id")));
            dis.setContent(cursor.getString(cursor.getColumnIndex("content")));
            dis.setDateTime(cursor.getString(cursor.getColumnIndex("date_time")));
            dis.setDiscussionId(cursor.getString(cursor.getColumnIndex("discussion_id")));
            dis.setUserId(cursor.getString(cursor.getColumnIndex("user_id")));
            discussions.add(dis);
        }
        db.close();
        return discussions;
    }
}
