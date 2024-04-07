package com.example.quantumscan;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class OrganizerNotification extends AppCompatActivity {

    ArrayList<String> messagesHolder = new ArrayList<>();
    ArrayAdapter<String> messagesAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_notification);
        RecyclerView recyclerView = findViewById(R.id.myRecyclerView);

        String []messages ={"Message 1", "Message 2", "Message 3"};
        messagesHolder.addAll(Arrays.asList(messages));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,messagesHolder);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        messagesAdapter = new ArrayAdapter<>(OrganizerNotification.this, R.layout.recycler_view_row, messagesHolder);



    }
}
