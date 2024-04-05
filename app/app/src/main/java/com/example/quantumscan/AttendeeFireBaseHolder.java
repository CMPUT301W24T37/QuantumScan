// AttendeeFireBaseHolder


package com.example.quantumscan;

import com.google.firebase.firestore.GeoPoint;

public class AttendeeFireBaseHolder {
    private String id;
    private boolean checkedIn;
    private String name;

    private int checkInCount;
    private GeoPoint location;  // this will be assigned only when the Attendee checked in the event.



    public AttendeeFireBaseHolder(String id, boolean checkedIn, String name, int checkInCount) {
        this.id = id;
        this.checkedIn = checkedIn;
        this.name = name;
        this.checkInCount = checkInCount;
        this.location = null;
    }

    public AttendeeFireBaseHolder() {
        this.location = null;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCheckInCount() {
        return checkInCount;
    }

    public void setCheckInCount(int checkInCount) {
        this.checkInCount = checkInCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCheckedIn() {
        return checkedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }


}
