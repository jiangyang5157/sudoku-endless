package com.gmail.jiangyang5157.sudoku.puzzle;

import java.util.ArrayList;

import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

import com.gmail.jiangyang5157.sudoku.Config;
import com.gmail.jiangyang5157.tookit.algorithm.dlx.Matrix;
import com.gmail.jiangyang5157.tookit.algorithm.dlx.MatrixHelper;
import com.gmail.jiangyang5157.tookit.algorithm.dlx.Node;

/**
 * @author Yang
 */
public class PuzzleSolver {

    private MatrixHelper matrixBuilder = null;
    private int[][] matrixCover = null;
    private Matrix matrix = null;
    private int[][] board = null;

    public PuzzleSolver() {
        matrixBuilder = new MatrixHelper();
        convertBoard();
    }

    /**
     * Converts a solvable sudoku board to exact cover matrix
     */
    private void convertBoard() {
        matrixCover = new int[Config.MATRIX_ROWS_COUNT][Config.MATRIX_COLUMNS_COUNT];

        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                for (int k = 0; k < Config.SUDOKU_SIZE; k++) {
                    int rowIndex = getRowIndex(k, i, j);
                    matrixCover[rowIndex][i * Config.SUDOKU_SIZE + j + Config.MATRIX_OFFSET_CELL] = 1;
                    matrixCover[rowIndex][k * Config.SUDOKU_SIZE + i + Config.MATRIX_OFFSET_ROW] = 1;
                    matrixCover[rowIndex][k * Config.SUDOKU_SIZE + j + Config.MATRIX_OFFSET_COLUMN] = 1;
                    matrixCover[rowIndex][k * Config.SUDOKU_SIZE + getBlock(rowIndex) + Config.MATRIX_OFFSET_BLOCK] = 1;
                }
            }
        }
    }

    /**
     * Add known positions to the exact cover matrix
     */
    private void coverKnown() {
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                if (board[i][j] != 0) {
                    int rowIndex = getRowIndex(board[i][j] - 1, i, j);
                    Node rowNode = matrixBuilder.getRowHeader(rowIndex);
                    matrix.cover(rowNode.getRoot());
                    for (Node rightNode = rowNode.getRight(); rightNode != rowNode; rightNode = rightNode.getRight()) {
                        matrix.cover(rightNode.getRoot());
                    }
                }
            }
        }
    }

    public void updateBoard(int i, int j, int value) {
        board[i][j] = value;
    }

    public void updateKnow(int i, int j, int value) {
        matrix = matrixBuilder.build(matrixCover);
        coverKnown();
    }

    public boolean hasUniqueSolution() {
        ArrayList<int[]> results = matrix.solve(2);
        int resultsSize = results.size();
//		System.out.println("hasUniqueSolution() - resultsSize: " + resultsSize);
        return resultsSize == 1 ? true : false;
    }

    /**
     * @return results count
     */
    public int solve(int maxResultsSize) {
        ArrayList<int[]> results = matrix.solve(maxResultsSize);
        int ret = results.size();
        System.out.println("solve(" + maxResultsSize + "): " + ret);

        if (ret > 0) {
            // install the first solution to board
            int[] result = results.get(0);
            int length = result.length;
            for (int i = 0; i < length; i++) {
                board[PuzzleSolver.getRow(result[i])][PuzzleSolver.getColumn(result[i])]
                        = PuzzleSolver.getValue(result[i]) + 1;
            }
        }

        return ret;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
        matrix = matrixBuilder.build(matrixCover);
        coverKnown();
    }

    public static final SparseIntArray getInappropriate(int[][] board) {
        SparseIntArray ret = new SparseIntArray();

        SparseIntArray[] rows = new SparseIntArray[Config.SUDOKU_SIZE];
        SparseIntArray[] columns = new SparseIntArray[Config.SUDOKU_SIZE];
        SparseIntArray[] blocks = new SparseIntArray[Config.SUDOKU_BLOCKS_COUNT];
        if (board != null) {
            for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
                if (rows[i] == null) {
                    rows[i] = new SparseIntArray();
                }
                for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                    if (board[i][j] != 0) {
                        //need to be checked

                        int index = getIndex(i, j);

                        //index of the same value(board[i][j]) in current row
                        int prevIndexFromRows = rows[i].get(board[i][j], -1);

                        if (prevIndexFromRows >= 0) {
                            ret.put(prevIndexFromRows, rows[i].keyAt(rows[i].indexOfValue(prevIndexFromRows)));
                            ret.put(index, board[i][j]);
                        } else {
                            rows[i].put(board[i][j], index);
                        }

                        if (columns[j] == null) {
                            columns[j] = new SparseIntArray();
                        }

                        //index of the same value(board[i][j]) in current column
                        int prevIndexFromColumns = columns[j].get(board[i][j], -1);

                        if (prevIndexFromColumns >= 0) {
                            ret.put(prevIndexFromColumns, columns[j].keyAt(columns[j].indexOfValue(prevIndexFromColumns)));
                            ret.put(index, board[i][j]);
                        } else {
                            columns[j].put(board[i][j], index);
                        }

                        int blockIndex = getBlock(i, j);
                        if (blocks[blockIndex] == null) {
                            blocks[blockIndex] = new SparseIntArray();
                        }

                        //index of the same value(board[i][j]) in current block
                        int prevIndexFromBlocks = blocks[blockIndex].get(board[i][j], -1);
                        if (prevIndexFromBlocks >= 0) {
                            ret.put(prevIndexFromBlocks, blocks[blockIndex].keyAt(blocks[blockIndex].indexOfValue(prevIndexFromBlocks)));
                            ret.put(index, board[i][j]);
                        } else {
                            blocks[blockIndex].put(board[i][j], index);
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * @param board
     * @return Check if the board is correct by Sudoku rules
     */
    public static final boolean isAppropriate(int[][] board) {
        boolean ret = board == null ? false : true;

        SparseBooleanArray[] rows = new SparseBooleanArray[Config.SUDOKU_SIZE];
        SparseBooleanArray[] columns = new SparseBooleanArray[Config.SUDOKU_SIZE];
        SparseBooleanArray[] blocks = new SparseBooleanArray[Config.SUDOKU_BLOCKS_COUNT];
        for (int i = 0; ret && (i < Config.SUDOKU_SIZE); i++) {
            if (rows[i] == null) {
                rows[i] = new SparseBooleanArray();
            }
            for (int j = 0; ret && (j < Config.SUDOKU_SIZE); j++) {
                if (board[i][j] != 0) {
                    if (rows[i].get(board[i][j])) {
                        ret = false;
                    } else {
                        rows[i].put(board[i][j], true);
                    }

                    if (columns[j] == null) {
                        columns[j] = new SparseBooleanArray();
                    }

                    if (columns[j].get(board[i][j])) {
                        ret = false;
                    } else {
                        columns[j].put(board[i][j], true);
                    }

                    int blockIndex = getBlock(i, j);
                    if (blocks[blockIndex] == null) {
                        blocks[blockIndex] = new SparseBooleanArray();
                    }

                    if (blocks[blockIndex].get(board[i][j])) {
                        ret = false;
                    } else {
                        blocks[blockIndex].put(board[i][j], true);
                    }
                }
            }
        }
        return ret;
    }

    /**
     * @param value a value of board
     * @param i     a row of board
     * @param j     a column of board
     * @return The row index of the Matrix
     */
    public static final int getRowIndex(int value, int i, int j) {
        return value * Config.SUDOKU_SIZE * Config.SUDOKU_SIZE + i * Config.SUDOKU_SIZE + j;
    }

    /**
     * @param rowIndex a given row index of the Matrix
     * @return The row of board
     */
    public static final int getRow(int rowIndex) {
        return (rowIndex / Config.SUDOKU_SIZE) % Config.SUDOKU_SIZE;
    }

    /**
     * @param rowIndex a given row index of the Matrix
     * @return The column of board
     */
    public static final int getColumn(int rowIndex) {
        return rowIndex % Config.SUDOKU_SIZE;
    }

    /**
     * @param rowIndex a given row index of the Matrix
     * @return a value of board
     */
    public static final int getValue(int rowIndex) {
        return rowIndex / (Config.SUDOKU_SIZE * Config.SUDOKU_SIZE);
    }

    /**
     * @param rowIndex a given row index of the Matrix
     * @return a block of board
     */
    public static final int getBlock(int rowIndex) {
        return ((getRow(rowIndex) / Config.SUDOKU_BLOCK_SIZE) * Config.SUDOKU_BLOCK_SIZE)
                + (getColumn(rowIndex) / Config.SUDOKU_BLOCK_SIZE);
    }

    /**
     * @param i a row of board
     * @param j a column of board
     * @return a block of board
     */
    public static final int getBlock(int i, int j) {
        return (i / Config.SUDOKU_BLOCK_SIZE * Config.SUDOKU_BLOCK_SIZE + j / Config.SUDOKU_BLOCK_SIZE);
    }

    /**
     * @param i a row of board
     * @param j a column of board
     * @return The index of board
     */
    public static final int getIndex(int i, int j) {
        return i * Config.SUDOKU_SIZE + j;
    }
}
