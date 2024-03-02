package com.example.authtest;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FireStoreBridge {
    private FirebaseFirestore db;
    private CollectionReference collectionName;
    public FireStoreBridge(String collectionName){
        db = FirebaseFirestore.getInstance();
        this.collectionName = db.collection(collectionName);
    }

    public boolean retrieveUser(String userID, ArrayList<Attendee> attendeeList){

        return false;
    }

    public boolean retrieveEvent(String eventID, ArrayList<Event> eventList){
       return false;
    }

    public boolean updateUser(String userID, User user){

        return false;
    }


    public boolean updateEvent(String eventID, Event event){
        return false;
    }


}

