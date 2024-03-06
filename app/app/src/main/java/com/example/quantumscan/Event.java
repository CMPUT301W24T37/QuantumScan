package com.example.quantumscan;

import java.util.ArrayList;

/*
  Todo: 0. to decide the structure of the Firebase database like how we store the data/info
        1. to decide whether to use FireBaseConnection to address the Database part OR to address it in other places OR to address in this class
        2. to add or change the methods
        3. to consistently update the code to make it works with other classes
        4. to fix the rest of the code
  The entire Class will probably be changed in the future.
*/

/**
 * This is a class that defines an Event, which can be uniquely identified by its 'id'.
 */
public class Event {
    /**
     * Int: an unique id that will not changed until the event got deleted
     *      default value is 0, indicating the Event has not changed the id.
     *      The place where generate or initialize a new Event should be responsible of setting the new ID!! (call CodeManger).
     */
    private String id;
    /**
     * String: the title of the event
     */
    private String title;
    /**
     * ArrayList<String>: a ArrayList that represents the announcements
     */
    private ArrayList<String> announcements;
    /**
     * String: a piece of string that represents the info/description of the event
     */
    private String description;
    /**
     * ArrayList<Attendee>: an ArrayList that contains the attendees who are signed up in
     */
    private ArrayList<Attendee> attendees;

    // Update Mar 5: we leave it for part4, canceled for part3 for now
//    /**
//     * ArrayList<Attendee>: an ArrayList that contains the attendees who have checked in
//     */
//    private ArrayList<Attendee> checkedInList;
    /**
     * Organizer: the owner of the event
     */
    private Organizer organizer;  // this could also be an ArrayList<Organizer>, but temporarily we assume only one Organizer per Event now
    /**
     * String: an UNIQUE eventCode that can be scanned by the Attendee BUT it can be changed at any time
     */
    private String eventCode;  // will be generated by using the eventID, which is unique
    // I removed the descrCode since it should act the same like eventCode in our app
    /**
     * String: the URL of the poster of the Event
     */
    private String posterCode;  // The URL of the poster
    /**
     * FirebaseFirestore: used to connect to Firebase and save/restore data from it
     */
    // private FirebaseFirestore db;  // it could be changed using Firebase Class instead of a variable? Or do it in-place

    // Constructor
    public Event() {
        this.id = null;  // Call CodeManger's methods there This would typically be generated by a CodeGenerator/CodeManger class
        this.title = null;
        this.announcements = new ArrayList<String>();
        this.description = null;
        this.attendees = new ArrayList<Attendee>();
        //this.checkedInList = new ArrayList<Attendee>();  // do it in part4
        this.organizer = null;  // the caller should be responsible for assigning the organizer since every event should have one organizer.
        // US 01.01.01 As an organizer, I want to create a new event and generate a unique QR code for attendee check-ins.
        this.eventCode = "PLACE_HOLDER"; // Call CodeManger's methods there This would typically be generated by a CodeGenerator/CodeManger class
        this.posterCode = null; // default is null, indicating no poster

        // Initialize Firebase reference
        // Could use FirebaseConnection Class or do it somewhere else instead of in this class. Currently commented out for now.
        // this.db = FirebaseDatabase.getInstance();
    }

    public Event(Organizer organizer) {
        this.id = null;  // Call CodeManger's methods there This would typically be generated by a CodeGenerator/CodeManger class
        this.title = null;
        this.announcements = new ArrayList<String>();
        this.description = null;
        this.attendees = new ArrayList<Attendee>();
        //this.checkedInList = new ArrayList<Attendee>();  // part4
        this.organizer = organizer;
        // US 01.01.01 As an organizer, I want to create a new event and generate a unique QR code for attendee check-ins.
        this.eventCode = "PLACE_HOLDER"; // Call CodeManger's methods there This would typically be generated by a CodeGenerator/CodeManger class
        this.posterCode = null; // default is null, indicating no poster

        // Initialize Firebase reference
        // Could use FirebaseConnection Class or do it somewhere else instead of in this class. Currently commented out for now.
        // this.db = FirebaseDatabase.getInstance();
    }

    public Event(String id, String title, String description) {
        this.id = id;  // This would typically be generated by a CodeGenerator/CodeManger class
        this.title = title;
        this.announcements = new ArrayList<String>();
        this.description = description;
        this.attendees = new ArrayList<Attendee>();
        //this.checkedInList = new ArrayList<Attendee>();  // part4
        this.organizer = null;  // the caller should be responsible for assigning the organizer since every event should have one organizer.
        // US 01.01.01 As an organizer, I want to create a new event and generate a unique QR code for attendee check-ins.
        this.eventCode = "PLACE_HOLDER"; // Call CodeManger's methods there This would typically be generated by a CodeGenerator/CodeManger class
        this.posterCode = null; // default is null, indicating no poster

        // Initialize Firebase reference
        // Could use FirebaseConnection Class or do it somewhere else instead of in this class. Currently commented out for now.
        // this.db = FirebaseDatabase.getInstance();
    }

    // Getters and setters for the announcement

    /**
     * This returns the id of the Event
     * @return
     *  Returns the id of the Event
     */
    public String getId() {
        return id;
    }

    /**
     * This sets the id of the Event
     * @param id
     *  The id of the Event
     */
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * This returns the announcements of the Event
     *
     * @return The announcements of the Event (String)
     */
    public ArrayList<String> getAnnouncement() {
        return announcements;
    }


    /**
     * This sets the announcements of the Event
     * @param announcements
     *  The announcements of the Event (String)
     */
    public void setAnnouncement(ArrayList<String> announcements) {
        this.announcements = announcements;
    }

    // Add one announcement into the announcements list

    /**
     * This add one (String) announcement into the (ArrayList<String>) announcements
     * @param anno String: announcement
     */
    public void addAnnouncement(String anno) {
        this.announcements.add(anno);
    }

    /**
     * This takes the (int) index of the (ArrayList<String>) announcements, and delete the corresponding announcement
     * @param index the index of the (String) announcement in the ArrayList<String> announcements
     */
    public void deleteAnnouncement(int index) {
        this.announcements.remove(index);
    }

    /**
     * This takes the (String) content of the announcement in (ArrayList<String>) announcements, and delete the corresponding announcement with the same content
     * @param announcement the content of the announcement in the announcements
     */
    public void deleteAnnouncement(String announcement) {
        this.announcements.remove(announcement);
    }

    // Getters and setters for event information/descr
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    // Getters for attendees
//    /**
//     * This returns the COPY of the attendee list
//     * @return
//     *  new ArrayList<>(attendees)
//     */
//    public List<Attendee> getAttendees() {
//        return new ArrayList<>(attendees); // Return a copy of the attendees list
//    }

    // US 01.10.01: As an organizer, I want to see who is signed up to attend my event.
    /**
     * This return the signed up attendee list (not the value, but the direct reference)
     * @return
     *  ArrayList<Attendee>: ths signed up list of attendees
     */
    public ArrayList<Attendee> getAttendees() {
        return attendees;
    }


    // US 01.02.01: As an organizer, I want to view the list of attendees who have checked in to my event.
    // update mar 5: leave it for part4, same for below
//    /**
//     * This returns the checked-in attendee list (not the copy but the direct reference)
//     * @return
//     *  new ArrayList<>(attendees)
//     */
//    public ArrayList<Attendee> getCheckedInList() {
//        return checkedInList;
//    }





//    /**
//     * This set the new checkedInList
//     * @param checkedInList
//     *  Arraylist<Attendee>
//     */
//    public void setCheckedInList(ArrayList<Attendee> checkedInList) {
//        this.checkedInList = checkedInList;
//    }

    // Getters and setters for the organizer
    public Organizer getOrganizer() {
        return organizer;
    }

    /**
     * This sets the Organizer of the Event
     * @param organizer
     *  (Organizer object) representing the organizer
     */
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }

    // Getters and setters for the event code
    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getPosterCode() {
        return posterCode;
    }

    public void setPosterCode(String posterCode) {
        this.posterCode = posterCode;
    }

    // methods
    // Method to add an attendee to the event
    /**
     * This adds one single Attendee object into the Attendee list
     * @param attendee
     *  The object of one single Attendee
     */
    public void addAttendee(Attendee attendee) {
        // Locally
        this.attendees.add(attendee);

        // Todo: add and save data in Firebase
        // Firebase
        // Code there
    }

    // remove/delete one Attendee from the checked-in list by passing Attendee object

    /**
     * This removes/deletes one single Attendee object from the signed up attendee list by passing Attendee object
     * @param attendee
     *  Attendee: an object of Attendee
     */
    public void deleteAttendee(Attendee attendee) {
        // Todo: remove/delete data in Firebase
        // Firebase
        // code there

        // Locally
        this.attendees.remove(attendee);
    }

    // Method to add an attendee to the checked-in list of the event

    // update: leave it for part4, same for below
//    /**
//     * This adds one single Attendee object into the checked-in Attendee list
//     * @param attendee
//     *  The object of one single Attendee
//     */
//    public void addCheckedInList(Attendee attendee) {
//        // Locally
//        this.checkedInList.add(attendee);
//
//        // Todo: add and save data in Firebase (if needed)
//        // Firebase
//        // Code there
//    }

    // remove/delete one Attendee from the checked-in list by passing Attendee object
//    /**
//     * This removes/deletes one single Attendee object from the checked-in attendee list by passing Attendee object
//     * @param attendee
//     *  The object of one single Attendee
//     */
//    public void deleteCheckedInAttendee(Attendee attendee) {
//        // Todo: remove/delete data in Firebase (if needed)
//        // Firebase
//        // code there
//
//        // Locally
//        this.checkedInList.remove(attendee);
//    }

    // US 01.01.02: Reuse an existing QR code for attendee check-ins, since it's for checked in, we leave it for part4???
    public void setExistingQRCode(String qrCodeUrl) {
        // Todo: adjust this part. What is the definition of the "existing QR code"?
        this.eventCode = qrCodeUrl;  // not sure how this works, this will be changed in the future

        // Todo: address this part, decide to use FireBaseConnection to store OR in other Classes/places OR to address there(below)
    }

    // US 01.01.03: Edit the description of the event
    public void editDescription(String newDescription) {
        // save the info/descr in the local
        this.setDescription(newDescription);

        // Todo: address the way we save in the database or do it in FireBaseConnection or in other Classes/places (to be discussed)
        // Update the description in Firebase
        // eventRef.child("description").setValue(description);  // not correctness guaranteed
    }

    // US 01.04.01: As an organizer, I want to upload an event poster to provide visual information to attendees.
    public void uploadPoster(String posterURL) {
        // Update the poster URL / poster picture locally
        this.setPosterCode(posterURL);

        // Todo: address the way we save the poster in the database or do it in FireBaseConnection or in other Classes/places (to be discussed)
        // Update the poster in Firebase
        // code there
    }

    // US 01.07.01: As an organizer, I want to create a new event and generate a unique promotion QR code that links to the event description and event poster in the app.
    // This is essential the eventCode, so just reuse the method above (if any?)

    // US 01.10.01: As an organizer, I want to see who is signed up to attend my event.
    // US 01.02.01: As an organizer, I want to view the list of attendees who have checked in to my event.
    // just directly call the getter methods of the attendees and checkedInList above
      public void EventIdGenerator(String UserId){
        LocalDateTime now = null;
        String formattedDateTime = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            now = LocalDateTime.now();
        }

        // Custom format
        DateTimeFormatter formatter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formattedDateTime = now.format(formatter);
        }
        this.id = formattedDateTime + "||" + UserId;
    }
}
