package com.example.quantumscan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class UserEventFragment extends Fragment {
    ListView eventListView;
    ArrayAdapter<String> eventAdapter;
    ArrayList<String> dataList;
    ArrayList<String> eventIDList;
    Admin admin;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_homepage, container, false);

        eventListView = view.findViewById(R.id.adminEventList);

        //String []events ={"Event 1", "Event 2", "Event 3"};
        //String []eventID ={"abcd","efjh","ijklm"};

        dataList = new ArrayList<>();
        eventIDList = new ArrayList<>();
        admin = new Admin();

        eventAdapter = new ArrayAdapter<>(view.getContext(), R.layout.event_content, dataList);
        eventListView.setAdapter(eventAdapter);

        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            DisplayMetrics displayMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            // Your code to set the height
            int screenHeight = displayMetrics.heightPixels;

            // Set ListView height to screenHeight - 100 pixels
            ViewGroup.LayoutParams params = eventListView.getLayoutParams();
            params.height = screenHeight - 450;
            eventListView.setLayoutParams(params);
        }


//        dataList.addAll(Arrays.asList(events));
//        eventIDList.addAll(Arrays.asList(eventID));


        admin.retrieveAllEvent(new Admin.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> events) {
                for (Event event : events) {  // there is always only one event in events, you can also access it using events.get(0)
                    System.out.println("Event:" + event.getId());
                    dataList.add(event.getTitle());
                    eventIDList.add(event.getId());
                }
                eventAdapter.notifyDataSetChanged();
            }
        });



        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEventName = dataList.get(position);
                String selectedEventID = eventIDList.get(position);

                Intent detailIntent = new Intent(getActivity(), AdminEventInfo.class);
                detailIntent.putExtra("eventID", selectedEventID);
                detailIntent.putExtra("eventName", selectedEventName);
                startActivity(detailIntent);

            }
        });
        return view;

    }
}

