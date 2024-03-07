package com.example.quantumscan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;

public class AttendeeTest {
    private User newLocalUser() {
        String name = "Austin Meng";
        String phone = "4293108009";
        String university = "University of Alberta";
        String profilePicture = null;
        String email = "atmeng@ualberta.ca";
        return new User(name, phone, university, profilePicture, email);
    }

    private User newLocalUserForOrganizer() {
        String name = "Davood";
        String phone = "513";
        String university = "University of Alberta";
        String profilePicture = null;
        String email = "davood@ualberta.ca";
        return new User(name, phone, university, profilePicture, email);
    }

    private Event createNewEventFromAnOrganizer() {
        // create a new user locally (locally for below as well)
        User user = newLocalUserForOrganizer();
        // create a new event (add an organizer role)
        user.addOrganizerRole();
        // get the organizer role list of user
        ArrayList<Organizer> organizerRoles = user.getOrganizerRoles();
        // get the new organizer role
        Organizer organizer = organizerRoles.get(0);
        // return the event that is created by the user
        return organizer.getEvent();
    }

    @Test
    public void testCreateNewUserWithoutUserID() {
        User user = newLocalUser();
        assertEquals("PLACE_HOLDER", user.getId());
        assertEquals("Austin Meng", user.getName());
        assertEquals("4293108009", user.getPhone());
        assertEquals("University of Alberta", user.getUniversity());
        assertNull(user.getProfilePicture());
        assertEquals("atmeng@ualberta.ca", user.getEmail());
        assertTrue(user.getAttendeeRoles().isEmpty());
        assertTrue(user.getOrganizerRoles().isEmpty());
    }

    @Test
    public void testUserAddAttendeeRole() {
        // Create an event from an organizer (not the user below)
        Event event = createNewEventFromAnOrganizer();
        // Create a new user
        User user = newLocalUser();
        // Check user's AttendeeRoleList is empty
        assertTrue(user.getAttendeeRoles().isEmpty());
        // User sign up for the event
        user.addAttendeeRole(event);
        // Check user's AttendeeRoleList is NOT empty
        assertFalse(user.getAttendeeRoles().isEmpty());
        // get the Attendee object
        Attendee attendee = user.getAttendeeRoles().get(0);
        // check default attributes' value
        assertFalse(attendee.getCheckIn());
        // check Attendee getters
        assertEquals(user, attendee.getUser());
        assertEquals(event, attendee.getEvent());
        // get the Event object from attendee
        assertEquals(attendee.getEvent(), event);
        // check the use is in the event's AttendeeList
        assertEquals(attendee, event.getAttendees().get(0));
        assertEquals(user, event.getAttendees().get(0).getUser());
        // check user_id
        assertEquals(user.getId(), attendee.getUserID());
        // check user_name
        assertEquals(user.getName(), attendee.getUserName());
    }
}
