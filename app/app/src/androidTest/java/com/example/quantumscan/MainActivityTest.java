package com.example.quantumscan;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

    private void checkIfEvent001Menu() {
        // Check if we are in the Event001 menu
        onView(withText("Return")).check(matches(isDisplayed()));
        onView(withText("Edit QR code")).check(matches(isDisplayed()));
        onView(withText("Organizer")).check(matches(isDisplayed()));
    }

    private void checkIfOrganizerPage() {
        // check if im in the organizer page
        onView(withText("NEW EVENT")).check(matches(isDisplayed()));
        onView(withText("Events I created")).check(matches(isDisplayed()));
    }

    @Test
    public void testFirstPage() {
        // check if im in the organizer page
        this.checkIfOrganizerPage();
    }

    @Test
    public void testClickCreateEvent() {
        // check if im in the organizer page
        this.checkIfOrganizerPage();
        // Click on Create event button
        onView(withId(R.id.buttonCreate)).perform(click());
        // check if im in the Create event page
        onView(withText("Name of Your Event")).check(matches(isDisplayed()));
        onView(withText("Information About Your Event")).check(matches(isDisplayed()));
        onView(withText("Events I created")).check(doesNotExist());
        //onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).check(matches((withText("Edmonton"))));
        // Type some text and close soft keyboard
        onView(withId(R.id.nameEditText)).perform(ViewActions.typeText("Name of the Event")).perform(ViewActions.closeSoftKeyboard());
        // Check the EditText is exactly the text we just typed in.
        onView(withId(R.id.nameEditText)).check(matches(withText("Name of the Event")));
        // Scroll down
        //onView(withId(R.id.infoEditText)).perform(ViewActions.scrollTo());
        // Type some text
        onView(withId(R.id.infoEditText)).perform(ViewActions.typeText("Information of the Event")).perform(ViewActions.closeSoftKeyboard());
        // Check the EditText is exactly the text we just typed in.
        onView(withId(R.id.infoEditText)).check(matches(withText("Information of the Event")));
        // Scroll to id edit text
        //onView(withId(R.id.idEditText)).perform(ViewActions.scrollTo());
        // Type some text
        onView(withId(R.id.idEditText)).perform(ViewActions.typeText("user id of the Attendee")).perform(ViewActions.closeSoftKeyboard());
        // Check the EditText is exactly the text we just typed in.
        onView(withId(R.id.idEditText)).check(matches(withText("user id of the Attendee")));
        // Delete all the texts that we just inputted
        //onView(withId(R.id.nameEditText)).perform(ViewActions.scrollTo());
        onView(withId(R.id.nameEditText)).perform(ViewActions.clearText());
        //onView(withId(R.id.infoEditText)).perform(ViewActions.scrollTo());
        onView(withId(R.id.infoEditText)).perform(ViewActions.clearText());
        //onView(withId(R.id.idEditText)).perform(ViewActions.scrollTo());
        onView(withId(R.id.idEditText)).perform(ViewActions.clearText());
        // Press Back button on top left
        onView(withId(R.id.returnButton)).perform(ViewActions.click());
        // check if im in the organizer page
        this.checkIfOrganizerPage();
    }

    @Test
    public void testClickIntoEvent() {
        // wait for 1500 ms to let APP loads the event from firebase database
        SystemClock.sleep(1500);
        // Click into that event "Event001"
        onView(withText("Event001")).perform(click());
        // Check if we are in the Event001 menu
        this.checkIfEvent001Menu();
        // Click view Attendee list
        onView(withText("View Attendees")).perform(click());
        onView(withText("Invite Attendees")).check(matches(isDisplayed()));
        onView(withText("Austin")).check(matches(isDisplayed()));
        // Press the back button of the phone
        pressBack();
        // Check if we are in the Event001 menu
        this.checkIfEvent001Menu();
        // Click Upload Poster Button/Option
        onView(withText("Upload Poster")).perform(click());
        // Check if it's in the "upload poster" page
        onView(withText("Choose Image")).check(matches(isDisplayed()));
        onView(withText("Upload Poster From Local")).check(matches(isDisplayed()));
        // hit back
        pressBack();
        // Check if we are in the Event001 menu
        this.checkIfEvent001Menu();
        // Press the back button of the phone
        pressBack();
        // Wait for 1500 ms
        SystemClock.sleep(1500);
        // Check if we are in the Organizer menu
        this.checkIfOrganizerPage();
    }

    @Test
    public void testClickIntoAttendeePage() {
        // wait for 1500 ms to let APP loads the event from firebase database
        SystemClock.sleep(1500);
        // Click into the page "ATTENDEE"
        onView(withId(R.id.attendee)).perform(doubleClick());
        // Check if it's in the ATTENDEE page
        onView(withText("Events I Attend")).check(matches(isDisplayed()));;
        onView(withText("Scan QR Code for Information")).check(matches(isDisplayed()));

    }

    @Test
    public void testClickIntoCommunityPage() {
        // wait for 1500 ms to let APP loads the event from firebase database
        SystemClock.sleep(1500);
        // Click into the page "COMMUNITY"
        onView(withId(R.id.community)).perform(doubleClick());
        // Check if it's in the COMMUNITY page
        onView(withText("Community Fragment")).check(matches(isDisplayed()));;

    }

    @Test
    public void testClickIntoProfilePage() {
        // wait for 1500 ms to let APP loads the event from firebase database
        SystemClock.sleep(1500);
        // Click into the page "COMMUNITY"
        onView(withId(R.id.profile)).perform(doubleClick());
        // Check if it's in the COMMUNITY page
        onView(withId(R.id.userNameText)).check(matches(isDisplayed()));
        onView(withId(R.id.userPronounText)).check(matches(isDisplayed()));
        onView(withId(R.id.userPhoneNumbText)).check(matches(isDisplayed()));
        onView(withId(R.id.userEmailText)).check(matches(isDisplayed()));
        onView(withId(R.id.userInfoText)).check(matches(isDisplayed()));

    }

}
