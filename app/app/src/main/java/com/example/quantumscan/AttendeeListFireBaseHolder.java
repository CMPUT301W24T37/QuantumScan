package com.example.quantumscan;

public class AttendeeListFireBaseHolder {
    private String id;
    private boolean checkedIn;
    private String name;

    private int checkInCount;


    public AttendeeListFireBaseHolder(String id, boolean checkedIn, String name, int checkInCount) {
        this.id = id;
        this.checkedIn = checkedIn;
        this.name = name;
        this.checkInCount = checkInCount;
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
