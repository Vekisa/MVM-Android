package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mvm.authentication.AppProperties;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class MessagesActivity extends AppCompatActivity {

    GridView gridview;
    ArrayList<Comment> comments = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);


        Long id = getIntent().getLongExtra("selected_discussion",0L);

        Request request = new Request.Builder()
                .url(AppProperties.getInstance().getServerUrl() + "/comment?"+"id="+id)
                .addHeader("Content-type", "application/x-www-form-urlencoded; charset=utf-8")
                .addHeader("Authorization", "Basic c2NpZW5jZUNlbnRlcjpjbGllbnRQYXNzd29yZA==")
                .build();

        try {
            Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
            if(response.code() == 200){
                Gson gson = new Gson();
                System.out.println("----------------------------------------------------------------------------------");
                List<Object> disc = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(), Object[].class));

                for (Object o:disc){

                    LinkedTreeMap ltm = (LinkedTreeMap) o;

                    Object user = ltm.get("user");

                    LinkedTreeMap linkedTreeMapUser = (LinkedTreeMap) user;

                    Log.d("myTag", String.valueOf(linkedTreeMapUser));

                    String name = linkedTreeMapUser.get("name").toString();

                    Double  idD = Double.parseDouble(ltm.get("id").toString());
                    long idCom = Math.round(idD);

                    Log.d("myTag", ltm.get("posted").toString());

                    String strDate = ltm.get("posted").toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    Date date = null;
                    try {
                        date = dateFormat.parse(strDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    System.out.println(date);

                    String content = ltm.get("content").toString();

                    Comment comment = new Comment(name,idCom, R.drawable.profile2,date,content);

                    comments.add(comment);

                }

            }else{
                Toast.makeText(getApplication().getBaseContext (),"Comment problem!",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        gridview = (GridView) findViewById(R.id.gridview);

//        comments.add(new Comment("Milica Matijevic", R.drawable.profile2, new Date(),"Je l sam ti lepo rekla da ne prskas krompir sa totalom!"));
//        comments.add(new Comment("Veljko Mosorinski", R.drawable.profile3, new Date(),"Sta cu jadan, tako mi majka rekla. . ."));
//        comments.add(new Comment("Milica Matijevic", R.drawable.profile2, new Date(),"Prestani da se bavis poljoprivredom, nije to za tebe."));

        MessageAdapter adapter = new MessageAdapter(this, R.layout.discussion_item, comments);
        gridview.setAdapter(adapter);
    }
}
