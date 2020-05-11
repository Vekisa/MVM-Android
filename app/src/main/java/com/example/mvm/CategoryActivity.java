package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.view.WindowManager;
import android.widget.GridView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    GridView gridview;
    ArrayList<Category> categories = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_category);

        gridview = (GridView) findViewById(R.id.gridview);

        categories.add(new Category("Jaja i živinsko meso", R.drawable.eggs_and_poultry));
        categories.add(new Category("Živa stoka", R.drawable.meat));
        categories.add(new Category("Mleko", R.drawable.milk));
        categories.add(new Category("Mlečni proizvodi", R.drawable.milk_products));
        categories.add(new Category("Voće", R.drawable.fruit));
        categories.add(new Category("Povrće", R.drawable.vegetables));
        categories.add(new Category("Žitarice", R.drawable.grains));

        CategoryAdapter adapter = new CategoryAdapter(this, R.layout.gridview_item, categories);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent listViewIntent = new Intent(getApplicationContext(), ProductActivity.class);
                listViewIntent.putExtra("selected_category", ((Category)adapterView.getItemAtPosition(i)).getName());
                startActivity(listViewIntent);
            }
        });
    }


}
