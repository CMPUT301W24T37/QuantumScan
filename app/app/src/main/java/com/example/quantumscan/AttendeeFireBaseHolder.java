package com.example.quantumscan;

public class AttendeeFireBaseHolder {
    // TODO: This is dogshit, but do not delete it
    private boolean checkInStatus;
    public AttendeeFireBaseHolder(String attendeeId, boolean checkInStatus) {
        this.attendeeId = attendeeId;
        this.checkInStatus = checkInStatus;

    }
    public AttendeeFireBaseHolder() {

    }
    private String attendeeId;

    public String getAttendeeId() {
        return attendeeId;
    }

    public void setAttendeeId(String attendeeId) {
        this.attendeeId = attendeeId;
    }

    public boolean isCheckInStatus() {
        return checkInStatus;
    }

    public void setCheckInStatus(boolean checkInStatus) {
        this.checkInStatus = checkInStatus;
    }



}
