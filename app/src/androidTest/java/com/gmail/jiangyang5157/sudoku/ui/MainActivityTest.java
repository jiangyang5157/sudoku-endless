package com.gmail.jiangyang5157.sudoku.ui;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.gmail.jiangyang5157.sudoku.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ui_Check() {
        onView(withText(R.string.app_name)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_puzzle_storage)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_settings)).check(matches(isDisplayed()));
        onView(withId(R.id.ib_level_easy)).check(matches(isDisplayed()));
        onView(withId(R.id.ib_level_normal)).check(matches(isDisplayed()));
        onView(withId(R.id.ib_level_nightmare)).check(matches(isDisplayed()));
        onView(withId(R.id.ib_level_hell)).check(matches(isDisplayed()));
    }
}
