package com.gmail.jiangyang5157.sudoku.sql;

import com.gmail.jiangyang5157.tookit.android.sql.BaseTable;

public class PuzzleTable extends BaseTable {
    public final static String TABLE_NAME = "Puzzle";

    public static final String KEY_CACHE = "cache";
    public static final String KEY_DRAWABLE = "drawable";

    public static final String KEY_DATE = "date";

    public static final String KEY_TIMER = "timer";
    public static final String KEY_BEST_TIME = "best_time";

    protected static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME
            + "(" + KEY_ROWID + " integer primary key autoincrement," // required
            + KEY_CACHE + " text,"
            + KEY_DRAWABLE + " text,"
            + KEY_DATE + " text,"
            + KEY_TIMER + " text,"
            + KEY_BEST_TIME + " text);";
}
