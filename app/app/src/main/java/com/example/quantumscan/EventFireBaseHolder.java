package com.example.quantumscan;

import java.util.ArrayList;

public class EventFireBaseHolder {
    private ArrayList<String> announcements;
    private String eventCode;
    private String id;
    private String organizer;
    private String posterCode;
    private String title;

    public EventFireBaseHolder() {
        this.announcements = new ArrayList<>();
        this.description = "description";
        this.eventCode = "eventCode";
        this.id = "id";
        this.organizer = "organizer";
        this.posterCode = "posterCode";
        this.title = "title";

    }
    public EventFireBaseHolder(ArrayList<String> announcements, String description, String eventCode, String id, String organizer, String posterCode, String title) {
        this.announcements = announcements;
        this.description = description;
        this.eventCode = eventCode;
        this.id = id;
        this.organizer = organizer;
        this.posterCode = posterCode;
        this.title = title;

    }

    private String description;

    public ArrayList<String> getAnnouncements() {
        return announcements;
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
