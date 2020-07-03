package com.example.mvm;

import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.mvm.adapter.ImageAdapter;
import com.example.mvm.model.Discussion;
import com.example.mvm.model.Image;
import com.example.mvm.model.User;
import com.example.mvm.services.DiscussionService;
import com.example.mvm.services.ForumService;
import com.example.mvm.services.ImageService;
import com.example.mvm.services.UserService;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewDiscussionActivity extends NavigationActivity{

    private TextInputEditText title;
    private TextInputEditText content;
    private User user;
    private RelativeLayout relativeLayout;
    List<Bitmap> images = new ArrayList<>();
    GridView gridView;
    ImageButton imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_new_discussion, null, false);
        drawer.addView(contentView, 0);
        navigationView.setCheckedItem(R.id.nav_forum);

        title = findViewById(R.id.newDiscussionTitle);
        content = findViewById(R.id.newDiscussionContent);
        user = UserService.findLoggedIn();
        relativeLayout = findViewById(R.id.relativeLayout);
        gridView = new GridView(this);
        gridView.setNumColumns(2);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.BELOW, R.id.inputLayout2);
        relativeLayout.addView(gridView, params);

        imageButton = findViewById(R.id.discussionImageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
    }

    private void selectImage(){
        final CharSequence[] options = {"Fotografišite se", "Izaberite iz galerije", "Odustanite"};
        AlertDialog.Builder builder = new AlertDialog.Builder(NewDiscussionActivity.this);
        builder.setTitle("Postavite fotografiju");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int item) {
                if(options[item].equals("Fotografišite se")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File file = new File(Environment.getExternalStorageDirectory(), "temp.png");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(intent, 1);
                }
                else if(options[item].equals("Izaberite iz galerije")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if(options[item].equals("Odustanite")){
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 1){
                File file = new File(Environment.getExternalStorageDirectory().toString());
                for(File temp : file.listFiles()){
                    if(temp.getName().equals("temp.png")){
                        file = temp;
                        break;
                    }
                }
                try {
                    BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
                    bitmap = ImageService.getResizedBitmap(bitmap, 400);

                    images.add(bitmap);

                    ImageAdapter adapter = new ImageAdapter(this, R.layout.image_item, images);
                    gridView.setAdapter(adapter);

                    String path = android.os.Environment
                            .getExternalStorageDirectory()
                            + File.separator
                            + "Phoenix" + File.separator + "default";
                    file.delete();
                    OutputStream outFile = null;
                    File f = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
                    try {
                        outFile = new FileOutputStream(f);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                        outFile.flush();
                        outFile.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
            else if(requestCode == 2){
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
                thumbnail= ImageService.getResizedBitmap(thumbnail, 400);

                images.add(thumbnail);

                ImageAdapter adapter = new ImageAdapter(this, R.layout.image_item, images);
                gridView.setAdapter(adapter);
            }
        }
    }

    public void onSaveDiscussionClick(View view) throws JSONException, IOException {
        Discussion discussion = new Discussion();
        discussion.setTitle(title.getText().toString());
        discussion.setContent(content.getText().toString());
        discussion.setDateTime(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
        discussion.setForumId(ForumService.findByCategoryName(user.getCategory()).getCategoryId());
        discussion.setUserUsername(user.getUsername());
        String id = DiscussionService.save(discussion);

        for(Bitmap bitmap : images){
            Image image = new Image();
            image.setContent(ImageService.BitMapToString(bitmap));
            image.setDiscussionId(id);
            ImageService.send(this, image);
        }

        Intent forum = new Intent(getApplicationContext(), ForumActivity.class);
        startActivity(forum);
    }
}
