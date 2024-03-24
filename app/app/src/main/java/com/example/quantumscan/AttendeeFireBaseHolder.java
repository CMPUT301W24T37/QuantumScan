package com.example.quantumscan;

public class AttendeeFireBaseHolder {
    // TODO: This is dogshit, but do not delete it
    private boolean checkInStatus;
    private String attendeeId;

    private String name;
    private long checkInCount;
    public AttendeeFireBaseHolder(String attendeeId, boolean checkInStatus) {
        this.attendeeId = attendeeId;
        this.checkInStatus = checkInStatus;

    }
    public AttendeeFireBaseHolder() {

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public long getCheckInCount() {
        return checkInCount;
    }

    public void setCheckInCount(long checkInCount) {
        this.checkInCount = checkInCount;
    }

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
