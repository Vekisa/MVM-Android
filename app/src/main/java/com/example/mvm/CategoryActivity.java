package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    GridView gridview;
    ArrayList<Category> categories = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }
}
