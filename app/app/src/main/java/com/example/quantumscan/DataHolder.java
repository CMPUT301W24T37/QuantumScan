package com.example.quantumscan;

import android.util.Log;

import java.util.ArrayList;

public class DataHolder {
    private static final DataHolder instance = new DataHolder();
    private User myUser;
    private EventAdapter events;


    public static DataHolder getInstance() {
        return instance;
    }

    public User getUserObject() {
        return myUser;
    }

    public void setMyUser() {



    }

    public EventAdapter getEventAdapter() {
        return events;
    }

    public void setEventAdapter(EventAdapter events) {
        this.events = events;
    }


}

