package com.example.mvm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvm.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    Context context;
    ArrayList<Product> products = new ArrayList<>();
    LayoutInflater inflater;

    public ProductAdapter(Context context, ArrayList<Product> products, LayoutInflater inflater) {
        this.context = context;
        this.products = products;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.listview_item, null);
        ImageView imageview = view.findViewById(R.id.product_image);
        TextView textview = view.findViewById(R.id.product_text);
        imageview.setImageResource(products.get(i).getImage_id());
        textview.setText(products.get(i).getName());
        return view;
    }
}
