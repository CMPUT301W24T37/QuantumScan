package com.example.quantumscan;

public class Attendee {
    private final User user;
    private final Event event;
    private final String userID;
    private final String userName;
    private Boolean checkIn = false;

    // Constructors
    public Attendee(User user, Event event) {
        this.user = user;
        this.event = event;
        this.userID = user.getId();
        this.userName = user.getName();
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
    public Event getEvent() { return event; }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }
}
