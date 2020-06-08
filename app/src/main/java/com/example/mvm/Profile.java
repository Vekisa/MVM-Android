package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.User;
import com.example.mvm.services.CategoryService;
import com.example.mvm.services.UserService;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class Profile extends NavigationActivity {

    private UserService userService = new UserService();
    private CategoryService catService = new CategoryService();
    private EditText name;
    private Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        name = findViewById(R.id.ipinput);
        category = findViewById(R.id.static_spinner);

        User currentUser = userService.findLoggedIn();
        List<Category> categories = catService.findAll();
        List<String> catNames = new ArrayList<>();
        int position = 0;
        for(int i = 0; i <= categories.size(); i++){
            catNames.add(categories.get(i).getName());
            if(categories.get(i).getName().equals(currentUser.getCategory())){
                position = i;
            }
        }
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, catNames);
        category.setAdapter(spinnerAdapter);
        category.setSelection(position);

        name.setText(currentUser.getName());

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_profile, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_profile);

        //setContentView(R.layout.activity_profile);

        String[] arraySpinner = new String[] {
                "Jaja i živinsko meso", "Živa stoka", "Mleko", "Mlečni proizvodi", "Voće", "Povrće", "Žitarice"
        };


        Spinner s = (Spinner) findViewById(R.id.static_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    public void onPasswordClick(View v){
        Intent passwordEdit = new Intent(getApplicationContext(),PasswordActivity.class);
        startActivity(passwordEdit);
    }

}
