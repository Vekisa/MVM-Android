package com.example.mvm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Date;

public class MessagesActivity extends AppCompatActivity {

    GridView gridview;
    ArrayList<Message> messages = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        gridview = (GridView) findViewById(R.id.gridview);

        messages.add(new Message("Milica Matijevic", R.drawable.profile2, new Date(),"Je l sam ti lepo rekla da ne prskas krompir sa totalom!"));
        messages.add(new Message("Veljko Mosorinski", R.drawable.profile3, new Date(),"Sta cu jadan, tako mi majka rekla. . ."));
        messages.add(new Message("Milica Matijevic", R.drawable.profile2, new Date(),"Prestani da se bavis poljoprivredom, nije to za tebe."));

        MessageAdapter adapter = new MessageAdapter(this, R.layout.discussion_item, messages);
        gridview.setAdapter(adapter);
    }
}
