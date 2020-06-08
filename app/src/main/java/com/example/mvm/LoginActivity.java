package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
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
    }

    public void onCheckCredentialsClick(View v){

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", username.getText().toString())
                .addFormDataPart("password", password.getText().toString())
                .addFormDataPart("grant_type", "password")
                .build();

        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/oauth/token")
                .post(requestBody)
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();

        try {
            Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                Gson gson = new Gson();
                LoginResponse responseResult =gson.fromJson(response.body().string(), LoginResponse.class);
                AppProperties.getInstance().token = responseResult.getAccess_token();
                Intent checkCredentialsIntent = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(checkCredentialsIntent);
            }else{
                Toast.makeText(getApplication().getBaseContext(),"Pogresna sifra ili korisnicko ime.",
                        Toast.LENGTH_SHORT).show();
                System.out.println(username.getText()  + " " + password.getText() + " Response: " + response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
