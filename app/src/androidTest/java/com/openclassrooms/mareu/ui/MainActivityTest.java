package com.openclassrooms.mareu.ui;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.openclassrooms.mareu.DI.DI;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.utils.DeleteViewAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
@LargeTest
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
    @After
    public void finish(){
        mActivity.finish();
        mActivity.myAdapter.mReunions.clear();
        mActivity.myAdapter.reunionsFull.clear();
    }

    @Test
    public void myMeetingsListIsFiltered_byRoom_withSuccess(){
        onView(allOf(withId(R.id.filterButton), isDisplayed()))
                .perform(click());
        onData(anything()).atPosition(0).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(allOf(withId(R.id.My_recyclerView), isDisplayed()))
                .check(withItemCount(3));
        for(int i = 0; i <= 2 ;i++){
            onView(allOf(withId(R.id.My_recyclerView), isDisplayed()))
                    .check(matches(atPosition(i, hasDescendant(withText("Salle A -")))));
        }

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
    public void createMeetingWithSuccess(){
        ViewInteraction floatingActionButton = onView(
                Matchers.allOf(withId(R.id.floatingActionButton),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.FrameLayout")),
                                        0),
                                0),
                        isDisplayed()));
        floatingActionButton.perform(click());

        ViewInteraction textInputEditText = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.text_input_name),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Tigrou"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                Matchers.allOf(withId(R.id.room_spinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction textView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(7);
        textView.perform(click());

        ViewInteraction textInputEditText2 = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.text_input_date),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText2.perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020, 4, 12));
        onView(withId(android.R.id.button1)).perform(click());



        ViewInteraction textInputEditText3 = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.text_input_time),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText3.perform(click());

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(15,30));
        onView(withId(android.R.id.button1)).perform(click());

        ViewInteraction textInputEditText4 = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.text_input_email),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText4.perform(replaceText("tigrou@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                Matchers.allOf(withId(R.id.imageButton_add_email),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction textInputEditText5 = onView(
                Matchers.allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.text_input_email),
                                0),
                        0),
                        isDisplayed()));
        textInputEditText5.perform(pressImeActionButton());

        ViewInteraction appCompatButton3 = onView(
                Matchers.allOf(withId(R.id.button_creation_ok), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction recyclerView = onView(
                Matchers.allOf(withId(R.id.My_recyclerView),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                3)));
        recyclerView.perform(actionOnItemAtPosition(11, click()));

        onView(Matchers.allOf(withId(R.id.textView_detail_nom),isDisplayed()))
                .check(matches(withText("Tigrou")));
        onView(Matchers.allOf(withId(R.id.textView_detail_date), isDisplayed()))
                .check(matches(withText("12 avr. 2020")));
        onView(Matchers.allOf(withId(R.id.textView_detail_time), isDisplayed()))
                .check(matches(withText("15H30")));
        onView(Matchers.allOf(withId(R.id.textView_detail_emails), isDisplayed()))
                .check(matches(withText("tigrou@gmail.com" + " , ")));
    }
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}