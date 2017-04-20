package com.gmail.jiangyang5157.sudoku.ui.puzzle;

import android.os.AsyncTask;

import com.gmail.jiangyang5157.sudoku.Config;
import com.gmail.jiangyang5157.sudoku.puzzle.Level;

import java.util.ArrayList;

import sudoku.Sudoku;

public class PuzzleGeneratorTask extends AsyncTask<Level, Integer, ArrayList<int[][]>> {

    public interface Listener {

        void onPreExecute();

        void onPostExecute(ArrayList<int[][]> result);
    }

    private Listener mListener = null;

    PuzzleGeneratorTask(Listener listener) {
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mListener != null) {
            mListener.onPreExecute();
        }
    }

    @Override
    protected ArrayList<int[][]> doInBackground(Level... params) {
        ArrayList<int[][]> ret = new ArrayList<>(2);
        int[][] value = buildUniqueSolutionPuzzle(params[0]);
        int[][] puzzleValues = solveUniqueSolutionPuzzle(value);
        ret.add(puzzleValues);
        ret.add(value);
        return ret;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    /**
     * @param result result.get(0) is puzzle values, result.get(1) is values
     */
    @Override
    protected void onPostExecute(ArrayList<int[][]> result) {
        super.onPostExecute(result);
        if (mListener != null) {
            mListener.onPostExecute(result);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    /**
     * @param i a row of board
     * @param j a column of board
     * @return a block of board
     */
    public static int getBlock(int i, int j) {
        return (i / Config.SUDOKU_BLOCK_SIZE * Config.SUDOKU_BLOCK_SIZE + j / Config.SUDOKU_BLOCK_SIZE);
    }

    /**
     * @param i a row of board
     * @param j a column of board
     * @return The index of board
     */
    static int getIndex(int i, int j) {
        return i * Config.SUDOKU_SIZE + j;
    }

    private int[][] buildUniqueSolutionPuzzle(Level level) {
        String raw = Sudoku.generatePuzzle(Config.SUDOKU_BLOCK_SIZE, level.randomMinSubGivens(), level.randomMinTotalGivens());
        return puzzleBytes2Array(raw.getBytes());
    }

    private int[][] solveUniqueSolutionPuzzle(int[][] puzzle) {
        StringBuilder raw = new StringBuilder();
        for (int r = 0, i = 0; r < Config.SUDOKU_SIZE; r++) {
            for (int c = 0; c < Config.SUDOKU_SIZE; c++, i++) {
                raw.append(String.valueOf(puzzle[r][c]));
            }
        }

        String out = Sudoku.solveRaw(Config.SUDOKU_BLOCK_SIZE, raw.toString(), 1);
        String[] data = out.split(new String(new byte[]{Sudoku.SOLUTION_PREFIX}));
        return puzzleBytes2Array(data[1].getBytes());
    }

    private int[][] puzzleBytes2Array(byte[] bs) {
        int[][] ret = new int[Config.SUDOKU_SIZE][Config.SUDOKU_SIZE];
        for (int r = 0, i = 0; r < Config.SUDOKU_SIZE; r++) {
            for (int c = 0; c < Config.SUDOKU_SIZE; c++, i++) {
                ret[r][c] = bs[i] - '0';
            }
        }
        return ret;
    }
}
