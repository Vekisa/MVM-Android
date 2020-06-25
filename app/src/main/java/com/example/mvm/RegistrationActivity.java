package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.authentication.LoginResponse;
import com.example.mvm.model.Category;
import com.example.mvm.model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name;
    private EditText username;
    private EditText password;
    private EditText passwordCheck;
    private Spinner category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        passwordCheck = findViewById(R.id.passwordCheck);
        category = findViewById(R.id.category);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        ArrayList<String> arraySpinner = new ArrayList<>();

        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/category/all")
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();

        try {
            Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                Gson gson = new Gson();
                System.out.println("----------------------------------------------------------------------------------");
                List<Object> list = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(), Object[].class));
                for(Object o : list) {
                    LinkedTreeMap ltm = (LinkedTreeMap) o;
                    arraySpinner.add(ltm.get("name").toString());
                }
                System.out.println(list);
            }else{
                Toast.makeText(getApplication().getBaseContext (),"Problem pri dobavljanju kategorija.",
                        Toast.LENGTH_SHORT).show();
                System.out.println(username.getText()  + " " + password.getText() + " Response: " + response.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Spinner s = (Spinner) findViewById(R.id.category);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
    }

    public void onRegistrationClick(View v){

        if(!password.getText().toString().equals(passwordCheck.getText().toString())) {
            Toast.makeText(getApplication().getBaseContext(), "Sifre se ne poklapaju",
                    Toast.LENGTH_SHORT).show();
        }else{

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            try {
                jsonObject.put("username", username.getText().toString());
                jsonObject.put("name", name.getText().toString());
                jsonObject.put("password", password.getText().toString());
                jsonObject.put("category", category.getSelectedItem().toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(AppProperties.getInstance().getServerUrl() + "/auth/registration")
                    .post(body)
                    .build();

            try {
                Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
                if(response.code() == 200){
                    Intent categoryIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(categoryIntent);
                }else{
                    Toast.makeText(getApplication().getBaseContext(),"Doslo je do greske pri komunikaciji sa serverom.",
                            Toast.LENGTH_SHORT).show();
                    System.out.println(username.getText()  + " " + password.getText() + " Response: " + response.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
