package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.gmail.jiangyang5157.sudoku.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

    @Rule
    public ActivityTestRule<SettingsActivity> mActivityRule = new ActivityTestRule<>(SettingsActivity.class);

    @Test
    public void ui_Check() {
        onView(withText(R.string.settings)).check(matches(isDisplayed()));
        onView(withText(R.string.color_palette)).check(matches(isDisplayed()));
        onView(withText(R.string.about)).check(matches(isDisplayed()));
    }
}
