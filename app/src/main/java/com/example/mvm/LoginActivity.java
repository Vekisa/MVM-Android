package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.authentication.LoginResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Button buttonCheckCr = (Button)findViewById(R.id.buttonSignIn);

        buttonCheckCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                    Response response = AppProperties.getInstance().login(getApplicationContext(),username.getText().toString(),password.getText().toString());
                    if(response.code() == 200){
                        Intent checkCredentialsIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                        checkCredentialsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        startActivity(checkCredentialsIntent);
                        finish();
                    }else{
                        Toast.makeText(getApplication().getBaseContext(),"Pogresna sifra ili korisnicko ime.",
                                Toast.LENGTH_SHORT).show();
                        System.out.println(username.getText()  + " " + password.getText() + " Response: " + response.toString());
                    }
            }
        });
    }


}
