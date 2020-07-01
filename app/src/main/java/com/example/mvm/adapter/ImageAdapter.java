package com.example.mvm.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mvm.R;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends ArrayAdapter {

    List<Bitmap> images = new ArrayList<>();

    public ImageAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        images = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.image_item, null);
        ImageView imageView = convertView.findViewById(R.id.imageItem);
        imageView.setImageBitmap(images.get(position));
        return convertView;
    }
}
