package com.example.quantumscan;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class AdminHomepage extends AppCompatActivity {
    ListView eventListView;
    ArrayAdapter<String> eventAdapter;
    ArrayList<String> dataList;
    ArrayList<String> eventIDList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_homepage);

        eventListView = findViewById(R.id.adminEventList);

        String []events ={"Event 1", "Event 2", "Event 3"};
        String []eventID ={"abcd","efjh","ijklm"};

        dataList = new ArrayList<>();
        eventIDList = new ArrayList<>();

        dataList.addAll(Arrays.asList(events));
        eventIDList.addAll(Arrays.asList(eventID));
        eventAdapter = new ArrayAdapter<>(this, R.layout.event_content, dataList);
        eventListView.setAdapter(eventAdapter);

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEventName = dataList.get(position);
                String selectedEventID = eventIDList.get(position);
                Intent detailIntent = new Intent(AdminHomepage.this, AdminEventPage.class);
                detailIntent.putExtra("eventID", selectedEventID);
                detailIntent.putExtra("eventName", selectedEventName);
                startActivity(detailIntent);
            }
        });


    }

}
