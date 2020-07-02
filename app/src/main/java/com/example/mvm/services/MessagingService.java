package com.example.mvm.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingService extends FirebaseMessagingService {
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


    }

}
