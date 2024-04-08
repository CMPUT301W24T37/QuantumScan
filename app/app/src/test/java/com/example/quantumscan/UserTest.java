package com.example.quantumscan;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

public class UserTest {
    private User newLocalUser() {
        String name = "Austin Meng Test";
        String phone = "4293108009";
        String university = "University of Alberta";
        String profilePicture = "DEFAULT_PFP";
        String email = "atmeng@ualberta.ca";
        User user = new User(name, phone, university, profilePicture, email);
        user.setId("testUserId123456789");
        return user;
    }

    private User newLocalUserForOrganizer() {
        String name = "David Test";
        String phone = "900801049213";
        String university = "University of Alberta";
        String profilePicture = "DEFAULT_PFP";
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
        assertEquals("DEFAULT_PFP", user.getProfilePicture());
        assertEquals("atmeng@ualberta.ca", user.getEmail());
        assertTrue(user.getAttendeeRoles().isEmpty());
        assertTrue(user.getOrganizerRoles().isEmpty());
    }

    private String randomPick(@NonNull User user){
        String Name = user.getName().toString();
        char firstLetter = Name.charAt(0);
        if (!(firstLetter >= 'A' && firstLetter <= 'Z') && !(firstLetter >= 'a' && firstLetter <= 'z')) {
            firstLetter = '?';
        }
        Random rand = new Random();
        int rand_int1 = rand.nextInt(4)+1;
        String pictureName = "" + firstLetter + rand_int1;
        pictureName = pictureName.toUpperCase()+".png";
        return pictureName;
    }

    @Test
    public void testUserDefaultPfp() {
        User user = newLocalUser();
        String userName = randomPick(user);
        assertEquals('A', userName.charAt(0));
        assertTrue(userName.matches("A[1-4]\\.png"));
    }
}

