package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        String[] arraySpinner = new String[] {
                "Mleko", "Kokoske", "Krave", "Ovce", "Zmije", "Mali Pauk", "Nesto"
        };
        Spinner s = (Spinner) findViewById(R.id.category);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    public void onRegistrationClick(View v){
        Intent registrationIntent = new Intent(getApplicationContext(), NavigationActivity.class);
        startActivity(registrationIntent);
    }
}
