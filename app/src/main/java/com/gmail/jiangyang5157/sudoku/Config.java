package com.gmail.jiangyang5157.sudoku;

/**
 * User: Yang
 * Date: 2014/11/16
 * Time: 23:40
 */
public class Config {
    public static final boolean DEBUG = false;

    public static final String PUZZLE_FILE_END = "se";

    public static final int SUDOKU_BLOCK_SIZE = 3;
    public static final int SUDOKU_SIZE = SUDOKU_BLOCK_SIZE * SUDOKU_BLOCK_SIZE;
    public static final int SUDOKU_CELL_COUNT = SUDOKU_SIZE * SUDOKU_SIZE;
}
