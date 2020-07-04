package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.authentication.LoginResponse;
import com.example.mvm.model.Category;
import com.example.mvm.services.CategoryService;
import com.example.mvm.services.OfferService;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
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

public class PriceOffer extends NavigationActivity {

    private EditText price;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_price_offer, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_offer);

        image = findViewById(R.id.imageCategory);

        Category category = CategoryService.findMyCategory();
        image.setImageBitmap(category.getImage());

        Button buttonCheckCr = (Button) findViewById(R.id.buttonOffer);

        price = findViewById(R.id.price);

        ArrayList<String> arraySpinner = new ArrayList<>();

        Request request = AppProperties.getInstance().getRequest()
                .url(AppProperties.getInstance().getServerUrl() + "/product")
                .build();

        try {
            Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if (response.code() == 200) {
                Gson gson = new Gson();
                System.out.println("----------------------------------------------------------------------------------");
                List<Object> list = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(), Object[].class));
                for (Object o : list) {
                    LinkedTreeMap ltm = (LinkedTreeMap) o;
                    arraySpinner.add(ltm.get("name").toString());
                }
                System.out.println(list);
            } else {
                Toast.makeText(getApplication().getBaseContext(), "Problem pri dobavljanju proizvoda.",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        Spinner s = (Spinner) findViewById(R.id.product);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


        buttonCheckCr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Spinner s = (Spinner) findViewById(R.id.product);
                    JSONObject json = new JSONObject();
                    json.put("name", s.getSelectedItem());
                    json.put("price", price.getText());
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
                    Request request = new Request.Builder()
                            .url(AppProperties.getInstance().getServerUrl() + "/offer")
                            .post(body)
                            .build();

                    Response response = null;
                    try {
                        response = AppProperties.getInstance().getHttpClient().newCall(request).execute();

                        if (response.code() == 200) {
                            Intent offerInt = new Intent(getApplicationContext(), OfferActivity.class);
                            startActivity(offerInt);

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
