package com.gmail.jiangyang5157.sudoku.ui.puzzle;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.gmail.jiangyang5157.sudoku.component.BaseActivity;
import com.gmail.jiangyang5157.sudoku.component.timer.TimerView;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.puzzle.*;
import com.gmail.jiangyang5157.sudoku.sql.AppDatabaseApi;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleTable;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;
import com.gmail.jiangyang5157.tookit.android.sql.BaseTable;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

public class PuzzleFragment extends BasePuzzleFragment implements KeypadFragment.Listener, PuzzleGeneratorTask.Listener {
    private final static String TAG = "[PuzzleFragment]";

    public static final String FRAGMENT_TAG = "PuzzleFragment";

    private TimerView mTimer = null;

    private PuzzleGeneratorTask task = null;

    private long rowId = BaseTable.INVALID_ROWID;

    private int mBestTime = 0;

    private NodeCache[][] mNodesCache = null;

    private Level mLevel = null;

    protected boolean isCompleted = false;

    @Override
    public void onPuzzleViewCreated(Bundle savedInstanceState) {
        rowId = savedInstanceState.getLong(BasePuzzleFragment.KEY_ROWID, BaseTable.INVALID_ROWID);
        if (rowId > 0) {
            mNodesCache = (NodeCache[][]) savedInstanceState.get(BasePuzzleFragment.KEY_NODES_CACHE);
            if (mNodesCache != null) {
                // restore puzzle
                restorePuzzle(savedInstanceState);
            } else {
                // puzzle from database
                loadPuzzle(rowId);
            }
        } else {
            // generate new puzzle
            newPuzzle((Level) savedInstanceState.get(BasePuzzleFragment.KEY_LEVEL_CACHE));
        }
    }

    public void loadPuzzle(long rowId) {
        if (mTimer != null) {
            mTimer.pause();
        }
        isCompleted = false;

        Cursor c = AppDatabaseApi.getInstance(getActivity()).queryPuzzle(PuzzleTable.KEY_ROWID, String.valueOf(rowId), AppDatabaseApi.buildOrderByDesc(PuzzleTable.KEY_ROWID));
        String cache = c.getString(c.getColumnIndexOrThrow(PuzzleTable.KEY_CACHE));
        String timer = c.getString(c.getColumnIndexOrThrow(PuzzleTable.KEY_TIMER));
        String best_time = c.getString(c.getColumnIndexOrThrow(PuzzleTable.KEY_BEST_TIME));

        Gson gson = new Gson();
        PuzzleCache puzzleCache = gson.fromJson(cache, PuzzleCache.class);

        mNodesCache = puzzleCache.getNodesCache();
        if (mTimer != null) {
            mTimer.setTime(Integer.parseInt(timer), true);
        }
        mBestTime = Integer.parseInt(best_time);

        mLevel = puzzleCache.getLevel();
        getActivity().setTitle(mLevel.toString());

        puzzleView.generatePuzzle(mNodesCache);

        if (puzzleView.isCompleted(false)) {
            onCompleted();
        } else {
            if (mTimer != null) {
                mTimer.start();
            }
        }
    }

    public void restorePuzzle(Bundle bundle) {
        if (mTimer != null) {
            mTimer.pause();
        }
        isCompleted = false;

        if (mTimer != null) {
            mTimer.setTime(bundle.getInt(BasePuzzleFragment.KEY_TIMER, mTimer.getTime()), true);
        }
        mBestTime = bundle.getInt(BasePuzzleFragment.KEY_BEST_TIME, mBestTime);

        mLevel = (Level) bundle.get(BasePuzzleFragment.KEY_LEVEL_CACHE);
        getActivity().setTitle(mLevel.toString());

        puzzleView.generatePuzzle(mNodesCache);

        if (puzzleView.isCompleted(false)) {
            onCompleted();
        } else {
            if (mTimer != null) {
                mTimer.start();
            }
        }
    }

    public void newPuzzle(Level level) {
        if (mTimer != null) {
            mTimer.pause();
        }

        if (level == null) {
            throw new IllegalArgumentException("To generate new puzzle, [Level] must not be null");
        }

        mLevel = level;
        getActivity().setTitle(mLevel.toString());

        if (task == null || task.getStatus() == AsyncTask.Status.FINISHED) {
            task = new PuzzleGeneratorTask(this);
        }

        if (task.getStatus() == AsyncTask.Status.PENDING) {
            task.execute(mLevel);
        }
    }

    @Override
    public void onPreExecute() {
        ((BaseActivity) getActivity()).showProcessingDialog();
    }

    @Override
    public void onPostExecute(ArrayList<int[][]> result) {
        // after config changes, fragment may not have been attached at this moment
        if (getActivity() == null) {
            return;
        }
        ((BaseActivity) getActivity()).hideProcessingDialog();

        if (mTimer != null) {
            mTimer.setTime(0, true);
        }
        mBestTime = 0;
        isCompleted = false;

        puzzleView.generatePuzzle(result.get(0), result.get(1));
        mNodesCache = puzzleView.getNodesCache();

        // everything will be updated [onPause()]
        String cache = puzzleView.getPuzzleCache(mNodesCache, mLevel);
        String drawable = puzzleView.getPuzzleDrawable();
        String longDate = String.valueOf(new Date().getTime());
        String timer = null;
        if (mTimer != null) {
            timer = String.valueOf(mTimer.getTime());
        } else {
            timer = String.valueOf(0);
        }
        String best_time = String.valueOf(mBestTime);
        rowId = AppDatabaseApi.getInstance(getActivity()).insertPuzzle(cache, drawable, longDate, timer, best_time);
//        Log.d(TAG, "insertPuzzle - rowId = " + rowId);

        if (puzzleView.isCompleted(false)) {
            onCompleted();
        } else {
            if (mTimer != null) {
                mTimer.start();
            }
        }
    }

    public void restart() {
        if (mTimer != null) {
            mTimer.pause();
        }
        puzzleView.clearPuzzle();
        isCompleted = false;

        if (mTimer != null) {
            mTimer.setTime(0, true);
        }
        if (puzzleView.isCompleted(false)) {
            onCompleted();
        } else {
            if (mTimer != null) {
                mTimer.start();
            }
        }
    }

    @Override
    public void onCompleted() {
        isCompleted = true;
        if (mTimer != null) {
            mTimer.pause();
        }

        puzzleView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.self_rotate));
        AppUtils.buildToast(getActivity(), R.string.msg_completed);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        fetch();
        //save transient data for configuration change
        outState.putLong(BasePuzzleFragment.KEY_ROWID, rowId);
        outState.putSerializable(BasePuzzleFragment.KEY_NODES_CACHE, mNodesCache);
        outState.putSerializable(BasePuzzleFragment.KEY_LEVEL_CACHE, mLevel);
        if (mTimer != null) {
            outState.putInt(BasePuzzleFragment.KEY_TIMER, mTimer.getTime());
        }
        outState.putInt(BasePuzzleFragment.KEY_BEST_TIME, mBestTime);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        fetch();
        sync();
    }

    public boolean fetch() {
        if (rowId <= 0) {
            // invalid puzzle
            return false;
        }

        mNodesCache = puzzleView.getNodesCache();
        return true;
    }

    public boolean sync() {
        if (rowId <= 0) {
            // invalid data
            return false;
        }

        if (mNodesCache == null) {
            //invalid data
            return false;
        }

        int best_time = mBestTime;
        int timer = 0;
        if (mTimer != null) {
            timer = mTimer.getTime();
            if (isCompleted) {
                if (best_time > 0) {
                    mBestTime = timer < best_time ? timer : best_time;
                } else {
                    mBestTime = timer;
                }
            }
        }

        String rowId = String.valueOf(this.rowId);
        String cache = puzzleView.getPuzzleCache(mNodesCache, mLevel);
        String drawable = puzzleView.getPuzzleDrawable();
        String longDate = String.valueOf(new Date().getTime());
        String stringTimer = String.valueOf(timer);
        String stringBestTime = String.valueOf(mBestTime);
        int result = AppDatabaseApi.getInstance(getActivity()).updatePuzzle(
                rowId, cache, drawable, longDate, stringTimer, stringBestTime);
//        Log.d(TAG, "updatePuzzle - result = " + result);
        return true;
    }

    public void setTimer(TimerView mTimer) {
        this.mTimer = mTimer;
    }

    public TimerView getTimer() {
        return mTimer;
    }

    @Override
    public boolean inputNumber(int number) {
        boolean ret = false;
        if (!isCompleted) {
            ret = puzzleView.inputNumber(number);
        }
        return ret;
    }

    @Override
    public boolean onErase() {
        boolean ret = false;
        if (!isCompleted) {
            ret = puzzleView.onErase();
        }
        return ret;
    }

    public Level getLevel() {
        return mLevel;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.pause();
        }
    }
}
