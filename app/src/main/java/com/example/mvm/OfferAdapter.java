package com.example.mvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvm.model.Category;
import com.example.mvm.model.Offer;

import java.util.ArrayList;
import java.util.List;

public class OfferAdapter extends ArrayAdapter {

    List<Offer> offers = new ArrayList<>();

    public OfferAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        offers = objects;
    }



    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.offer_item, null);
        TextView userTitle = convertView.findViewById(R.id.userTitle);
        TextView message = convertView.findViewById(R.id.titleOffer);

        System.out.println(offers.get(position).getUsername());

        userTitle.setText(offers.get(position).getUsername());
        message.setText(offers.get(position).getName()+" Cena: "+ offers.get(position).getPrice());

        return convertView;
    }
}
