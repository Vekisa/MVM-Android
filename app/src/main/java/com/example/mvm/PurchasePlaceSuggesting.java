package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class PurchasePlaceSuggesting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_purchase_place_suggesting);
    }

    public void onFinishedClick(View v){
        Intent checkCredentialsIntent = new Intent(getApplicationContext(), CategoryActivity.class);
        startActivity(checkCredentialsIntent);
    }
}
