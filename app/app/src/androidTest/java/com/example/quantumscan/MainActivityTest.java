package com.example.quantumscan;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
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

    @Test
    public void testFirstPage() {
        // check if im in the organizer page
        onView(withText("NEW EVENT")).check(matches(isDisplayed()));
        onView(withText("Events I created")).check(matches(isDisplayed()));
    }

    @Test
    public void testClickCreateEvent() {
        // check if im in the organizer page
        onView(withText("NEW EVENT")).check(matches(isDisplayed()));
        onView(withText("Events I created")).check(matches(isDisplayed()));
        // Click on Create event button
        onView(withId(R.id.buttonCreate)).perform(click());
        // check if im in the Create event page
        onView(withText("Name of Your Event")).check(matches(isDisplayed()));
        onView(withText("Information About Your Event")).check(matches(isDisplayed()));
        onView(withText("Events I created")).check(doesNotExist());
        //onData(is(instanceOf(String.class))).inAdapterView(withId(R.id.city_list)).atPosition(0).check(matches((withText("Edmonton"))));
        onView(withId(R.id.nameEditText)).perform(ViewActions.typeText("Name of Event"));
    }




}
