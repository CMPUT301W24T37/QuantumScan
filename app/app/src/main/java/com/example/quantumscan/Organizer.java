package com.example.quantumscan;

public class Organizer {
    private final User user;
    private final Event event;

    // Constructors

    public Organizer(User user, Event event) {
        this.user = user;
        this.event = event;
    }

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

