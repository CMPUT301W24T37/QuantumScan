package com.example.quantumscan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


public class OrganizerFragment extends Fragment {
    User myUser;

    ListView eventListView;
    Button buttonCreate;
    ArrayAdapter<String> eventAdapter;
    ArrayList<String> organizerRole;

    ArrayList<String> dataList;
    ArrayList<String> eventIDList;


    // Initialize the launcher with the contract and callback to handle the result
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    if (data != null) {
                        // Handle the data from the result
                        String eventID = data.getStringExtra("eventID");
                        String eventName = data.getStringExtra("eventName");
                        // Use the returned value as needed
                        dataList.add(eventName);
                        eventIDList.add(eventID);
                        eventAdapter.notifyDataSetChanged();


                    }
                }
            });




    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_organizer, container, false);
        eventListView = view.findViewById(R.id.organizerEventList);
        buttonCreate = view.findViewById(R.id.buttonCreate);


        //String []events ={"CMPUT 301", "Lab Team", "CMPUT 291"};
        dataList = new ArrayList<>();
        eventIDList = new ArrayList<>();
        //dataList.addAll(Arrays.asList(events));
        eventAdapter = new ArrayAdapter<>(view.getContext(), R.layout.event_content, dataList);
        
        FireStoreBridge fb = new FireStoreBridge("USER");
        String userId = Settings.Secure.getString(this.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        fb.retrieveUser(userId, new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
                eventIDList.clear();
                for(String event : organizerRoles){
                    eventIDList.add(event);
                    System.out.println(event);
                }

                FireStoreBridge fb_events = new FireStoreBridge("EVENT");
                fb_events.retrieveAllEvent(new FireStoreBridge.OnEventRetrievedListener() {
                    @Override
                    public void onEventRetrieved(ArrayList<Event> events, ArrayList<String> organizerList) {
                        dataList.clear();
                        for(String eventID : eventIDList){

                            for(Event event: events){
                                if(Objects.equals(eventID, event.getId())){
                                    System.out.println("Size"+ event.getTitle());
                                    dataList.add(event.getTitle());
                                }
                            }
                        }
                        eventAdapter.notifyDataSetChanged();

                    }
                });

            }
        });




        eventListView.setAdapter(eventAdapter);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OrganizerCreateEvent.class);
                mStartForResult.launch(intent);
            }
        });

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedEventName = dataList.get(position);
                String selectedEventID = eventIDList.get(position);
                Intent detailIntent = new Intent(getActivity(), OrganizerEventPage.class);
                detailIntent.putExtra("eventID", selectedEventID);
                detailIntent.putExtra("eventName", selectedEventName);
                startActivity(detailIntent);
            }
        });




        return view;
    }

    /*
    public void convertEvent(){
        for (Event event : eventArrayList) {
            dataList.add(event.getTitle());
        }
    }

     */
}