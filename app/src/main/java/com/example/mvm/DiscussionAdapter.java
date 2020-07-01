package com.example.mvm;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.mvm.adapter.ImageAdapter;
import com.example.mvm.model.Discussion;
import com.example.mvm.model.Image;
import com.example.mvm.services.DiscussionService;
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.discussion_item, null);
        ImageView imageview = convertView.findViewById(R.id.imageview);
        TextView user = convertView.findViewById(R.id.user);
        TextView name = convertView.findViewById(R.id.name);
        TextView date = convertView.findViewById(R.id.date);
        TextView content = convertView.findViewById(R.id.content);
        GridView gridView = convertView.findViewById(R.id.discussionItemGridView);
        List<Bitmap> objs = new ArrayList<>();
        for(String s : DiscussionService.getImages(discussions.get(position).getId())){
            objs.add(ImageService.String2Bitmap(s));
        }
        ImageAdapter adapter = new ImageAdapter(getContext(), R.layout.image_item, objs);
        gridView.setAdapter(adapter);
        gridView.setFocusable(false);
        gridView.setClickable(false);

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
