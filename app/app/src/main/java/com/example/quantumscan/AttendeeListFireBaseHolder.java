package com.example.quantumscan;

/**
 * Represents an attendee of an event, tracking their check-in status,
 * name, location (when checked in), and the total number of check-ins.
 */
public class AttendeeListFireBaseHolder {
    /**
     * Unique identifier for the attendee.
     */
    private String id;
    /**
     * Check-in status of the attendee.
     */
    private boolean checkedIn;
    /**
     * Name of the attendee.
     */
    private String name;
    /**
     * Total number of times the attendee has checked into events.
     */
    private int checkInCount;


    /**
     * Constructs an Attendee with specified details.
     *
     * @param id The unique identifier for the attendee.
     * @param checkedIn The check-in status of the attendee.
     * @param name The name of the attendee.
     * @param checkInCount The total number of times the attendee has checked in.
     */
    public AttendeeListFireBaseHolder(String id, boolean checkedIn, String name, int checkInCount) {
        this.id = id;
        this.checkedIn = checkedIn;
        this.name = name;
        this.checkInCount = checkInCount;
    }
    /**
     * Returns the name of the attendee.
     *
     * @return The current name of the attendee.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the attendee.
     *
     * @param name The new name of the attendee.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the total number of check-ins for the attendee.
     *
     * @return The total check-in count.
     */
    public int getCheckInCount() {
        return checkInCount;
    }

    /**
     * Sets the total number of check-ins for the attendee.
     *
     * @param checkInCount The new total check-in count.
     */
    public void setCheckInCount(int checkInCount) {
        this.checkInCount = checkInCount;
    }

    /**
     * Returns the unique identifier of the attendee.
     *
     * @return The unique ID of the attendee.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the attendee.
     *
     * @param id The new unique identifier for the attendee.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the check-in status of the attendee.
     *
     * @return true if the attendee is checked in, false otherwise.
     */
    public boolean isCheckedIn() {
        return checkedIn;
    }

    /**
     * Sets the check-in status of the attendee.
     *
     * @param checkedIn The new check-in status.
     */
    public void setCheckedIn(boolean checkedIn) {
        this.checkedIn = checkedIn;
    }


}
