package com.example.mvm.receiver;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    AlertDialog.Builder builder;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Firebase","STIGLO");

    }
}
