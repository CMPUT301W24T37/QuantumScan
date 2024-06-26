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
        String name = "Austin Meng Test";
        String phone = "4293108009";
        String university = "University of Alberta";
        String profilePicture = "A1.png";
        String email = "atmeng@ualberta.ca";
        User user = new User(name, phone, university, profilePicture, email);
        user.setId("testUserId123456789");
        return user;
    }

    private User newLocalUserForOrganizer() {
        String name = "David Test";
        String phone = "900801049213";
        String university = "University of Alberta";
        String profilePicture = "D1.png";
        String email = "david@ualberta.ca";
        User user = new User(name, phone, university, profilePicture, email);
        user.setId("testUserId987654321");
        return user;
    }

    private Event createNewEventFromAnOrganizer() {
        // create a new user locally (locally for below as well)
        User user = newLocalUserForOrganizer();
        // create a new event (add an organizer role)
        // get the organizer role list of user
        //ArrayList<String> organizerRoles = user.getOrganizerRoles();
        // get the new organizer role
        //String organizer = organizerRoles.get(0);
        // return the event that is created by the user
        return user.addOrganizerRole();
    }

    @Test
    public void testCreateNewUser() {
        User user = newLocalUser();
        assertEquals("testUserId123456789", user.getId());
        assertEquals("Austin Meng Test", user.getName());
        assertEquals("4293108009", user.getPhone());
        assertEquals("University of Alberta", user.getUniversity());
        assertEquals("A1.png", user.getProfilePicture());
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
        Attendee attendee = event.getAttendees().get(0);
        // check default attributes' value
        assertFalse(attendee.isCheckedIn());
        // check Attendee getters
        assertEquals(user.getId(), attendee.getId());
        // check default value
        assertNull(attendee.getLocation());
        // check default value
        assertEquals(0, attendee.getCheckInCount());
    }
}
