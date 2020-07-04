package com.example.mvm.helper;

import android.net.Uri;

public class DiscussionContract {
    public static final String CONTENT_ITEM_TYPE = "discussion";//"vnd.android.cursor.item/vnd.udinic.tvshow";
    public static final String CONTENT_TYPE_DIR = "discussion";//"vnd.android.cursor.dir/vnd.udinic.tvshow";

    public static final String AUTHORITY = "com.example.discussion.provider";
    // content://<authority>/<path to type>
    public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/discussion");

    public static final String DISCUSSION_ID = "_id";
    public static final String DISCUSSION_TITLE = "name";
}
