package com.example.mvm.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.mvm.helper.DatabaseHelper;

public class DatabaseAdapter {

    DatabaseHelper helper;

    public DatabaseAdapter(Context context) {
        helper = new DatabaseHelper(context);
    }

    public long insertForum(){
        SQLiteDatabase dbb = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", "1");
        contentValues.put("category_id", "1");
        long id = dbb.insert("mvm", null , contentValues);
        return id;
    }
}
