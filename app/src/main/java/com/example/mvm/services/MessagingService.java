package com.example.mvm.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {

    AlertDialog.Builder builder;


    public MessagingService() {
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i("FIREBASE", s);

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("FIREBASE Message", remoteMessage.getData().toString());

        Intent intent = new Intent();
        intent.putExtra("message",remoteMessage.getNotification().getBody());
        intent.putExtra("title",remoteMessage.getNotification().getTitle());
        intent.setAction(Intent.ACTION_SEND);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);

    }





}
