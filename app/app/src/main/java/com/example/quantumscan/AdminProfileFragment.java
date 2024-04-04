package com.example.quantumscan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class AdminProfileFragment extends Fragment {
    ListView eventListView;
    ArrayAdapter<String> eventAdapter;
    ArrayList<String> dataList;
    ArrayList<String> userIDList;
    Admin admin;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_profile_homepage, container, false);

        eventListView = view.findViewById(R.id.adminProfileList);

        //String []events ={"Event 1", "Event 2", "Event 3"};
        //String []eventID ={"abcd","efjh","ijklm"};

        dataList = new ArrayList<>();
        userIDList = new ArrayList<>();
        admin = new Admin();

        eventAdapter = new ArrayAdapter<>(view.getContext(), R.layout.event_content, dataList);
        eventListView.setAdapter(eventAdapter);

//        dataList.addAll(Arrays.asList(events));
//        eventIDList.addAll(Arrays.asList(eventID));


        admin.retrieveAllUser(new Admin.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(ArrayList<User> users) {
                if (users == null) {
                    return;
                }

                for (User user : users) {
                    System.out.println("Event:" + user.getId());
                    dataList.add(user.getName());
                    userIDList.add(user.getId());
                }
                eventAdapter.notifyDataSetChanged();
            }
        });



        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEventName = dataList.get(position);
                String selectedEventID = userIDList.get(position);
                Intent detailIntent = new Intent(getActivity(), AdminProfileDetail.class);
                detailIntent.putExtra("userID", selectedEventID);
                detailIntent.putExtra("userName", selectedEventName);
                startActivity(detailIntent);
            }
        });
        return view;

    }
}
