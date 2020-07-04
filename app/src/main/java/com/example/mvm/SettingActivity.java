package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.mvm.authentication.AppProperties;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class SettingActivity extends NavigationActivity {

    Switch aSwitch ;
    Request.Builder requestBuilder = new Request.Builder();
    String fcmToken ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //registerReceiver(new InternetReceiver(), new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_setting, null, false);
        drawer.addView(view, 0);
        navigationView.setCheckedItem(R.id.nav_settings);

        aSwitch = (Switch) findViewById(R.id.switchNotification);

        Log.i("Switch ", String.valueOf(AppProperties.getInstance().sharedPreferences.getBoolean("sub",false)));

        aSwitch.setChecked(AppProperties.getInstance().sharedPreferences.getBoolean("sub",false));
        fcmToken = AppProperties.getInstance().fcmToken;


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        Log.i("Switch","CHECKED");

                        Request request = requestBuilder
                                .url(AppProperties.getInstance().getServerUrl() + "/auth/sub?token="+fcmToken)
                                .build();

                        Response response = null;
                        try {
                            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
                            if (response.code()==200){
                                Log.i("Firebase ","Sub ");

                                AppProperties.getInstance().editor.putBoolean("sub",true);
                                AppProperties.getInstance().editor.apply();

                            }
                            Log.i("Firebase ",response.body().string());
                            Log.i("Firebase ", String.valueOf(response.code()));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }else{
                        Log.i("Switch","Unchecked");
                        Request request = requestBuilder
                                .url(AppProperties.getInstance().getServerUrl() + "/auth/unSub?token="+fcmToken)
                                .build();

                        Response response = null;
                        try {
                            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
                            if (response.code()==200){
                                Log.i("Firebase ","UNSub ");
                                AppProperties.getInstance().editor.putBoolean("sub",false);
                                AppProperties.getInstance().editor.apply();
                            }
                            Log.i("Firebase ",response.body().string());
                            Log.i("Firebase ", String.valueOf(response.code()));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

            }
        });



    }
}