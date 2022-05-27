package com.example.taskmaster1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;




import static androidx.test.espresso.Espresso.onData;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;

import androidx.test.ext.junit.rules.ActivityScenarioRule;



import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    private RecyclerView recyclerView;
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.taskmaster1", appContext.getPackageName());
    }

    //assert that important UI elements are displayed on the page
    //if click on all Tasks button will navigate to all tasks page
    // and ensure there is a button and image existing in the page
    @Test
    public void test3() {

        Espresso.onView(withId(R.id.button7)).perform(click());
        onView(withId(R.id.imageView)).check(matches(isDisplayed()));
        onView(withId(R.id.button2)).check(matches(isDisplayed()));
    }

    //edit the user’s username, and assert that it says the correct thing on the homepage
    // test navigate to settings page and change the user name
    @Test
    public void testUserNameChange() {
        String name ="Manar";
        Espresso.onView(withId(R.id.button8)).perform(click());
        onView(withId(R.id.username)).perform(clearText());
        onView(withId(R.id.username)).perform(typeText("Manar"),
                closeSoftKeyboard());
        onView(withId(R.id.btnsubmit)).perform(click());
        onView(withId(R.id.username)).check(matches(withText("Manar")));

    }

    // test add new task // press add task then write title, body and choose state
    @Test
    public void addTaskTest() {
        onView(withId(R.id.button6)).perform(click());
        onView(withId(R.id.newTaskTitle)).perform(clearText());
        onView(withId(R.id.doSomthing)).perform(clearText());
        onView(withId(R.id.newTaskTitle)).perform(typeText("new Task"));
        onView(withId(R.id.doSomthing)).perform(typeText("clean your room"), closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(withId(R.id.button)).perform(click());
    }
    @Test
    //tap on a task, and assert that the resulting activity displays the name of that task
    // click on a task in recycler view and navigate to details page to show the task details
    public void testClickOnTaskToShowDetails2(){
        activityScenarioRule.getScenario().onActivity(activity -> {
            recyclerView = activity.findViewById(R.id.recycler_view);
        });
        int item = recyclerView.getAdapter().getItemCount();
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(item-1));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(item-1 , click()));
        onView(withId(R.id.title)).check(matches(withText("new Task")));
        onView(withId(R.id.textLorem)).check(matches(withText("clean your room")));
        onView(withId(R.id.state)).check(matches(withText("in progress")));
    }


    // test the details page contents after adding new test
    @Test
    public void testClickOnTaskToShowDetails(){
        onView(withId(R.id.button6)).perform(click());
        onView(withId(R.id.newTaskTitle)).perform(clearText());
        onView(withId(R.id.doSomthing)).perform(clearText());
        onView(withId(R.id.newTaskTitle)).perform(typeText("make coffee") ,closeSoftKeyboard());
        onView(withId(R.id.doSomthing)).perform(typeText("Do not add sugar") ,closeSoftKeyboard());
        onView(withId(R.id.spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        onView(withId(R.id.button)).perform(click());
        activityScenarioRule.getScenario().onActivity(activity -> {
            recyclerView = activity.findViewById(R.id.recycler_view);
        });
        int item = recyclerView.getAdapter().getItemCount();
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.scrollToPosition(item-1));
        onView(withId(R.id.recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition(item-1 , click()));
        onView(withId(R.id.title)).check(matches(withText("make coffee")));
        onView(withId(R.id.textLorem)).check(matches(withText("Do not add sugar")));
        onView(withId(R.id.state)).check(matches(withText("new")));
    }

}