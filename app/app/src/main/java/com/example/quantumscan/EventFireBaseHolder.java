package com.example.quantumscan;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This is a class that defines an Event, which can be uniquely identified by its 'id'.
 * @author Wei
 * @see Organizer
 * @see Attendee
 * @version 0.0.00001
 *
 */
public class EventFireBaseHolder {
    /**
     * ArrayList<String>: a ArrayList that represents the announcements
     */
    private ArrayList<String> announcements;
    /**
     * String: an UNIQUE eventCode that can be scanned by the Attendee BUT it can be changed at any time
     */
    private String eventCode;
    /**
     * String: an unique id that will not changed until the event got deleted
     *      default value is 0, indicating the Event has not changed the id.
     *      The place where generate or initialize a new Event should be responsible of setting the new ID!! (call CodeManger).
     */
    private String id;
    /**
     * Organizer: the owner of the event
     */
    private String organizer;
    /**
     * String: the URL of the poster of the Event
     */
    private String posterCode;
    /**
     * String: the title of the event
     */
    private String title;
    /**
     * String: a piece of string that represents the info/description of the event
     */
    private String description;
    /**
     * long: the max attendee number limit
     */
    private long attendeeLimit;

    /**
     * long: the current number of attendee
     */
    private long currentTotalAttendee;




    /**
     * Event Constructor
     * Caller should always make sure to call {@link #setOrganizer(String) setOrganizer} to assign an Organier to this event
     */
    public EventFireBaseHolder() {
        this.announcements = new ArrayList<>();
        this.description = "description";
        this.eventCode = "eventCode";
        this.id = "id";
        this.organizer = "organizer";
        this.posterCode = "posterCode";
        this.title = "title";

    }
    /**
     * Event Constructor
     */
    public EventFireBaseHolder(ArrayList<String> announcements, String description, String eventCode,
                               String id, String organizer, String posterCode, String title,
                               long attendeeLimit, long currentTotalAttendee) {
        this.announcements = announcements;
        this.description = description;
        this.eventCode = eventCode;
        this.id = id;
        this.organizer = organizer;
        this.posterCode = posterCode;
        this.title = title;
        this.attendeeLimit = attendeeLimit;
        this.currentTotalAttendee = currentTotalAttendee;

    }


    /**
     * Returns the list of announcements associated with the event.
     *
     * @return A list of announcement strings for the event.
     */
    public ArrayList<String> getAnnouncements() {
        return announcements;
    }

    /**
     * Returns the maximum number of attendees allowed for the event.
     *
     * @return The attendee limit for the event.
     */
    public long getAttendeeLimit() {
        return attendeeLimit;
    }

    /**
     * Sets the maximum number of attendees allowed for the event.
     *
     * @param attendeeLimit The maximum number of attendees that can attend the event.
     */
    public void setAttendeeLimit(long attendeeLimit) {
        this.attendeeLimit = attendeeLimit;
    }

    /**
     * Returns the current total number of attendees for the event.
     *
     * @return The current count of attendees who have joined the event.
     */
    public long getCurrentTotalAttendee() {
        return currentTotalAttendee;
    }

    /**
     * Sets the current total number of attendees for the event.
     *
     * @param currentTotalAttendee The total number of attendees currently registered for the event.
     */
    public void setCurrentTotalAttendee(long currentTotalAttendee) {
        this.currentTotalAttendee = currentTotalAttendee;
    }
    /**
     * Sets the announcements associated with the event.
     *
     * @param announcements A list of announcements for the event.
     */
    public void setAnnouncements(ArrayList<String> announcements) {
        this.announcements = announcements;
    }

    /**
     * Returns the description of the event.
     *
     * @return The event's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the event.
     *
     * @param description The description of the event.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the unique event code associated with the event.
     *
     * @return The event's unique code.
     */
    public String getEventCode() {
        return eventCode;
    }

    /**
     * Sets the unique event code for the event.
     *
     * @param eventCode The unique code to identify the event.
     */
    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    /**
     * Returns the unique identifier of the event.
     *
     * @return The unique ID of the event.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the event.
     *
     * @param id The unique identifier of the event.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the organizer of the event.
     *
     * @return The name or identifier of the event's organizer.
     */
    public String getOrganizer() {
        return organizer;
    }

    /**
     * Sets the organizer of the event.
     *
     * @param organizer The name or identifier of the person or entity organizing the event.
     */
    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    /**
     * Returns the poster code associated with the event.
     *
     * @return The event's poster code.
     */
    public String getPosterCode() {
        return posterCode;
    }

    /**
     * Sets the poster code for the event.
     *
     * @param posterCode The code associated with the event's promotional poster.
     */
    public void setPosterCode(String posterCode) {
        this.posterCode = posterCode;
    }

    /**
     * Returns the title of the event.
     *
     * @return The event's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the event.
     *
     * @param title The title of the event.
     */
    public void setTitle(String title) {
        this.title = title;
    }





}
