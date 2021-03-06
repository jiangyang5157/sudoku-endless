package com.gmail.jiangyang5157.sudoku.puzzle;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Random;

public class Level implements Serializable {

    private static final long serialVersionUID = 2305843009213693954L;

    private final Difficulty difficulty;

    public Level(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int randomMinTotalGivens() {
        return randomPick(difficulty.getMinTotalGivens());
    }

    public int randomMinSubGivens() {
        return randomPick(difficulty.getMinSubGivens());
    }

    private static int randomPick(@NonNull final int[] range) throws IllegalArgumentException {
        return new Random().nextInt(range[1] - range[0] + 1) + range[0];
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return difficulty.toString();
    }
}
