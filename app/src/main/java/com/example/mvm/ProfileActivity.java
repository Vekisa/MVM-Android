package com.example.mvm;

import androidx.annotation.Nullable;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.mvm.model.Category;
import com.example.mvm.model.Image;
import com.example.mvm.model.User;
import com.example.mvm.services.CategoryService;
import com.example.mvm.services.ImageService;
import com.example.mvm.services.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends NavigationActivity implements View.OnClickListener{

    private UserService userService = new UserService();
    private CategoryService catService = new CategoryService();
    private EditText name;
    private Spinner category;
    ImageView imageView;
    FloatingActionButton uploadButton;
    Image image = new Image();
    TextInputEditText nameEdit;
    //TextInputEditText usernameEdit;
    Spinner categoryEdit;
    User user = UserService.findLoggedIn();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        image.setUserId(user.getId());

        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_profile, null, false);
        drawer.addView(view, 0);
        navigationView.setCheckedItem(R.id.nav_profile);

        imageView = (ImageView) findViewById(R.id.imageView);
        String content = ImageService.obtain(image).getContent();
        if(content != null){
            imageView.setImageBitmap(ImageService.String2Bitmap(content));
        }

        uploadButton = (FloatingActionButton) findViewById(R.id.uploadButton);
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        nameEdit = (TextInputEditText) findViewById(R.id.nameEdit);
        nameEdit.setText(user.getName());

        /*usernameEdit = (TextInputEditText) findViewById(R.id.usernameEdit);
        usernameEdit.setText(user.getUsername());*/

        categoryEdit = (Spinner) findViewById(R.id.spinner);
        List<String> categoriesSpinner = new ArrayList<String>();
        int position = 0;
        for(Category cat : CategoryService.findAll()){
            categoriesSpinner.add(cat.getName());
            if(cat.getName().equals(user.getCategory())){
                position = categoriesSpinner.size()-1;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryEdit.setAdapter(adapter);
        categoryEdit.setSelection(position);
    }

    private void selectImage(){
        final CharSequence[] options = {"Fotografišite se", "Izaberite iz galerije", "Odustanite"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
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

    public void onPasswordClick(View v){
        Intent passwordEdit = new Intent(getApplicationContext(),PasswordActivity.class);
        startActivity(passwordEdit);
    }

    @Override
    public void onClick(View view) {

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
                    imageView.setImageBitmap(bitmap);

                    image.setContent(ImageService.BitMapToString(bitmap));
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
                imageView.setImageBitmap(thumbnail);
                image.setContent(ImageService.BitMapToString(thumbnail));
            }
        }
    }

    public void onSaveChangesClick(View v){
        if(image.getContent() != null){
            ImageService.send(ProfileActivity.this, image);
        }
        user.setName(nameEdit.getText().toString());
        user.setCategory(categoryEdit.getSelectedItem().toString());
        UserService.save(user);
    }
}
