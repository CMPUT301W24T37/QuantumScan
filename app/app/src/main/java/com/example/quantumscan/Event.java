package com.example.quantumscan;

public class Event {
    private String id;
    private String title;
    private String description;
    // Other fields that your event might have, e.g., location, date, etc.

    public Event() {
        // Default constructor required for calls to DataSnapshot.getValue(Event.class)
    }

    public Event(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        // Initialize other fields
    }

    // Getters and setters for each field

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getters and setters for other fields
}
