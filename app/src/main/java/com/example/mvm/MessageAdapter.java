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

import java.util.ArrayList;

public class MessageAdapter  extends ArrayAdapter {

    ArrayList<Message> messages = new ArrayList<>();

    public MessageAdapter(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);
        messages = objects;
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
        TextView date = convertView.findViewById(R.id.date);
        ImageView profile = convertView.findViewById(R.id.profile);
        TextView content = convertView.findViewById(R.id.content);

        profile.setImageResource(messages.get(position).getImage_id());
        user.setText(messages.get(position).getUser());
        date.setText(messages.get(position).getDate().getDay() + "/" + messages.get(position).getDate().getMonth() + "/" + messages.get(position).getDate().getYear());
        content.setText(messages.get(position).getContent());

        return convertView;
    }
}
