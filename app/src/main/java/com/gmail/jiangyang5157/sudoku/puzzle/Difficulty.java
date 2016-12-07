package com.gmail.jiangyang5157.sudoku.puzzle;

public enum Difficulty {
    DONE("Done", new int[]{9, 9}, new int[]{81, 81}),
    IDIOT("Idiot", new int[]{8, 8}, new int[]{80, 80}),
    EASY("Easy", new int[]{5, 7}, new int[]{50, 57}),
    NORMAL("Normal", new int[]{4, 6}, new int[]{36, 49}),
    NIGHTMARE("Nightmare", new int[]{3, 5}, new int[]{32, 35}),
    HELL("Hell", new int[]{2, 4}, new int[]{28, 31});

    private final String difficulty;
    private final int[] minSubGivens;
    private final int[] minTotalGivens;

    Difficulty(String difficulty, int[] minSubGivens, int[] minTotalGivens) {
        this.difficulty = difficulty;
        this.minSubGivens = minSubGivens;
        this.minTotalGivens = minTotalGivens;
    }

    public int[] getMinSubGivens() {
        return minSubGivens;
    }

    public int[] getMinTotalGivens() {
        return minTotalGivens;
    }

    @Override
    public String toString() {
        return difficulty;
    }
}
