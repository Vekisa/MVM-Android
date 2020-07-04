package com.example.mvm.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "mvm_database";
    public static final int DB_VERSION = 1;
    public static final String DISCUSSION_TABLE_NAME = "discussion";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE FORUM (" +
                "_id VARCHAR(255) PRIMARY KEY," +
                "CATEGORY_ID VARCHAR(255))");

        db.execSQL("CREATE TABLE IMAGE (" +
                "_id VARCHAR(255) PRIMARY KEY," +
                "PATH VARCHAR(255)," +
                "CATEGORY_ID VARCHAR(255)," +
                "USER_ID VARCHAR(255)," +
                "DISCUSSION_ID VARCHAR(255)," +
                "COMMENT_ID VARCHAR(255))");

        db.execSQL("CREATE TABLE USER (" +
                "_id VARCHAR(255) PRIMARY KEY," +
                "NAME VARCHAR(255))");

        db.execSQL("CREATE TABLE DISCUSSION (" +
                "_id VARCHAR(255) PRIMARY KEY," +
                "TITLE VARCHAR(255)," +
                "CONTENT VARCHAR(255)," +
                "DATE_TIME VARCHAR(255)," +
                "FORUM_ID VARCHAR(255)," +
                "USER_ID VARCHAR(255))");

        db.execSQL("CREATE TABLE COMMENT (" +
                "_id VARCHAR(255) PRIMARY KEY," +
                "CONTENT VARCHAR(255)," +
                "DATE_TIME VARCHAR(255)," +
                "DISCUSSION_ID VARCHAR(255)," +
                "USER_ID VARCHAR(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS FORUM");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS IMAGE");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS USER");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS DISCUSSION");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS COMMENT");
        onCreate(sqLiteDatabase);
    }
}
