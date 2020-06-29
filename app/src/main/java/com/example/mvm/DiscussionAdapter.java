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
import com.example.mvm.model.Image;
import com.example.mvm.services.ImageService;

import java.util.ArrayList;
import java.util.List;

public class DiscussionAdapter extends ArrayAdapter {

    List<Discussion> discussions = new ArrayList<>();

    public DiscussionAdapter(@NonNull Context context, int resource, @NonNull List objects) {
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
        TextView content = convertView.findViewById(R.id.content);

        String imageContent = discussions.get(position).getUserImage();
        if(imageContent != null){
            imageview.setImageBitmap(ImageService.String2Bitmap(imageContent));
        }
        user.setText(discussions.get(position).getUserName());
        name.setText(discussions.get(position).getTitle());
        date.setText(discussions.get(position).getDateTime());
        content.setText(discussions.get(position).getContent());

        return convertView;
    }
}
