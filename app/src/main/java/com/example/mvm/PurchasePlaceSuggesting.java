package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.mvm.model.Category;
import com.example.mvm.services.CategoryService;

public class PurchasePlaceSuggesting extends NavigationActivity {

    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                //WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        //setContentView(R.layout.activity_purchase_place_suggesting);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_purchase_place_suggesting, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_suggestion_place);

        image = findViewById(R.id.imageCategory);

        Category category = CategoryService.findMyCategory();
        image.setImageBitmap(category.getImage());
    }

    public void onFinishedClick(View v){
        Intent map = new Intent(getApplicationContext(), Map.class);
        map.putExtra("value","route");
        startActivity(map);
    }
}
