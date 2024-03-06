package com.example.quantumscan;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testRetrieveUser(){

        FireStoreBridge fb = new FireStoreBridge("USER");
            fb.retrieveUser("1658f5315ca1a74d", new FireStoreBridge.OnUserRetrievedListener() {
            @Override
            public void onUserRetrieved(User user, ArrayList<String> attendeeRoles, ArrayList<String> organizerRoles) {
                System.out.println(user.getName());
            }
        });


        FireStoreBridge fb1 = new FireStoreBridge("EVENT");
        fb1.retrieveAllEvent(new FireStoreBridge.OnEventRetrievedListener() {
            @Override
            public void onEventRetrieved(ArrayList<Event> event, ArrayList<String> organizerIdList) {
                for(int i = 0; i < event.size(); i++){
                    System.out.println("event id " + event.get(i).getId());
                    System.out.println("organizer/user id " + organizerIdList.get(i));
                }

            }

        });
    }

    @Test
    public void testRetrieveAllEvents(){}
}