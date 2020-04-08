package com.openclassrooms.mareu.ui;


import android.widget.DatePicker;
import android.widget.TimePicker;

import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.utils.DeleteViewAction;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressBack;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
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
        mActivity.myAdapter.mReunions = DI.getNewInstanceApiService().getReunions();
        mActivity.myAdapter.reunionsFull = DI.getNewInstanceApiService().getReunions();


    }

    @Test
    public void myMeetingsListIsFiltered_byRoom_withSuccess(){
        onView(allOf(withId(R.id.filterButton), isDisplayed()))
                .perform(click());
        onData(anything()).atPosition(0).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(allOf(withId(R.id.My_recyclerView), isDisplayed()))
                .check(withItemCount(3));
    }
    @Test
    public void myMeetingsListFilter_isReset_withSuccess(){
        onView(allOf(withId(R.id.filterButton), isDisplayed()))
                .perform(click());
        onData(anything()).atPosition(0).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(allOf(withId(R.id.filterButton), isDisplayed()))
                .perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(allOf(withId(R.id.My_recyclerView), isDisplayed()))
            .check(withItemCount(ITEMS_COUNT));

    }

    @Test
    public void myMeetingsListIsFiltered_byDate_withSuccess(){
        onView(allOf(withId(R.id.filterButton), isDisplayed()))
                .perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 4, 21));
        onView(withId(android.R.id.button1)).perform(click());
        onView(allOf(withId(R.id.My_recyclerView), isDisplayed()))
                .check(withItemCount(7));
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
    public void CreateMeetingWithSuccess(){
        String reunionName = "Reunion Test";
        String reunionDate = "21 avr. 2020";
        String reunionTime = "15H30";
        String reunionMail = "tigrou@chat.com";

        onView(allOf(withId(R.id.floatingActionButton) ,isDisplayed()))
                .perform(click());
        onView(allOf(withId(R.id.editText_name) , isDisplayed()))
                .perform(click())
                .perform(typeText(reunionName));
        onView(allOf(withId(R.id.editText_date) , isDisplayed()))
                .perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 4, 21));
        onView(withId(android.R.id.button1)).perform(click());
        onView(allOf(withId(R.id.editText_time), isDisplayed()))
                .perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(15,30));
        onView(withId(android.R.id.button1)).perform(click());
        onView(allOf(withId(R.id.room_spinner),isDisplayed()))
                .perform(click());
        onData(anything()).atPosition(2).perform(click());
        onView(allOf(withId(R.id.editText_email),isDisplayed()))
                .perform(click())
                .perform(typeText(reunionMail));
        onView(allOf(withId(R.id.imageButton_add_email)))
                .perform(click())
                .perform(pressBack());
        onView(allOf(withId(R.id.button_creation_ok)))
                .perform(click());
        onView(allOf(withId(R.id.My_recyclerView), isDisplayed()))
                .perform(RecyclerViewActions.scrollToPosition(12))
                .perform(RecyclerViewActions.actionOnItemAtPosition(12, click()));
        onView(allOf(withId(R.id.textView_detail_nom),isDisplayed()))
                .check(matches(withText(reunionName)));
        onView(allOf(withId(R.id.textView_detail_date), isDisplayed()))
                .check(matches(withText(reunionDate)));
        onView(allOf(withId(R.id.textView_detail_time), isDisplayed()))
                .check(matches(withText(reunionTime)));
        onView(allOf(withId(R.id.textView_detail_emails), isDisplayed()))
                .check(matches(withText(reunionMail + " , ")));
    }

}