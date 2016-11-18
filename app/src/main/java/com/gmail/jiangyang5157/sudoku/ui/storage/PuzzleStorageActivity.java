package com.gmail.jiangyang5157.sudoku.ui.storage;

import android.os.Bundle;

import com.gmail.jiangyang5157.sudoku.component.BaseActivity;
import com.gmail.jiangyang5157.sudoku.component.BaseListFragment;
import com.gmail.jiangyang5157.sudoku.R;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 11:44
 */
public class PuzzleStorageActivity extends BaseActivity {
    private final static String TAG = "[PuzzleStorageActivity]";

    private PuzzleStorageFragment mPuzzleStorageFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle_storage);

        setupViews(savedInstanceState);
    }

    private void setupViews(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mPuzzleStorageFragment = BaseListFragment.newInstance(PuzzleStorageFragment.class);

            getFragmentManager().beginTransaction()
                    .add(R.id.container, mPuzzleStorageFragment, PuzzleStorageFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            mPuzzleStorageFragment = (PuzzleStorageFragment) getFragmentManager().findFragmentByTag(PuzzleStorageFragment.FRAGMENT_TAG);
        }
    }

    @Override
    public void onBackPressed() {
        if (mPuzzleStorageFragment.onBackPressed()) {
            // handled BACK in Fragment, do nothing in the Activity
        } else {
            // Didn't handle BACK in Fragment
            super.onBackPressed();
        }
    }
}
