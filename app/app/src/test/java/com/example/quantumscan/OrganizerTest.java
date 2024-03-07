package com.example.quantumscan;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;

// This only test Organizer on the local (not firebase) for now
public class OrganizerTest {
    private User newLocalUser() {
        String name = "Austin Meng";
        String phone = "4293108009";
        String university = "University of Alberta";
        String profilePicture = null;
        String email = "atmeng@ualberta.ca";
        return new User(name, phone, university, profilePicture, email);
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
    public void testUserAddOrganizerRole() {
        // create a new user locally (locally for below as well)
        User user = newLocalUser();
        assertTrue(user.getOrganizerRoles().isEmpty());
        // create a new event (add an organizer role)
        user.addOrganizerRole();
        assertFalse(user.getOrganizerRoles().isEmpty());
        // get the organizer role list of user
        ArrayList<Organizer> organizerRoles = user.getOrganizerRoles();
        // get the new organizer role
        Organizer organizer = organizerRoles.get(0);
        assertNotNull(organizer.getEvent());
        assertEquals(user, organizer.getUser());
        // get the event that is created by the user
        Event event = organizer.getEvent();
        assertNotNull(event.getId());
        String eventID = event.getId();
        System.out.print("event id: " + eventID + "\n");
        assertEquals(organizer, event.getOrganizer());
    }

    @Test
    public void testOrganizerRole() {
        // create a new user locally (locally for below as well)
        User user = newLocalUser();
        // create a new event (add an organizer role)
        user.addOrganizerRole();
        // get the organizer role list of user
        ArrayList<Organizer> organizerRoles = user.getOrganizerRoles();
        // get the new organizer role
        Organizer organizer = organizerRoles.get(0);
        // get the event that is created by the user
        Event event = organizer.getEvent();
        assertTrue(organizer.getAttendees().isEmpty());
    }
}
