package com.gmail.jiangyang5157.sudoku.sql;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

public class PuzzleCursorLoader extends CursorLoader {

    public static final int QUERY_PUZZLES = 1000;

    private Context context = null;
    private String orderBy = null;

    /**
     * @param context
     * @param orderBy Passing null will use the default sort order, which may be unordered.
     */
    public PuzzleCursorLoader(Context context, String orderBy) {
        super(context);
        this.context = context;
        this.orderBy = orderBy;
    }

    @Override
    protected Cursor onLoadInBackground() {
        Cursor ret = null;
        switch (getId()) {
            case QUERY_PUZZLES:
                ret = AppDatabaseApi.getInstance(context).queryPuzzles(orderBy);
                break;
            default:
                ret = super.onLoadInBackground();
        }
        return ret;
    }
}
