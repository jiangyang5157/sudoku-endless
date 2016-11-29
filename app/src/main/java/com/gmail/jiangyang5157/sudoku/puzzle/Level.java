package com.gmail.jiangyang5157.sudoku.puzzle;

import android.support.annotation.NonNull;

import com.gmail.jiangyang5157.sudoku.Config;
import com.gmail.jiangyang5157.tookit.math.Mersenne;

import java.io.Serializable;

public class Level implements Serializable {

    private static final long serialVersionUID = 2305843009213693954L;

    private final Difficulty difficulty;
    private final int[] order = disorder(0, Config.SUDOKU_SIZE * Config.SUDOKU_SIZE - 1);

    public Level(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public int randomMinInPuzzle() {
        return randomPick(difficulty.getRangeOfMinInPuzzle());
    }

    public int randomMinInEachRowAndCol() {
        return randomPick(difficulty.getRangeOfMinInEachRowAndCol());
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int[] getOrder() {
        return order;
    }

    @Override
    public String toString() {
        return difficulty.toString();
    }

    public static final int[] disorder(final int start, final int end) {
        int length = end - start + 1;
        int[] ret = new int[length];
        // init value
        for (int i = 0; i < length; i++) {
            ret[i] = i + start;
        }
        // switch by randomizing length times
        Mersenne rng = new Mersenne();
        for (int i = 0; i < length; i++) {
            int temp = ret[i];
            int randomIndex = Math.abs(rng.nextInt(length));
            ret[i] = ret[randomIndex];
            ret[randomIndex] = temp;
        }
        return ret;
    }

    public static final int randomPick(@NonNull final int[] range) throws IllegalArgumentException {
        return new Mersenne().nextInt(range[1] - range[0]) + range[0];
    }
}
