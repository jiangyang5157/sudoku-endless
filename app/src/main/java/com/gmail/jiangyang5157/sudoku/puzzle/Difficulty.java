package com.gmail.jiangyang5157.sudoku.puzzle;

public enum Difficulty {
    EASY("Easy", new int[]{4, 5}, new int[]{36, 49}),
    NORMAL("Normal", new int[]{3, 4}, new int[]{32, 35}),
    NIGHTMARE("Nightmare", new int[]{2, 3}, new int[]{28, 31}),
    HELL("Hell", new int[]{0, 1}, new int[]{20, 23});

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
