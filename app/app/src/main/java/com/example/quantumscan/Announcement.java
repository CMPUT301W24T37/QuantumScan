package com.example.quantumscan;
import android.os.Build;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Announcement {

    private String announcment;
    private String organizer;

    private String event;
    private  LocalDateTime now;
    public Announcement(String announcment, String organizer, String event) {
        this.announcment = announcment;
        this.organizer = organizer;
        this.event = event;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.now = LocalDateTime.now();
        }


    }

    public String getAnnouncment() {
        return announcment;
    }

    public void setAnnouncment(String announcment) {
        this.announcment = announcment;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTime(){
        String formattedDateTime = "1111-11-11 11:11:11";
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
              formattedDateTime = now.format(formatter);
        }

        return formattedDateTime;
    }





}
