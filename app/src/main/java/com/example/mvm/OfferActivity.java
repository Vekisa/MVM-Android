package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;

import com.example.mvm.model.Category;
import com.example.mvm.model.Offer;
import com.example.mvm.services.OfferService;

import java.util.ArrayList;
import java.util.List;

public class OfferActivity extends NavigationActivity {

    GridView gridview;
    List<Offer> offers = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_offer, null, false);
        drawer.addView(view, 0);
        navigationView.setCheckedItem(R.id.nav_offers);

        offers = OfferService.findAll();

        gridview = (GridView) findViewById(R.id.gridviewoffer);

        OfferAdapter adapter = new OfferAdapter(this, R.layout.offer_item,offers);
        gridview.setAdapter(adapter);

    }
}