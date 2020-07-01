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

public class MessageAdapter  extends ArrayAdapter {

    ArrayList<Comment> comments = new ArrayList<>();

    public MessageAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        comments = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.message_item, null);
        TextView user = convertView.findViewById(R.id.user);
        TextView date = convertView.findViewById(R.id.posted);

        TextView content = convertView.findViewById(R.id.content);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm");


        user.setText(comments.get(position).getUser());
        date.setText(formatter.format(comments.get(position).getPosted()));
        content.setText(comments.get(position).getContent());

        return convertView;
    }
}
