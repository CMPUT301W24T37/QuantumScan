package com.example.quantumscan;

import java.util.ArrayList;

/*
    Todo: 1. implement Firestore/Firebase code. (currently only local)
          2. complete other needed features of the class
          3. current version is not working b/c missing other classes' methods or constructors
 */

/**
 * Represent one single user.
 */
public class User {
    private String userId;  // this could be int, to be discussed. This is automatically generated from the users' phone.
    private ArrayList<Attendee> attendeeRoles;
    private ArrayList<Organizer> organizerRoles;
    private String name;
    private String homePage;
    private String contactInformation;
    private String profilePicture;  // should be the URL of the picture? Need to allow users to upload their pfp onto the Firebase database?

    // Constructors
    public User(String userId, String name, String profilePicture) {
        this.userId = userId;
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<Organizer>();
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public User(String userId, String name) {
        this.userId = userId;
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<Organizer>();
        this.name = name;
        this.profilePicture = "DEFAULT_PFP";  // Todo: replace it with the generateProfilePictureByName() in the future (in part4 perhaps)
    }

    public User(String userId) {
        this.userId = userId;
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<Organizer>();
        this.name = "New User";
        this.profilePicture = "DEFAULT_PFP";  // Todo: replace it with the generateProfilePictureByName() in the future (in part4 perhaps)
    }

    // Getters and Setters

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<Attendee> getAttendeeRoles() {
        return attendeeRoles;
    }

    public void setAttendeeRoles(ArrayList<Attendee> attendeeRoles) {
        this.attendeeRoles = attendeeRoles;
    }

    public ArrayList<Organizer> getOrganizerRoles() {
        return organizerRoles;
    }

    public void setOrganizerRoles(ArrayList<Organizer> organizerRoles) {
        this.organizerRoles = organizerRoles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Methods

    /**
     * This add an Attendee object (userId + eventID)
     * Representing the user signed up for an EXISTING specific event.
     * @param event
     *  Event: one specific event which has an unique event_id
     */
    public void addAttendeeRole(Event event) {
        // create a new Attendee object by passing User object and Event object
        Attendee attendee = new Attendee(this, event);  // Todo: implement the constructor in Attendee
        this.attendeeRoles.add(attendee);
    }

    /**
     * This add an Organizer object in organizerRoles (userId + eventID) by creating a NEW event
     * Representing the user hosting a new event.
     */
    public void addOrganizerRole() {
        // Create a new event
        Event event = new Event();
        // create a new Attendee object by passing User object and Event object
        Organizer organizer = new Organizer(this, event);  // Todo: implement the constructor in Organizer
        this.organizerRoles.add(organizer);
    }
}