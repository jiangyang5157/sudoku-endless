package com.gmail.jiangyang5157.sudoku.ui.puzzle;

import android.os.AsyncTask;

import com.gmail.jiangyang5157.sudoku.puzzle.Level;
import com.gmail.jiangyang5157.sudoku.puzzle.PuzzleGenerator;
import com.gmail.jiangyang5157.sudoku.puzzle.PuzzleSolver;

import java.util.ArrayList;

public class PuzzleGeneratorTask extends AsyncTask<Level, Integer, ArrayList<int[][]>> {

    public interface Listener {

        public void onPreExecute();

        public void onPostExecute(ArrayList<int[][]> result);
    }

    private Listener mListener = null;

    public PuzzleGeneratorTask(Listener listener) {
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
        ArrayList<int[][]> ret = new ArrayList<int[][]>(2);

        PuzzleGenerator puzzleGenerator = new PuzzleGenerator();
        int[][] puzzleValues = null;
        do {
            puzzleValues = puzzleGenerator.getImcompleteSudokuByInitRandomizedBlocks(PuzzleGenerator.BLOCK3_BLOCKS_048);
        } while (!PuzzleSolver.isAppropriate(puzzleValues));
        PuzzleSolver solver = new PuzzleSolver();
        solver.setBoard(puzzleValues);
        solver.solve(1);
        int[][] values = puzzleGenerator.generateUnique(puzzleValues, params[0]);

        ret.add(puzzleValues);
        ret.add(values);
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
}
