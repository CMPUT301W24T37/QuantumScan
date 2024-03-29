package com.example.quantumscan;

/**
 * Representing one Attendee object, which contains two primary keys: user id and event id
 * @author Austin, Wei, Kaining
 * @see User
 * @see Event
 * @version 0.0.00001
 */
public class Attendee {
    /**
     * One {@link User} object, indicating which {@link User} is the {@link Attendee}
     */
    private final User user;
    /**
     * One {@link Event} object, indicating which {@link Event} the {@link Attendee} has signed up
     */
    private final Event event;
    /**
     * {@link String} - the user id of the Attendee
     */
    private final String userID;
    /**
     * {@link String} - the user name of the Attendee
     */
    private final String userName;
    /**
     * {@link Boolean} - indicate whether the {@link Attendee} has checked in the {@link Event}<br>
     * True - The {@link Attendee} has checked in the event<br>
     * False - The {@link Attendee} has not checked in the event
     */
    private Boolean checkIn = false;


    /**
     * int - account how many times the {@link Attendee} has checked in
     */
    private int checkInCount;

    // Constructors

    /**
     * Attendee Constructor
     * @param user {@link User} - indicating which {@link User} is the {@link Attendee}
     * @param event {@link Event} - indicating which {@link Event} the {@link Attendee} has signed up
     */
    public Attendee(User user, Event event) {
        this.user = user;
        this.event = event;
        this.userID = user.getId();
        this.userName = user.getName();
    }


    // Setters and Getters

    /**
     * This returns how many times the {@link Attendee} has checked in the {@link Event}
     * @return int - the times an has {@link Attendee} checked in the {@link Event}
     */
    public int getCheckInCount() {
        return checkInCount;
    }

    /**
     * This takes an int and set it as the times that an {@link Attendee} has checked in the {@link Event}
     * @param checkInCount int - how many times the {@link Attendee} has checked in the {@link Event}
     */
    public void setCheckInCount(int checkInCount) {
        this.checkInCount = checkInCount;
    }

    /**
     * This returns whether an {@link Attendee} has checked in the {@link Event}
     * @return {@link String} - indicate whether the {@link Attendee} has checked in<br>
     * True - The {@link Attendee} has checked in the {@link Event}<br>
     * False - The {@link Attendee} has not checked in the {@link Event}
     */
    public Boolean getCheckIn() {
        return checkIn;
    }

    /**
     * This set the status of the checkIn to False or True
     * @param checkIn {@link Boolean} - Whether the {@link Attendee} has checked in the {@link Event}
     */
    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * This returns the {@link User User Object} of the {@link Attendee}
     * @return {@link User}
     */
    public User getUser() {
        return user;
    }

    /**
     * This returns the {@link Event Event Object} which the {@link Attendee} has signed up
     * @return {@link Event}
     */
    public Event getEvent() { return event; }

    /**
     * This returns the {@link User User's} id({@link String}) of the {@link Attendee}
     * @return {@link String} - user id
     */
    public String getUserID() {
        return userID;
    }

    /**
     * This returns the {@link User User's} name({@link String}) of the {@link Attendee}
     * @return {@link String} - user name
     */
    public String getUserName() {
        return userName;
    }
}
