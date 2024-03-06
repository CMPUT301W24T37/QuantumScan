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

    public setCheckIn( Boolean checkInStatus){
        this.checkIn = checkInStatus;
    }
    
    public getCheckIn( Boolean checkInStatus){
        this.checkIn = checkInStatus;
    }

    public Atten

    // Setters and Getters

    public User getUser() {
        return user;
    }

    // You should never call this method
    /*
    public void setUser(User user) {
        this.user = user;
    }
     */

    public Event getEvent() {
        return event;
    }

    // You should never call this method
    /*
    public void setEvent(Event event) {
        this.event = event;
    }
     */
}
