package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

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
        Button backButton = findViewById(R.id.returnButton);
        FireStoreBridge fb = new FireStoreBridge("EVENT");

        Intent intent = getIntent();
        String eventID = intent.getStringExtra("eventID");
        String eventName = intent.getStringExtra("eventName");
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,messagesHolder);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messagesAdapter = new ArrayAdapter<>(OrganizerNotification.this, R.layout.recycler_view_row, messagesHolder);
        fb.retrieveEventAnnouncement(eventID, new FireStoreBridge.OnRetrieveEventAnnouncement() {
            @Override
            public void onRetrieveEventAnnouncement(ArrayList<String> announcements) {
                if (announcements == null || announcements.isEmpty()) {
                    announcements.add("No Announcement"); // Directly modify announcements if it's empty or null
                }
                messagesHolder.clear(); // Clear existing data
                messagesHolder.addAll(announcements); // Add all announcements to the holder
                adapter.notifyDataSetChanged(); // Notify the RecyclerViewAdapter of the dataset change
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
