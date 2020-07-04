package com.example.mvm.adapter;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.example.mvm.helper.DiscussionContract;
import com.example.mvm.model.Discussion;
import com.example.mvm.services.DiscussionService;
import com.example.mvm.services.ForumService;
import com.example.mvm.services.UserService;

import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class DiscussionSyncAdapter extends AbstractThreadedSyncAdapter {
    public DiscussionSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        // Get from local
        DatabaseAdapter adapter = new DatabaseAdapter(getContext());
        List<Discussion> localDiscussions = adapter.getDiscussions();

        // Get from remote
        List<Discussion> remoteDiscussions = ForumService.getDiscussions(UserService.findLoggedIn().getCategory());

        // See what Locals are missing on Remote
        List<Discussion> toRemote = new ArrayList<Discussion>();
        for (Discussion local : localDiscussions) {
            Boolean exist = false;
            for(Discussion remote : remoteDiscussions){
                if(local.getId().equals(remote.getId())){
                    exist = true;
                }
            }
            if(!exist){
                toRemote.add(local);
            }
        }

        // See what Remotes are missing on Local
        List<Discussion> toLocal = new ArrayList<Discussion>();
        for (Discussion remote : remoteDiscussions) {
            Boolean exist = false;
            for(Discussion local : localDiscussions){
                if(remote.getId().equals(local.getId())){
                    exist = true;
                }
            }
            if(!exist){
                toLocal.add(remote);
            }
        }

        if (toRemote.size() == 0) {
            Log.d("discussionsSyncAdapter ", "> No local changes to update server");
        } else {
            Log.d("discussionsSyncAdapter ", "> Updating remote server with local changes");

            // Updating remote
            for (Discussion remote : toRemote) {
                try {
                    DiscussionService.save(remote);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (toLocal.size() == 0) {
            Log.d("discussionsSyncAdapter ", "> No server changes to update local database");
        } else {
            Log.d("discussionsSyncAdapter ", "> Updating local database with remote changes");
            // Updating local
            for(Discussion local: toLocal){
                try {
                    adapter.insertDiscussion(local);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
