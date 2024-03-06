package com.example.quantumscan;

import android.util.Log;

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


        FireStoreBridge fb = new FireStoreBridge("USER");
        fb.retrieveUser("1658f5315ca1a74d", new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user) {
                myUser = user;
            }

        });

    }

    public EventAdapter getEventAdapter() {
        return events;
    }

    public void setEventAdapter(EventAdapter events) {
        this.events = events;
    }


}

