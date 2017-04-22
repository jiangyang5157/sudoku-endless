package com.gmail.jiangyang5157.sudoku.ui.storage;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.ui.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

@RunWith(AndroidJUnit4.class)
public class PuzzleStorageActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        onView(withId(R.id.ib_level_easy)).perform(click());
        pressBack();
        onView(withId(R.id.menu_puzzle_storage)).perform(click());
    }

    @Test
    public void ui_Check() {
        onView(withText(R.string.puzzle_storage)).check(matches(isDisplayed()));

        onView(withId(R.id.menu_select_all)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_import_puzzle)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_discard)).check(doesNotExist());
        onView(withId(R.id.menu_share)).check(doesNotExist());

        onView(withId(R.id.menu_select_all)).perform(click());

        onView(withId(R.id.menu_select_all)).check(doesNotExist());
        onView(withId(R.id.menu_import_puzzle)).check(doesNotExist());
        onView(withId(R.id.menu_discard)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_share)).check(matches(isDisplayed()));

        pressBack();

        onView(withId(R.id.menu_select_all)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_import_puzzle)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_discard)).check(doesNotExist());
        onView(withId(R.id.menu_share)).check(doesNotExist());

        onData(anything())
                .inAdapterView(withId(android.R.id.list))
                .atPosition(0)
                .onChildView(withId(R.id.iv_puzzle_drawable))
                .perform(click());

        onView(withId(R.id.menu_select_all)).check(doesNotExist());
        onView(withId(R.id.menu_import_puzzle)).check(doesNotExist());
        onView(withId(R.id.menu_discard)).check(matches(isDisplayed()));
        onView(withId(R.id.menu_share)).check(matches(isDisplayed()));

        pressBack();
    }
}

