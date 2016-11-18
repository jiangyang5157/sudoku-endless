package com.gmail.jiangyang5157.sudoku.sql;

import android.content.Context;

import com.gmail.jiangyang5157.tookit.android.sql.BaseAppDatabaseOpenHelper;

public class AppDatabaseOpenHelper extends BaseAppDatabaseOpenHelper {

    /**
     * File will locate in "/data/data/PACKAGE_PATH/DATABASE_NAME" as default
     */
    public static final String DATABASE_NAME = "SudokuEndless.db";

    public static final int DATABASE_VERSION = 9;

    protected AppDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    protected String[] getCreateTableSqlsOnCreate() {
        return new String[]{PuzzleTable.SQL_CREATE_TABLE};
    }

    @Override
    protected String[] getDropTableNamesOnUpgrade() {
        return new String[]{PuzzleTable.TABLE_NAME};
    }
}
