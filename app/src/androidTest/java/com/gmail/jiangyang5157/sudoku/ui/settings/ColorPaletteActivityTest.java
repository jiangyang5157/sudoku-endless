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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ColorPaletteActivityTest {

    @Rule
    public ActivityTestRule<ColorPaletteActivity> mActivityRule = new ActivityTestRule<>(ColorPaletteActivity.class);

    @Test
    public void ui_Check() {
        onView(withText(R.string.color_palette)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_reset)).check(matches(isDisplayed()));
        onView(withId(R.id.puzzle_container)).check(matches(isDisplayed()));
        onView(withId(android.R.id.list)).check(matches(isDisplayed()));
    }
}
