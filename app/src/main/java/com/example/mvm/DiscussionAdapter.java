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

import com.example.mvm.model.Discussion;

import java.util.ArrayList;

public class DiscussionAdapter extends ArrayAdapter {

    ArrayList<Discussion> discussions = new ArrayList<>();

    public DiscussionAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        discussions = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.discussion_item, null);
        ImageView imageview = convertView.findViewById(R.id.imageview);
        TextView user = convertView.findViewById(R.id.user);
        TextView name = convertView.findViewById(R.id.name);
        TextView date = convertView.findViewById(R.id.date);

        imageview.setImageResource(discussions.get(position).getImage_id());
        user.setText(discussions.get(position).getUser());
        name.setText(discussions.get(position).getName());
        date.setText(discussions.get(position).getDate().getDay() + "/" + discussions.get(position).getDate().getMonth() + "/" + discussions.get(position).getDate().getYear());

        return convertView;
    }
}
