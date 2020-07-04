package com.example.mvm.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mvm.LoginActivity;
import com.example.mvm.R;
import com.example.mvm.authentication.AppProperties;
import com.example.mvm.model.Image;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ImageService {

    public static Response send(final Context context, Image image){
        final ProgressDialog loading = new ProgressDialog(context);
        Response response = null;

        loading.setMessage("Molim sačekajte");
        loading.show();
        loading.setCanceledOnTouchOutside(false);

        Request request = createRequest(image, "/image/save");
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        loading.dismiss();
        return response;
    }

    public static String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        String str = Base64.encodeToString(b, Base64.DEFAULT);
        return str;
    }

    public static Bitmap String2Bitmap(String content){
        Bitmap bitmap = null;
        try {
            byte[] imageByte = Base64.decode(content, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static Image obtain(Image image){
        Request request = createRequest(image, "/image/getImage");
        Response response = null;
        Image retImage = new Image();
        try {
            response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            LinkedTreeMap treeMap = (LinkedTreeMap) new GsonBuilder().create().fromJson(response.body().string(), Object.class);
            retImage.setCategoryId(treeMap.get("categoryId") != null ? treeMap.get("categoryId").toString() : null);
            retImage.setUserId(treeMap.get("userId") != null ? treeMap.get("userId").toString() : null);
            retImage.setContent(treeMap.get("content") != null ? treeMap.get("content").toString() : null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retImage;
    }

    private static Request createRequest(Image image, String url){

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("categoryId", image.getCategoryId());
            jsonObject.put("userId", image.getUserId());
            jsonObject.put("content", image.getContent());
            jsonObject.put("discussionId", image.getDiscussionId());
            jsonObject.put("commentId", image.getCommentId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        return new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + url)
                .post(body)
                .build();
    }

}
