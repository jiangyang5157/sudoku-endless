package com.gmail.jiangyang5157.sudoku.puzzle;

import android.util.SparseIntArray;

public interface Puzzle {

    void generatePuzzle(int[][] puzzleValues, int[][] values);

    void generatePuzzle(NodeCache[][] nodesCache);

    void clearPuzzle();

    void setInappropriate(SparseIntArray inappropriate);

    int[][] getValues();

    int[][] getPuzzleValues();

    NodeCache[][] getNodesCache();
}
