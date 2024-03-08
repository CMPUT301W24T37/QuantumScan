package com.example.quantumscan;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class OrganizerFireBaseHolder {
    // TODO: This is dogshit, but do not delete it

    private String organizerID;
    private ArrayList<AttendeeFireBaseHolder> attendeeList = new ArrayList<>();

    public OrganizerFireBaseHolder(String organizerID, ArrayList<AttendeeFireBaseHolder> attendeeList) {
        this.organizerID = organizerID;
        this.attendeeList = attendeeList;
    }

    public OrganizerFireBaseHolder(){

    }

    public String getOrganizerID() {
        return organizerID;
    }

    public void setOrganizerID(String organizerID) {
        this.organizerID = organizerID;
    }

    public ArrayList<AttendeeFireBaseHolder> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(ArrayList<AttendeeFireBaseHolder> attendeeList) {
        this.attendeeList = attendeeList;
    }

}

