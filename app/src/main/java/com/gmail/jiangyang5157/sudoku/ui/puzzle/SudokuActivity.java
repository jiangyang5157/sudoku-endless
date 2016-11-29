package com.gmail.jiangyang5157.sudoku.ui.puzzle;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.*;

import com.gmail.jiangyang5157.sudoku.component.BaseActivity;
import com.gmail.jiangyang5157.sudoku.component.BaseFragment;
import com.gmail.jiangyang5157.sudoku.component.timer.TimerViewImpl;
import com.gmail.jiangyang5157.sudoku.R;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 11:44
 */
public class SudokuActivity extends BaseActivity implements KeypadFragment.Listener {
    private final static String TAG = "[SudokuActivity]";

    public static final int REQUESTCODE = 1000;

    private PuzzleFragment puzzleFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        setupViews(savedInstanceState);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayShowCustomEnabled(true);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(Gravity.START);
        params.setMargins(this.getResources().getDimensionPixelOffset(R.dimen.action_bar_custom_view_margin_left), 0, 0, 0);
        TimerViewImpl timerViewImpl = new TimerViewImpl(this);
        ab.setCustomView(timerViewImpl, params);
        puzzleFragment.setTimer(timerViewImpl);
    }

    private void setupViews(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            puzzleFragment = BaseFragment.newInstance(PuzzleFragment.class, getIntent().getExtras());

            getFragmentManager().beginTransaction()
                    .add(R.id.puzzle_container, puzzleFragment, PuzzleFragment.FRAGMENT_TAG)
                    .commit();

            getFragmentManager().beginTransaction()
                    .add(R.id.keypad_container, BaseFragment.newInstance(KeypadFragment.class), KeypadFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            puzzleFragment = (PuzzleFragment) getFragmentManager().findFragmentByTag(PuzzleFragment.FRAGMENT_TAG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sudoku, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                // this Activity could be launched by different Activity, cannot navigate to a specific parentActivity
                this.finish();
                return true;
            case R.id.menu_validate:
                puzzleFragment.validate(true);
                return true;
            case R.id.menu_restart:
                puzzleFragment.restart();
                return true;
            case R.id.menu_new_puzzle:
                puzzleFragment.fetch();
                puzzleFragment.sync();
                puzzleFragment.newPuzzle(puzzleFragment.getLevel());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        Log.d(TAG, "onRestoreInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
//        Log.d(TAG, "onRetainCustomNonConfigurationInstance");
        return super.onRetainCustomNonConfigurationInstance();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        Log.d(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean inputNumber(int number) {
        return puzzleFragment.inputNumber(number);
    }

    @Override
    public boolean onErase() {
        return puzzleFragment.onErase();
    }
}
