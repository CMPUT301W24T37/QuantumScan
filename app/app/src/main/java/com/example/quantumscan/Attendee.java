package com.example.quantumscan;

public class Attendee {
    private final User user;
    private final Event event;



    private Boolean checkIn = false;

    // Constructors

    public Attendee(User user, Event event) {
        this.user = user;
        this.event = event;
    }


    // Setters and Getters
    public Boolean getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }
    public User getUser() {
        return user;
    }


}
