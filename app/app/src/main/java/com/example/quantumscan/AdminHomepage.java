package com.example.quantumscan;

import android.os.Bundle;
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
        eventIDList.



    }

}
