package com.gmail.jiangyang5157.sudoku.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.gmail.jiangyang5157.tookit.android.sql.BaseAppDatabaseApi;
import com.gmail.jiangyang5157.tookit.android.sql.BaseAppDatabaseOpenHelper;

public class AppDatabaseApi extends BaseAppDatabaseApi {

    private volatile static AppDatabaseApi uniqueAppDatabaseApi = null;

    public static AppDatabaseApi getInstance(Context context) {
        if (uniqueAppDatabaseApi == null) {
            synchronized (AppDatabaseApi.class) {
                if (uniqueAppDatabaseApi == null) {
                    uniqueAppDatabaseApi = new AppDatabaseApi(context);
                }
            }
        }
        return uniqueAppDatabaseApi;
    }

    private AppDatabaseApi(Context context) {
        super(context);
    }

    @Override
    protected BaseAppDatabaseOpenHelper getAppDatabaseOpenHelper(Context context) {
        return new AppDatabaseOpenHelper(context);
    }

    public long insertPuzzle(String cache, String drawable, String date, String timer, String best_time) {
//        open();
//        try {
            ContentValues cv = new ContentValues();
            cv.put(PuzzleTable.KEY_CACHE, cache);
            cv.put(PuzzleTable.KEY_DRAWABLE, drawable);
            cv.put(PuzzleTable.KEY_DATE, date);
            cv.put(PuzzleTable.KEY_TIMER, timer);
            cv.put(PuzzleTable.KEY_BEST_TIME, best_time);

            long ret = insert(PuzzleTable.TABLE_NAME, cv);
            return ret;
//        } finally {
//            close();
//        }
    }

    public int updatePuzzle(String rowId, String cache, String drawable, String date, String timer, String best_time) {
//        open();
//        try {
            ContentValues cv = new ContentValues();
            cv.put(PuzzleTable.KEY_CACHE, cache);
            cv.put(PuzzleTable.KEY_DRAWABLE, drawable);
            cv.put(PuzzleTable.KEY_DATE, date);
            cv.put(PuzzleTable.KEY_TIMER, timer);
            cv.put(PuzzleTable.KEY_BEST_TIME, best_time);

            int ret = update(PuzzleTable.TABLE_NAME, rowId, cv);
            return ret;
//        } finally {
//            close();
//        }
    }

    public Cursor queryPuzzles(String orderBy) {
//        open();
//        try {
            String col[] = {PuzzleTable.KEY_ROWID,
                    PuzzleTable.KEY_CACHE,
                    PuzzleTable.KEY_DRAWABLE,
                    PuzzleTable.KEY_DATE,
                    PuzzleTable.KEY_TIMER,
                    PuzzleTable.KEY_BEST_TIME};

            Cursor ret = query(PuzzleTable.TABLE_NAME, col, orderBy);
            return ret;
//        } finally {
//            close();
//        }
    }

    public Cursor queryPuzzle(String key, String value, String orderBy) {
//        open();
//        try {
            String col[] = {PuzzleTable.KEY_ROWID,
                    PuzzleTable.KEY_CACHE,
                    PuzzleTable.KEY_DRAWABLE,
                    PuzzleTable.KEY_DATE,
                    PuzzleTable.KEY_TIMER,
                    PuzzleTable.KEY_BEST_TIME};

            Cursor ret = queryValue(PuzzleTable.TABLE_NAME, col, key, value, orderBy);
            return ret;
//        } finally {
//            close();
//        }
    }

    public Cursor queryPuzzles(String key, String like, String orderBy) {
//        open();
//        try {
            String col[] = {PuzzleTable.KEY_ROWID,
                    PuzzleTable.KEY_CACHE,
                    PuzzleTable.KEY_DRAWABLE,
                    PuzzleTable.KEY_DATE,
                    PuzzleTable.KEY_TIMER,
                    PuzzleTable.KEY_BEST_TIME};

            Cursor ret = queryLike(PuzzleTable.TABLE_NAME, col, key, like, orderBy);
            return ret;
//        } finally {
//            close();
//        }
    }

    public int deletePuzzle(String key, String value) {
//        open();
//        try {
            int ret = delete(PuzzleTable.TABLE_NAME, key, value);
            return ret;
//        } finally {
//            close();
//        }
    }
}
