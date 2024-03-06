package com.example.quantumscan;

import java.util.ArrayList;

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

    // methods
    // US 01.01.01: done in User class (As an organizer, I want to create a new event and generate a unique QR code for attendee check-ins.)
    // US 01.01.02: Reuse an existing QR code for attendee check-ins  (are we implementing this rn??)
    public void setExistingQRCode(String qrCodeUrl) {
        this.event.setExistingQRCode(qrCodeUrl);  // not sure how this works, this will be changed in the future
        // Firebase storing will be done in Event. No need to implement there (I guess?)
    }

    // US 01.01.03: As an organizer, I want to be able to edit the description of the event.
    public void editDescription(String newDescription) {
        // save the info/descr in the local
        this.event.setDescription(newDescription);
        // Firebase storing will be done in Event. No need to implement there (I guess?)
    }

    // US 01.02.01: As an organizer, I want to view the list of attendees who have checked in to my event.
    // Todo: this won't be implemented until this issue is addressed: https://github.com/CMPUT301W24T37/QuantumScan/issues/49
    // Update Mar 5th: leave it for part4 for now (wont implement this in part3)

    // US 01.04.01: As an organizer, I want to upload an event poster to provide visual information to attendees.
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
    public ArrayList<Attendee> getAttendees() {
        return this.event.getAttendees();
    }

}

