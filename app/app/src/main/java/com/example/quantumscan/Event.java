package com.example.quantumscan;

public class Event {
    private String id;
    private String title;
    private String description;
    // Simplified check-in status flag
    private boolean isCheckedIn;

    // Other fields that your event might have, e.g., location, date, etc.

    public Event() {
        // Default constructor required for Firestore data mapping
    }

    public Event(String id, String title, String description, boolean isCheckedIn) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCheckedIn = isCheckedIn;
        // Initialize other fields as needed
    }

    // Getters and setters

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

    public boolean isCheckedIn() {
        return isCheckedIn;
    }

    public void setCheckedIn(boolean isCheckedIn) {
        this.isCheckedIn = isCheckedIn;
    }

    // Getters and setters for any other fields
}


