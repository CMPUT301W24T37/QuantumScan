package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class OrganizerFragment extends Fragment {
    ListView eventList;
    Button buttonCreate;
    ArrayAdapter<String> eventAdapter;
    ArrayList<String> dataList;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organizer, container, false);
        eventList = view.findViewById(R.id.organizerEventList);
        buttonCreate = view.findViewById(R.id.buttonCreate);


        String []events ={"CMPUT 301", "Lab Team", "CMPUT 291"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(events));
        eventAdapter = new ArrayAdapter<>(getActivity(), R.layout.event_content, dataList);
        eventList.setAdapter(eventAdapter);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrganizerCreate.class);
                startActivity(intent);
            }
        });

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEvent = dataList.get(position);
                Intent detailIntent = new Intent(getActivity(), OrganizerEventFragment.class);
                detailIntent.putExtra("eventName", selectedEvent);
                startActivity(detailIntent);
            }
        });




        return view;
    }
}