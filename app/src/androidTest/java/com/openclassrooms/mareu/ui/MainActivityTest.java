package com.openclassrooms.mareu.ui;


import androidx.test.espresso.contrib.RecyclerViewActions;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.models.Reunion;
import com.openclassrooms.mareu.service.ReunionApiService;
import com.openclassrooms.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.openclassrooms.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private MainActivity mActivity;
    private static int ITEMS_COUNT = 12;


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);


    @Before
    public void setUp() throws Exception {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    @Test
    public void myMeetingsList_shouldNotBeEmpty(){
        onView(allOf(withId(R.id.My_recyclerView), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem(){
        // Given : We remove the element at position 2
        onView(allOf(withId(R.id.My_recyclerView), isDisplayed())).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(allOf(withId(R.id.My_recyclerView), isDisplayed()))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(allOf(withId(R.id.My_recyclerView), isDisplayed())).check(withItemCount(ITEMS_COUNT-1));
    }

    @Test
    public void onCreateOptionsMenu() {
    }

    @Test
    public void onOptionsItemSelected() {
    }

    @Test
    public void onCreate() {
    }

    @Test
    public void onStart() {
    }

    @Test
    public void onDateSet() {
    }

    @Test
    public void filterList() {
    }

    @Test
    public void showDetail() {
    }

    @Test
    public void showText() {
    }

    @Test
    public void hideText() {
    }
}