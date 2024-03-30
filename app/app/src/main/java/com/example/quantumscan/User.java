package com.example.quantumscan;

import java.util.ArrayList;

/*
    Todo: 1. implement Firestore/Firebase code. (currently only local)
          2. complete other needed features of the class
 */


// Important note before you calling User()!
// Do not call User's constructors directly if you wanna a new user, call the method in Authentication's accountCreation() to create a new User!
// Meaning: the caller of User constructors should be responsible for generating an unique user id.
/**
 * Represent one single user.
 * @author Austin, Wei
 * @see Attendee
 * @see Organizer
 * @version 0.0.00001
 */
public class User {
    /**
     * {@link String} - The user id
     */
    private String id;
    /**
     * {@link ArrayList<Attendee>} - The ArrayList that contain Attendee objects, representing a user signed up as an Attendee of one Event
     */
    private ArrayList<Attendee> attendeeRoles;
    /**
     * {@link ArrayList<Organizer>} - The ArrayList that contain Organizer objects, representing a user host a new Event as an Organizer
     */
    private ArrayList<String> organizerRoles;
    /**
     * {@link String} - The user name
     */
    private String name;
    /**
     * {@link String} - The user phone
     */
    private String phone;
    /**
     * {@link String} - The user email
     */
    private String email;
    /**
     * {@link String} - The user university
     */
    private String university;
    /**
     * {@link String} - The user profile picture
     */
    private String profilePicture;  // should be the URL of the picture? Need to allow users to upload their pfp onto the Firebase database?

    // Constructors
    // Todo: maybe we can default all variable to null, this way we only need one constructor? we can deal with null attribute within constructor?

    /**
     * User Constructor
     * @param name {@link String} - the name of the user
     * @param phone {@link String} - the phone of the user
     * @param university {@link String} - the university of the user
     * @param profilePicture {@link String} - the profile picture of the user
     * @param email {@link String} - the email of the user
     */
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

    /**
     * User Constructor
     * @param userId {@link String} - the unique id of the user
     * @param name {@link String} - the name of the user
     * @param profilePicture {@link String} - the profile picture of the user
     */
    public User(String userId, String name, String profilePicture) {
        this.id = userId;
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<String>();
        this.name = name;
        this.profilePicture = profilePicture;
    }

    /**
     * User Constructor
     * @param userId {@link String} - the unique id of the user
     * @param name {@link String} - the name of the user
     */
    public User(String userId, String name) {
        this.id = userId;
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<String>();
        this.name = name;
        this.profilePicture = "DEFAULT_PFP";  // Todo: replace it with the generateProfilePictureByName() in the future (in part4 perhaps)
    }

    /**
     * User Constructor
     * @param userId {@link String} - the unique id of the user
     */
    public User(String userId) {
        this.id = userId;
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<String>();
        this.name = "New User";  // default user name
        this.profilePicture = "DEFAULT_PFP";  // Todo: replace it with the generateProfilePictureByName() in the future (in part4 perhaps)
    }

    /**
     * User Constructor
     */
    public User() {
        this.id = "PLACE_HOLDER";
        this.attendeeRoles = new ArrayList<Attendee>();
        this.organizerRoles = new ArrayList<String>();
        this.name = "New User";  // default user name
        this.profilePicture = "DEFAULT_PFP";  // Todo: replace it with the generateProfilePictureByName() in the future (in part4 perhaps)
    }

    // Getters and Setters

    /**
     * This returns the id of the user
     * @return {@link String}<br>The id of the user
     */
    public String getId() {
        return id;
    }

    /**
     * This takes the user id(String) and sets as user's id
     * @param id {@link String}<br>The id of the user
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This returns the {@link ArrayList<Attendee>} of the User
     * @return {@link ArrayList<Attendee>} The ArrayList of the Attendee
     */
    public ArrayList<Attendee> getAttendeeRoles() {
        return attendeeRoles;
    }

    /**
     * takes a {@link ArrayList<Attendee>} and set it as the user's attendees list
     * @param attendeeRoles {@link ArrayList<Attendee>} The ArrayList of the Attendee
     */
    public void setAttendeeRoles(ArrayList<Attendee> attendeeRoles) {
        this.attendeeRoles = attendeeRoles;
    }

    /**
     * This returns the {@link ArrayList<Organizer>} of the User
     * @return {@link ArrayList<Organizer>} The ArrayList of the Organizer
     */
    public ArrayList<String> getOrganizerRoles() {
        return organizerRoles;
    }

    /**
     * takes a {@link ArrayList<Organizer>} and set it as the user's Organizer list
     * @param organizerRoles {@link ArrayList<Organizer>} The ArrayList of the Organizer
     */
    public void setOrganizerRoles(ArrayList<String> organizerRoles) {
        this.organizerRoles = organizerRoles;
    }

    //  US 03.01.03: As a user, I want to update information such as name, homepage, and contact information on my profile.

    /**
     * This returns the name of the User
     * @return {@link String} - the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * This takes a String of the name and set it as the user's name
     * @param name {@link String} - user name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This returns the phone of the user
     * @return {@link String} - the phone of the user
     */
    public String getPhone() {
        return phone;
    }

    /**
     * This takes a String of the phone and set it as the user's phone
     * @param phone {@link String} - user phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * This returns the email of the user
     * @return {@link String} - the email of the user
     */
    public String getEmail() {
        return email;
    }
    /**
     * This takes a String of the email and set it as the user's email
     * @param email {@link String} - user email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * This returns the university of the user
     * @return {@link String} - the university of the user
     */
    public String getUniversity() {
        return university;
    }

    /**
     * This takes a String of the university and set it as the user's university
     * @param university {@link String} - user university
     */
    public void setUniversity(String university) {
        this.university = university;
    }

    // US 03.01.01: As a user, I want to upload a profile picture for a more personalized experience.
    /**
     * This returns the profile picture of the user
     * @return {@link String} - the profile picture of the user
     */
    public String getProfilePicture() {
        return profilePicture;
    }
    /**
     * This takes a String of the profile picture and set it as the user's profile picture
     * @param profilePicture {@link String} - user profile picture
     */
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Methods
    // 6 methods below for part4
//    public Boolean ifAlreadySignedEvent(Event event) {
//        for (Attendee attendee : event.getAttendees()) {
//            if (attendee.getUser() == this) {return true;}
//        }
//        return false;
//    }
//    public Boolean ifAlreadyHostingEvent(Event event) {
//        return (event.getOrganizer().getUser() == this);
//    }
//
//    public void deleteAttendeeRole(Attendee attendee) {
//        this.attendeeRoles.remove(attendee);
//    }
//
//    public void deleteOrganizerRole(Organizer organizer) {
//        this.organizerRoles.remove(organizer);
//    }
//
//    // get
//    public Attendee getAttendeeByEvent(Event event) {
//        for (Attendee attendee : event.getAttendees()) {
//            if (attendee.getUser() == this) {return attendee;}
//        }
//        return null;
//    }
//
//    public Organizer getOrganizerByEvent(Event event) {
//        if (event.getOrganizer().getUser() == this) {
//            return event.getOrganizer();
//        }
//        return null;
//    }

    /**
     * This add an Attendee object (userId + eventID) into the Event's signed up attendee list
     * Representing the user signed up for an EXISTING specific event.
     * @param event
     *  Event: one specific event which has an unique event_id
     */
    public void addAttendeeRole(Event event) {
        // create a new Attendee object by passing User object and Event object
        Attendee attendee = new Attendee(
                getId(),
                false,
                getName(),
                0);
        this.attendeeRoles.add(attendee);
        // add the Attendee in the Event object
        event.addAttendee(attendee);
    }

    /**
     * This add an Organizer object in organizerRoles (userId + eventID) by creating a NEW event
     * Representing the user hosting a new event.
     */
    public Event addOrganizerRole() {
        // Create a new event
        Event event = new Event();
        // Assign an unique ID for eventID
        event.EventIdGenerator(this.getId());
        // create a new Organizer object by passing User object and Event object
        //Organizer organizer = new Organizer(this, event);
        this.organizerRoles.add(event.getId());
        // set the organizer in the Event object
        event.setOrganizer(this.getId());
        return event;
    }
}
