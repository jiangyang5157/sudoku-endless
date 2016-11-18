package com.gmail.jiangyang5157.sudoku.puzzle;

public enum Difficulty {
    DONE("Done", new int[]{81, 81}, new int[]{9, 9}),
    IDIOT("Idiot", new int[]{80, 80}, new int[]{8, 8}),
    EASY("Easy", new int[]{50, 57}, new int[]{5, 7}),
    NORMAL("Normal", new int[]{36, 49}, new int[]{4, 6}),
    NIGHTMARE("Nightmare", new int[]{32, 35}, new int[]{3, 5}),
    HELL("Hell", new int[]{28, 31}, new int[]{2, 4});

    private final String difficulty;
    private final int[] rangeOfMinInPuzzle;
    private final int[] rangeOfMinInEachRowAndCol;

    private Difficulty(String difficulty, int[] rangeOfMinInPuzzle, int[] rangeOfMinInEachRowAndCol) {
        this.difficulty = difficulty;
        this.rangeOfMinInPuzzle = rangeOfMinInPuzzle;
        this.rangeOfMinInEachRowAndCol = rangeOfMinInEachRowAndCol;
    }

    public int[] getRangeOfMinInPuzzle() {
        return rangeOfMinInPuzzle;
    }

    public int[] getRangeOfMinInEachRowAndCol() {
        return rangeOfMinInEachRowAndCol;
    }

    @Override
    public String toString() {
        return difficulty;
    }
}
