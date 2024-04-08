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
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
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
import androidx.test.rule.GrantPermissionRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
//    private final String[] permissions = new String[] {android.Manifest.permission.CAMERA,android.Manifest.permission.INTERNET,android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<MainActivity>(MainActivity.class);

//    @Rule
//    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(permissions);

    private String userName = "Test User";
    private String email = "test@ualberta.ca";
    private String phone = "54321";
    private String university = "University of Alberta";

    private void SkipLoginPage() {
        SystemClock.sleep(1500);
        try {
            onView(withHint("username")).perform(ViewActions.typeText(userName)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("email")).perform(ViewActions.typeText(email)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("phone")).perform(ViewActions.typeText(phone)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("organization / university")).perform(ViewActions.typeText(university)).perform(ViewActions.closeSoftKeyboard());
            onView(withText("confirm")).perform(click());
        }
        catch (Exception ignored) {}
        finally {
            onView(withText("Wellcome to QRCheckIn")).check(matches(isDisplayed()));
            onView(withText("User")).perform(click());
            this.checkIfOrganizerPage();
        }
    }

    private void SkipLoginPageToAdmin() {
        SystemClock.sleep(1500);
        try {
            onView(withHint("username")).perform(ViewActions.typeText(userName)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("email")).perform(ViewActions.typeText(email)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("phone")).perform(ViewActions.typeText(phone)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("organization / university")).perform(ViewActions.typeText(university)).perform(ViewActions.closeSoftKeyboard());
            onView(withText("confirm")).perform(click());
        }
        catch (Exception ignored) {}
        finally {
            onView(withText("Wellcome to QRCheckIn")).check(matches(isDisplayed()));
            onView(withText("Administrator")).perform(click());
            this.checkIfAdminPage();
        }
    }

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

    private void checkIfAdminPage() {
        // check if im in the organizer page
        onView(withText("Enter encryption key：")).check(matches(isDisplayed()));
    }

    // make sure the testing device does not have anything stored in Firebase yet to run the following tests
    @Test
    public void testLoginFirstUserShowUp() {
        SystemClock.sleep(1000);
        try {
            onView(withHint("username")).check(matches(isDisplayed()));
            onView(withHint("email")).check(matches(isDisplayed()));
            onView(withHint("phone")).check(matches(isDisplayed()));
            onView(withHint("organization / university")).perform(ViewActions.typeText(university)).perform(ViewActions.closeSoftKeyboard());
        }
        catch (Exception ignored) {}

    }

    @Test
    public void testLoginFirstUserSetupProfile() {
        SystemClock.sleep(1000);
        try {
            onView(withHint("username")).perform(ViewActions.typeText(userName)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("email")).perform(ViewActions.typeText(email)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("phone")).perform(ViewActions.typeText(phone)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("organization / university")).perform(ViewActions.typeText(university)).perform(ViewActions.closeSoftKeyboard());
            onView(withText("confirm")).perform(click());
        }
        catch (Exception ignored) {}
        finally {
            onView(withText("Wellcome to QRCheckIn")).check(matches(isDisplayed()));
        }

    }

    @Test
    public void testFirstPage() {
        // check if im in the organizer page
        SystemClock.sleep(2500);
        try {
            onView(withHint("username")).perform(ViewActions.typeText(userName)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("email")).perform(ViewActions.typeText(email)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("phone")).perform(ViewActions.typeText(phone)).perform(ViewActions.closeSoftKeyboard());
            onView(withHint("organization / university")).perform(ViewActions.typeText(university)).perform(ViewActions.closeSoftKeyboard());
            onView(withText("confirm")).perform(click());
        }
        catch (Exception ignored) {}
        finally {
            onView(withText("Wellcome to QRCheckIn")).check(matches(isDisplayed()));
            onView(withText("User")).perform(click());
            this.checkIfOrganizerPage();
        }

    }

    @Test
    public void testClickCreateEvent() {
        SkipLoginPage();
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
        // Delete all the texts that we just inputted
        //onView(withId(R.id.nameEditText)).perform(ViewActions.scrollTo());
        onView(withId(R.id.nameEditText)).perform(ViewActions.clearText());
        //onView(withId(R.id.infoEditText)).perform(ViewActions.scrollTo());
        onView(withId(R.id.infoEditText)).perform(ViewActions.clearText());
        // Press Back button on top left
        onView(withId(R.id.returnButton)).perform(ViewActions.click());
        // check if im in the organizer page
        this.checkIfOrganizerPage();
    }

    @Test
    public void testClickIntoEvent() {
        // wait for 1500 ms to let APP loads the event from firebase database
//        SystemClock.sleep(1500);
//        // Click into that event "Event001"
//        onView(withText("Data")).perform(click());
//        // Check if we are in the Event001 menu
//        this.checkIfEvent001Menu();
//        // Click view Attendee list
//        onView(withText("View Attendees")).perform(click());
//        onView(withText("Invite Attendees")).check(matches(isDisplayed()));
//        onView(withText("Austin")).check(matches(isDisplayed()));
//        // Press the back button of the phone
//        pressBack();
//        // Check if we are in the Event001 menu
//        this.checkIfEvent001Menu();
//        // Click Upload Poster Button/Option
//        onView(withText("Upload Poster")).perform(click());
//        // Check if it's in the "upload poster" page
//        onView(withText("Choose Image")).check(matches(isDisplayed()));
//        onView(withText("Upload Poster From Local")).check(matches(isDisplayed()));
//        // hit back
//        pressBack();
//        // Check if we are in the Event001 menu
//        this.checkIfEvent001Menu();
//        // Press the back button of the phone
//        pressBack();
//        // Wait for 1500 ms
//        SystemClock.sleep(1500);
//        // Check if we are in the Organizer menu
//        this.checkIfOrganizerPage();
    }

    @Test
    public void testClickIntoAttendeePage() {
        // wait for 1500 ms to let APP loads the event from firebase database
        SkipLoginPage();
        // Click into the page "ATTENDEE"
        onView(withId(R.id.attendee)).perform(doubleClick());
        // Check if it's in the ATTENDEE page
        onView(withText("Scan QR Code for Information")).check(matches(isDisplayed()));

    }

    @Test
    public void testClickIntoCommunityPage() {
        // wait for 1500 ms to let APP loads the event from firebase database
        SkipLoginPage();
        // Click into the page "COMMUNITY"
        onView(withId(R.id.community)).perform(doubleClick());
        // Check if it's in the COMMUNITY page
        onView(withText("Events I created")).check(doesNotExist());

    }

    @Test
    public void testClickIntoProfilePage() {
        // wait for 1500 ms to let APP loads the event from firebase database
        SkipLoginPage();
        // Click into the page "COMMUNITY"
        onView(withId(R.id.profile)).perform(doubleClick());
        // Check if it's in the COMMUNITY page
        onView(withId(R.id.userNameText)).check(matches(isDisplayed()));
        // onView(withId(R.id.userPronounText)).check(matches(isDisplayed()));
        onView(withId(R.id.userPhoneNumbText)).check(matches(isDisplayed()));
        onView(withId(R.id.userEmailText)).check(matches(isDisplayed()));
        onView(withId(R.id.userInfoText)).check(matches(isDisplayed()));
        // check if contents are displaying correctly
        onView(withId(R.id.userPhoneNumbText)).check(matches(withText(this.phone)));
        onView(withId(R.id.userEmailText)).check(matches(withText(this.email)));

    }

    @Test
    public void testAdminLoginPage() {
        SkipLoginPageToAdmin();
    }

    @Test
    public void testAdminLoginPageLogin() {
        SkipLoginPageToAdmin();
        onView(withId(R.id.editTextText)).perform(ViewActions.typeText("12345")).perform(ViewActions.closeSoftKeyboard());
        onView(withText("Log in")).perform(click());
        onView(withText("Events")).check(matches(isDisplayed()));
    }

    @Test
    public void testAdminLoginPageLoginToProfile() {
        SkipLoginPageToAdmin();
        onView(withId(R.id.editTextText)).perform(ViewActions.typeText("12345")).perform(ViewActions.closeSoftKeyboard());
        onView(withText("Log in")).perform(click());
        onView(withId(R.id.profiles)).perform(click());
        onView(withText("Profiles")).check(matches(isDisplayed()));
    }

}
