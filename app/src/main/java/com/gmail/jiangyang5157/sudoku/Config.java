package com.gmail.jiangyang5157.sudoku;

import com.gmail.jiangyang5157.tookit.base.data.structure.contact.EmailAddress;
import com.gmail.jiangyang5157.tookit.base.data.structure.contact.Type;
import com.gmail.jiangyang5157.tookit.base.data.structure.contact.person.Author;

import java.util.ArrayList;

/**
 * User: Yang
 * Date: 2014/11/16
 * Time: 23:40
 */
public class Config {
    private static final String TAG = "[Config]";

    public static final boolean DEBUG = false;

    public static ArrayList<Author> authors = new ArrayList<Author>();

    static {
        Author author = new Author("Yang", "Jiang");
        author.putEmailAddress(Type.HOME, new EmailAddress("jiangyang5157", "gmail.com"));
        authors.add(author);
    }

    public static final String PUZZLE_FILE_END = "se";

    /*
     * -------------------------
     * | x x x | x x x | x x x |
     * | x x x | x x x | x x x |
     * | x x x | x x x | x x x |
     * -------------------------
     * | x x x | x x x | x x x |
     * | x x x | x x x | x x x |
     * | x x x | x x x | x x x |
     * -------------------------
     * | x x x | x x x | x x x |
     * | x x x | x x x | x x x |
     * | x x x | x x x | x x x |
     * -------------------------
     */
    public static final int SUDOKU_BLOCK_SIZE = 3;
    public static final int SUDOKU_UNKNOWN_CELL_COUNT_MIN = 17;
    public static final int SUDOKU_UNKNOWN_CELL_COUNT_MAX = 64;

    public static final int SUDOKU_BLOCKS_COUNT = SUDOKU_BLOCK_SIZE * SUDOKU_BLOCK_SIZE;
    public static final int SUDOKU_SIZE = SUDOKU_BLOCK_SIZE * SUDOKU_BLOCK_SIZE;
    public static final int SUDOKU_CELL_COUNT = SUDOKU_SIZE * SUDOKU_SIZE;


    /*
         * 1. Every square must have a number in it:
         * [A Number has to be in 0,0], [A Number has to be in 0,1], ..., [A Number has to be in 8,8]
         * = 9 * 9 = 81 requirements.
         *
         * 2. Every row must have numbers 1-9 in it:
         * [Row 0 must have a 1], [Row 0 must have a 2], ...,
         * [Row 1 must have a 1], ... [Row 8 must have a 1], ..., [Row 8 must have a 9]
         * = 9 * 9 = 81 requirements.
         *
         * 3. Every column must have numbers 1-9 in it:
         * [Column 0 must have a 1], ..., [Column 8 must have a 9]
         * = 9 * 9 = 81 requirements.
         *
         * 4. Every block (always are blocks) must have numbers 1-9 in it:
         * [Block 0 must have a 1], [Block 0 must have a 2], ..., [Block 8 must have a 9]
         * = 9 * 9 = 81 requirements.
         */
    public static final int SUDOKU_REQUIREMENTS_COUNT = 4;

    /*
     * Each row corresponding to each of a filling method.
     * Each column represents a state of requirement.
     */
    public static final int MATRIX_ROWS_COUNT = SUDOKU_SIZE * SUDOKU_SIZE * SUDOKU_SIZE;
    public static final int MATRIX_COLUMNS_COUNT = SUDOKU_SIZE * SUDOKU_SIZE * SUDOKU_REQUIREMENTS_COUNT;

    /*
     * requirement 1 - column 0~80 is for testing each cell contain exactly a number.
     * requirement 2 - column 81~161 is for testing each row contain each number exactly once.
     * requirement 3 - column 162~242 is for testing each column contain each number exactly once.
     * requirement 4 - column 243~323 is for testing each block contain each number exactly once.
     */
    public static final int MATRIX_OFFSET_CELL = 0;
    public static final int MATRIX_OFFSET_ROW = SUDOKU_SIZE * SUDOKU_SIZE;
    public static final int MATRIX_OFFSET_COLUMN = MATRIX_OFFSET_ROW + SUDOKU_SIZE * SUDOKU_SIZE;
    public static final int MATRIX_OFFSET_BLOCK = MATRIX_OFFSET_COLUMN + SUDOKU_SIZE * SUDOKU_SIZE;
}
