package com.example.quantumscan;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.Query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import androidx.annotation.NonNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class FireStoreUnitTest {

    @Mock
    FireStoreBridge fireStoreBridge;
    private String MOCK_USER_ID = "1658f5315ca1a74d";
    private String MOCK_USER_NAME = "zhiyang";
    private String MOCK_USER_PHONE = "119";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Mock the retrieveUser method to call the callback with predefined values
        doAnswer(invocation -> {
            FireStoreBridge.OnUserRetrievedListener listener = invocation.getArgument(1);
            User mockUser = new User("1658f5315ca1a74d"); // Assuming User is a class with an ID field
            mockUser.setName(MOCK_USER_NAME);
            mockUser.setPhone(MOCK_USER_PHONE);
            ArrayList<String> mockAttendeeRoles = new ArrayList<>();
            ArrayList<String> mockOrganizerRoles = new ArrayList<>();
            // Populate mockAttendeeRoles and mockOrganizerRoles as needed

            listener.onUserRetrieved(mockUser, mockAttendeeRoles, mockOrganizerRoles);
            return null;
        }).when(fireStoreBridge).retrieveUser(any(String.class), any(FireStoreBridge.OnUserRetrievedListener.class));

        // EVENT TEST
        // Initialize your FireStoreBridge with a mocked FirebaseFirestore instance


    }
    @Test
    public void testRetrieveUser(){
        fireStoreBridge.retrieveUser(MOCK_USER_ID, new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
                // Perform your assertions here
                assertEquals(MOCK_USER_ID, user.getId());
                assertEquals(MOCK_USER_NAME, user.getName());
                assertEquals(MOCK_USER_PHONE, user.getPhone());
            }
        });
    }








}