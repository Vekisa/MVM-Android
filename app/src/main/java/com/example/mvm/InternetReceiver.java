package com.example.mvm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mvm.adapter.DatabaseAdapter;
import com.example.mvm.adapter.DiscussionSyncAdapter;
import com.example.mvm.model.Discussion;

import java.text.ParseException;

public class InternetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnected = isConnectedToInternet(context);
        if (isConnected) {
            new DiscussionSyncAdapter(context, true).onPerformSync(null, null, null, null, null);
            System.out.println("JOPET");
            Discussion dis = new Discussion();
            dis.setContent("test");
            dis.setDateTime("04.07.2020");
            dis.setForumId("6");
            dis.setId("5");
            dis.setTitle("test");
            dis.setUserId("2");
            dis.setUserImage("null");
            dis.setUserName("mica");
            dis.setUserUsername("mica");
            try {
                new DatabaseAdapter(context).insertDiscussion(dis);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isConnectedToInternet(Context context){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
