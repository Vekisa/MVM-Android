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

import com.example.mvm.model.Comment;
import com.example.mvm.services.ImageService;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends ArrayAdapter {
    List<Comment> comments = new ArrayList<>();

    public CommentAdapter(@NonNull Context context, int resource, @NonNull List objects) {
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
        convertView = inflater.inflate(R.layout.discussion_item, null);
        ImageView imageview = convertView.findViewById(R.id.imageview);
        TextView user = convertView.findViewById(R.id.user);
        TextView name = convertView.findViewById(R.id.name);
        TextView date = convertView.findViewById(R.id.date);
        TextView content = convertView.findViewById(R.id.content);

        String imageContent = comments.get(position).getUserImage();
        if(imageContent != null){
            imageview.setImageBitmap(ImageService.String2Bitmap(imageContent));
        }
        user.setText(comments.get(position).getUserName());
        date.setText(comments.get(position).getDateTime());
        content.setText(comments.get(position).getContent());

        return convertView;
    }
}
