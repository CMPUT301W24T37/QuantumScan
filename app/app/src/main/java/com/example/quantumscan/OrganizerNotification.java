package com.example.quantumscan;

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

        String []messages ={"Message 1: To display a list of messages using a RecyclerView in Android Studio, you will need to create an adapter for the RecyclerView that inflates the layout for each item. Below I'll guide you through the main components you will need to set up",
                "Message 2: This is a custom adapter that extends RecyclerView.Adapter. It will bind each message in the array to a view in the RecyclerView",
                "Message 3: This is a custom view holder that extends RecyclerView.ViewHolder. It contains a reference to the TextView where the message will be displayed"};
        messagesHolder.addAll(Arrays.asList(messages));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,messagesHolder);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        messagesAdapter = new ArrayAdapter<>(OrganizerNotification.this, R.layout.recycler_view_row, messagesHolder);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}
