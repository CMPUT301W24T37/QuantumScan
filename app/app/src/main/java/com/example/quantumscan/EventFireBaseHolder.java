package com.example.quantumscan;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class EventFireBaseHolder {
    private ArrayList<String> announcements;
    private String eventCode;
    private String id;
    private String organizer;
    private String posterCode;
    private String title;
    private String description;
    private long attendeeLimit;

    private long currentTotalAttendee;




    public EventFireBaseHolder() {
        this.announcements = new ArrayList<>();
        this.description = "description";
        this.eventCode = "eventCode";
        this.id = "id";
        this.organizer = "organizer";
        this.posterCode = "posterCode";
        this.title = "title";

    }
    public EventFireBaseHolder(ArrayList<String> announcements, String description, String eventCode,
                               String id, String organizer, String posterCode, String title,
                               long attendeeLimit, long currentTotalAttendee) {
        this.announcements = announcements;
        this.description = description;
        this.eventCode = eventCode;
        this.id = id;
        this.organizer = organizer;
        this.posterCode = posterCode;
        this.title = title;
        this.attendeeLimit = attendeeLimit;
        this.currentTotalAttendee = currentTotalAttendee;

    }


    public ArrayList<String> getAnnouncements() {
        return announcements;
    }

    public long getAttendeeLimit() {
        return attendeeLimit;
    }

    public void setAttendeeLimit(long attendeeLimit) {
        this.attendeeLimit = attendeeLimit;
    }

    public long getCurrentTotalAttendee() {
        return currentTotalAttendee;
    }

    public void setCurrentTotalAttendee(long currentTotalAttendee) {
        this.currentTotalAttendee = currentTotalAttendee;
    }
    public void setAnnouncements(ArrayList<String> announcements) {
        this.announcements = announcements;
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





}
