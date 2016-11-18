package com.gmail.jiangyang5157.sudoku.puzzle;

import android.util.SparseIntArray;

public interface Puzzle {
    public void generatePuzzle(int[][] puzzleValues, int[][] values);

    public void generatePuzzle(NodeCache[][] nodesCache);

    public void clearPuzzle();

    public void setInappropriate(SparseIntArray inappropriate);

    public int[][] getValues();

    public int[][] getPuzzleValues();

    public NodeCache[][] getNodesCache();
}
