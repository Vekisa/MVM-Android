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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DiscussionAdapter extends ArrayAdapter {

    List<Discussion> discussions = new ArrayList<>();

    public DiscussionAdapter(@NonNull Context context, int resource, @NonNull List<Discussion> objects) {
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
        TextView title = convertView.findViewById(R.id.title);
        TextView posted = convertView.findViewById(R.id.posted);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm");

        imageview.setImageResource(discussions.get(position).getImage_id());
        user.setText(discussions.get(position).getUser());
        title.setText(discussions.get(position).getTitle());
        posted.setText(formatter.format(discussions.get(position).getPosted()));

        return convertView;
    }
}
