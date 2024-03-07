package com.example.quantumscan;

import java.util.ArrayList;

public class EventFireBaseHolder {
    private ArrayList<String> announcement;
    private String eventCode;
    private String id;
    private String organizer;
    private String posterCode;
    private String title;
    private ArrayList<AttendeeListFireBaseHolder> attendeeList;

    public EventFireBaseHolder(ArrayList<String> announcments, String description, String eventCode, String id, String organizer, String posterCode, String title, ArrayList<AttendeeListFireBaseHolder> attendeeList) {
        this.announcement = announcments;
        this.description = description;
        this.eventCode = eventCode;
        this.id = id;
        this.organizer = organizer;
        this.posterCode = posterCode;
        this.title = title;
        this.attendeeList = attendeeList;
    }

    private String description;

    public ArrayList<String> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(ArrayList<String> announcement) {
        this.announcement = announcement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getPosterCode() {
        return posterCode;
    }

    public void setPosterCode(String posterCode) {
        this.posterCode = posterCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<AttendeeListFireBaseHolder> getAttendeeList() {
        return attendeeList;
    }

    public void setAttendeeList(ArrayList<AttendeeListFireBaseHolder> attendeeList) {
        this.attendeeList = attendeeList;
    }




}
