package com.example.quantumscan;

import java.util.ArrayList;

/*
    Todo: 1. implement Firestore/Firebase code. (currently only local)
          2. complete other needed features of the class
 */

/**
 * Represent one single user.
 */
// Important note before you calling User()!
// Do not call User's constructors directly if you wanna a new user, call the method in Authentication's accountCreation() to create a new User!
// Meaning: the caller of User constructors should be responsible for generating an unique user id.
public class User {
    private String id;
    private ArrayList<Attendee> attendeeRoles;
    private ArrayList<Organizer> organizerRoles;
    private String name;
    private String phone;
    private String email;
    private String university;
    private String profilePicture;  // should be the URL of the picture? Need to allow users to upload their pfp onto the Firebase database?

    // Constructors
    // Todo: maybe we can default all variable to null, this way we only need one constructor? we can deal with null attribute within constructor?
    public User(String name, String phone, String university, String profilePicture, String email) {
        this.id = "PLACE_HOLDER";  // this will be changed in somewhere else, so just level it there
        this.attendeeRoles = new ArrayList<>();
        this.organizerRoles = new ArrayList<>();
        this.name = name;
        this.phone = phone;
        this.university = university;
        this.profilePicture = profilePicture;
        this.email = email;
    }

    public User(String userId, String name, String profilePicture) {
        this.id = userId;
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<Organizer>();
        this.name = name;
        this.profilePicture = profilePicture;
    }

    public User(String userId, String name) {
        this.id = userId;
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<Organizer>();
        this.name = name;
        this.profilePicture = "DEFAULT_PFP";  // Todo: replace it with the generateProfilePictureByName() in the future (in part4 perhaps)
    }

    public User(String userId) {
        this.id = userId;
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<Organizer>();
        this.name = "New User";  // default user name
        this.profilePicture = "DEFAULT_PFP";  // Todo: replace it with the generateProfilePictureByName() in the future (in part4 perhaps)
    }

    public User() {
        this.id = "PLACE_HOLDER";
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<Organizer>();
        this.name = "New User";  // default user name
        this.profilePicture = "DEFAULT_PFP";  // Todo: replace it with the generateProfilePictureByName() in the future (in part4 perhaps)
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    //  US 03.01.03: As a user, I want to update information such as name, homepage, and contact information on my profile.
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    // US 03.01.01: As a user, I want to upload a profile picture for a more personalized experience.
    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Methods

    /**
     * This add an Attendee object (userId + eventID) into the Event's signed up attendee list
     * Representing the user signed up for an EXISTING specific event.
     * @param event
     *  Event: one specific event which has an unique event_id
     */
    public void addAttendeeRole(Event event) {
        // create a new Attendee object by passing User object and Event object
        Attendee attendee = new Attendee(this, event);
        this.attendeeRoles.add(attendee);
        // add the Attendee in the Event object
        event.addAttendee(attendee);
    }

    /**
     * This add an Organizer object in organizerRoles (userId + eventID) by creating a NEW event
     * Representing the user hosting a new event.
     */
    public void addOrganizerRole() {
        // Create a new event
        Event event = new Event();
        // Assign an unique ID for eventID
        event.EventIdGenerator(this.getId());
        // create a new Organizer object by passing User object and Event object
        Organizer organizer = new Organizer(this, event);
        this.organizerRoles.add(organizer);
        // set the organizer in the Event object
        event.setOrganizer(organizer);
    }
}
