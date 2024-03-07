package com.example.quantumscan;

public class AttendeeListFireBaseHolder {
    private String id;
    private boolean checkedIn;


    public AttendeeListFireBaseHolder(String id, boolean checkedIn) {
        this.id = id;
        this.checkedIn = checkedIn;
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
