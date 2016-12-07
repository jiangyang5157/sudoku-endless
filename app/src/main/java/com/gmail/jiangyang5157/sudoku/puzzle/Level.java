package com.gmail.jiangyang5157.sudoku.puzzle;

import android.support.annotation.NonNull;

import com.gmail.jiangyang5157.tookit.math.Mersenne;

import java.io.Serializable;

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

    public static final int randomPick(@NonNull final int[] range) throws IllegalArgumentException {
        return new Mersenne().nextInt(range[1] - range[0] + 1) + range[0];
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return difficulty.toString();
    }
}
