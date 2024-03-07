package com.example.quantumscan;

import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

public class DataHolder {
    private static final DataHolder instance = new DataHolder();
    private User myUser;
    private ArrayList<Event> myEvents;
    private FireStoreBridge fb_events = new FireStoreBridge("EVENT");

    public static DataHolder getInstance() {
        return instance;
    }

    public User getUserObject() {
        return myUser;
    }

    public void setMyUser() {



    }

    public Event getEvent(String eventID) {
        /*
        for(Event event : events){
            if(Objects.equals(event.getId(), eventID)){
                return myEvents;
            }
        }
        return null;*/
        return myEvents.get(0);
    }

    public void setEvents() {
        /*

        FireStoreBridge fb_events = new FireStoreBridge("EVENT");
        fb_events.retrieveAllEvent(new FireStoreBridge.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> events, ArrayList<String> organizerList) {
                myEvents.addAll(events);
            }
        });
         */
    }


}

