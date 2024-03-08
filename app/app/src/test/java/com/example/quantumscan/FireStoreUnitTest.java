package com.example.quantumscan;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

//public class FireStoreUnitTest {
//
//    @Mock
//    FireStoreBridge fireStoreBridge;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//
//        // Mock the retrieveUser method to call the callback with predefined values
//        doAnswer(invocation -> {
//            FireStoreBridge.OnUserRetrievedListener listener = invocation.getArgument(1);
//            User mockUser = new User("1658f5315ca1a74d"); // Assuming User is a class with an ID field
//            ArrayList<String> mockAttendeeRoles = new ArrayList<>();
//            ArrayList<String> mockOrganizerRoles = new ArrayList<>();
//            // Populate mockAttendeeRoles and mockOrganizerRoles as needed
//
//            listener.onUserRetrieved(mockUser, mockAttendeeRoles, mockOrganizerRoles);
//            return null;
//        }).when(fireStoreBridge).retrieveUser(any(String.class), any(FireStoreBridge.OnUserRetrievedListener.class));
//    }
//
//    @Test
//    public void testRetrieveUser(){
//        fireStoreBridge.retrieveUser("1658f5315ca1a74d", new FireStoreBridge.OnUserRetrievedListener() {
//            @Override
//            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
//                System.out.println(user.getId());
//                // Perform your assertions here
//                assertEquals("1658f5315ca1a74d", user.getId());
//            }
//        });
//    }
//}