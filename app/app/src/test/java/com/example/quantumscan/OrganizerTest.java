package com.example.quantumscan;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;

// This only test Organizer on the local (not firebase) for now
public class OrganizerTest {
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
    public void testUserAddOrganizerRole() {
        // create a new user locally (locally for below as well)
        User user = newLocalUser();
        assertTrue(user.getOrganizerRoles().isEmpty());
        // create a new event (add an organizer role)
        Event newEvent = user.addOrganizerRole();
        assertFalse(user.getOrganizerRoles().isEmpty());
        // get the organizer role list of user
        String eventId = user.getOrganizerRoles().get(0);
        // get the new organizer role
        assertEquals(newEvent.getId(), eventId);
        assertEquals(newEvent.getOrganizer(), user.getId());
    }

}
