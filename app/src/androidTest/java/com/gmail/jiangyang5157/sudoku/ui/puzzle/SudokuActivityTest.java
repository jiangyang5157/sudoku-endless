package com.gmail.jiangyang5157.sudoku.ui.puzzle;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SudokuActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        onView(withId(R.id.ib_level_easy)).perform(click());
    }

    @Test
    public void ui_Check() {
        onView(withId(R.id.menu_new_puzzle)).check(matches(isDisplayed()));
        onView(withId(R.id.puzzle_container)).check(matches(isDisplayed()));

        onView(withId(R.id.btn_numeric_1)).check(matches(withText("1")));
        onView(withId(R.id.btn_numeric_2)).check(matches(withText("2")));
        onView(withId(R.id.btn_numeric_3)).check(matches(withText("3")));
        onView(withId(R.id.btn_numeric_4)).check(matches(withText("4")));
        onView(withId(R.id.btn_numeric_5)).check(matches(withText("5")));
        onView(withId(R.id.btn_numeric_6)).check(matches(withText("6")));
        onView(withId(R.id.btn_numeric_7)).check(matches(withText("7")));
        onView(withId(R.id.btn_numeric_8)).check(matches(withText("8")));
        onView(withId(R.id.btn_numeric_9)).check(matches(withText("9")));
        onView(withId(R.id.btn_erase)).check(matches(withText("-")));

        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        pressBack();
    }
}