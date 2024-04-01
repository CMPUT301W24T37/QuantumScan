package com.example.quantumscan;

import java.util.ArrayList;

/**
 * Represent the Organizer, which contains two primary keys: user id and event id
 * @author Austin
 * @see User
 * @see Event
 * @version 0.0.00001
 */
public class Organizer {
    /**
     * One {@link User} object, indicating which {@link User} is the Organizer
     */
    private final User user;
    /**
     * One {@link Event} object, indicating which {@link Event} the Organizer is hosting
     */
    private final Event event;

    // Constructors

    /**
     * Organizer Constructor
     * @param user {@link User} - indicating which {@link User} is the Organizer
     * @param event {@link Event} - indicating which {@link Event} the Organizer is hosting
     */
    public Organizer(User user, Event event) {
        this.user = user;
        this.event = event;
    }

    // Setters and Getters

    /**
     * This return the {@link User} who is organizing the event
     * @return {@link User} - user object who is organizing the event
     */
    public User getUser() {
        return user;
    }

    // You should never call this method
    /*
    public void setUser(User user) {
        this.user = user;
    }
     */

    /**
     * This return the {@link Event} which {@link Event} the Organizer is hosting
     * @return {@link Event} - event object which {@link Event} the Organizer is hosting
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

    // methods
    // US 01.01.01: done in User class (As an organizer, I want to create a new event and generate a unique QR code for attendee check-ins.)
    // US 01.01.02: Reuse an existing QR code for attendee check-ins  (are we implementing this rn??)
    /**
     * This takes an existing QR code and set it as the Event's QR code for signing up/description page
     * @param qrCodeUrl {@link String}<br>The new QR code
     */
    public void setExistingQRCode(String qrCodeUrl) {
        this.event.setExistingQRCode(qrCodeUrl);  // not sure how this works, this will be changed in the future
        // Firebase storing will be done in Event. No need to implement there (I guess?)
    }

    // US 01.01.03: As an organizer, I want to be able to edit the description of the event.
    /**
     * This takes a {@link String} of the description, and sets the event's description
     * @param newDescription String    the description of the event
     */
    public void editDescription(String newDescription) {
        // save the info/descr in the local
        this.event.setDescription(newDescription);
        // Firebase storing will be done in Event. No need to implement there (I guess?)
    }

    // US 01.02.01: As an organizer, I want to view the list of attendees who have checked in to my event.
    // Todo: this won't be implemented until this issue is addressed: https://github.com/CMPUT301W24T37/QuantumScan/issues/49
    // Update Mar 5th: leave it for part4 for now (wont implement this in part3)

    // US 01.04.01: As an organizer, I want to upload an event poster to provide visual information to attendees.
    /**
     * This takes the URL of the poster and set it as the Event poster
     * @param posterURL {@link String}<br>The URL of the poster
     */
    public void uploadPoster(String posterURL) {
        // Update the poster URL / poster picture locally
        this.event.setPosterCode(posterURL);
        // Firebase storing will be done in Event. No need to implement there (I guess?)
    }

    // US 01.07.01: As an organizer, I want to create a new event and generate a unique promotion QR code that links to the event description and event poster in the app.
    // Update Mar 5th: we combine descrCode and eventCode into one same code, called eventCode
//    // Todo: Further implementation of this user story should be in the front-end (Activity/Fragment)
//    public void generateDescrCode() {
//        // Todo: replace the code with CodeManger's methods.
//        // Change and save locally.
//        // Need to call the method of CodeManger to generate the code.
//        String newDescrCode = "Place_holder";  // call CodeManger's method there
//        this.event.setDescrCode(newDescrCode);
//        // Firebase storing will be done in Event. No need to implement there (I guess?)
//    }

    // US 01.10.01: As an organizer, I want to see who is signed up to attend my event.
    // This return the ORIGINAL ArrayList in the Event!!! not the copy
    /**
     * This return the signed up attendee list (not the value, but the direct reference)
     * @return
     *  {@link ArrayList<Attendee>}: ths signed up list of attendees
     */
    public ArrayList<Attendee> getAttendees() {
        return this.event.getAttendees();
    }

}

