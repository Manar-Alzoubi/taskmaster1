package com.example.taskmaster1;

//import static androidx.test.espresso.Espresso.onView;
//import static androidx.test.espresso.matcher.ViewMatchers.withId;
//
//import android.content.Context;
//
//import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import androidx.test.rule.ActivityTestRule;


import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.*;

import android.content.Context;
import android.widget.Toast;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
//    @Rule
//    public ActivityScenarioRule<MainActivity> activitySetting = new ActivityScenarioRule<>(settingsActivity.class);

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.taskmaster1", appContext.getPackageName());
    }
    // test navigate to settings page and change the user name
    @Test
    public void testUserNameChange() {
        Espresso.onView(withId(R.id.button8)).perform(click());
        onView(withId(R.id.username)).perform(clearText());
        onView(withId(R.id.username)).perform(typeText("Manar"),
                closeSoftKeyboard());
        onView(withId(R.id.btnsubmit)).perform(click());
        onView(withId(R.id.username)).check(matches(withText("Manar")));
    }

    // test add new task // press add task then write title, body and choose state
    @Test
    public void addTaskTest(){
        onView(withId(R.id.button6)).perform(click());
        onView(withId(R.id.newTaskTitle)).perform(clearText());
        onView(withId(R.id.doSomthing)).perform(clearText());
        onView(withId(R.id.newTaskTitle)).perform(typeText("new Task "));
        onView(withId(R.id.doSomthing)).perform(typeText("clean your room"),closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.button)).perform(click());
    }



}