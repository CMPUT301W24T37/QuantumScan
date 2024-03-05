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

import java.util.ArrayList;
import java.util.Arrays;


public class OrganizerFragment extends Fragment {
    User myUser;

    ListView eventListView;
    Button buttonCreate;
    ArrayAdapter<String> eventAdapter;
    ArrayList<Organizer> organizerRoles;
    ArrayList<String> dataList;

    ArrayList<Event> eventArrayList;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organizer, container, false);
        eventListView = view.findViewById(R.id.organizerEventList);
        buttonCreate = view.findViewById(R.id.buttonCreate);


        String []events ={"CMPUT 301", "Lab Team", "CMPUT 291"};
        dataList.addAll(Arrays.asList(events));


        /*
        myUser = DataHolder.getInstance().getUserObject();
        organizerRoles = myUser.getOrganizerRoles();
        for (Organizer organizer : organizerRoles) {
            eventArrayList.add(organizer.getEvent());
        }
        this.convertEvent();
         */

        eventAdapter = new ArrayAdapter<>(getActivity(), R.layout.event_content, dataList);
        eventListView.setAdapter(eventAdapter);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrganizerCreateEvent.class);
                startActivity(intent);
            }
        });

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEvent = dataList.get(position);
                Intent detailIntent = new Intent(getActivity(), OrganizerEventPage.class);
                detailIntent.putExtra("eventName", selectedEvent);
                startActivity(detailIntent);
            }
        });




        return view;
    }

    public void convertEvent(){
        for (Event event : eventArrayList) {
            dataList.add(event.getTitle());
        }
    }
}