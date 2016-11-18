package com.gmail.jiangyang5157.sudoku.puzzle;

import android.util.Log;
import android.util.SparseIntArray;

import com.gmail.jiangyang5157.sudoku.Config;
import com.gmail.jiangyang5157.tookit.math.Mersenne;

public class PuzzleGenerator {
    private static final String TAG = "[PuzzleGenerator]";

    public static final int[] BLOCK3_BLOCKS_048 = new int[]{0, 4, 8};

    public interface Listener {
        public void onGenerateTotalPending(int count);

        public void onHandledPending(int count);
    }

    public int[][] generate(int[][] nodes, Level level) {
        int[][] ret = arrayClone(nodes);

        int lowerBoundInPuzzle = level.randomMinInPuzzle();

        int remainedNodesInPuzzle = Config.SUDOKU_SIZE * Config.SUDOKU_SIZE;
        int[] remainedNodesInEachRow = new int[Config.SUDOKU_SIZE];
        int[] remainedNodesInEachColumn = new int[Config.SUDOKU_SIZE];
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            remainedNodesInEachRow[i] = Config.SUDOKU_SIZE;
            remainedNodesInEachColumn[i] = Config.SUDOKU_SIZE;
        }
        int length = Config.SUDOKU_SIZE * Config.SUDOKU_SIZE;
        for (int i = 0; i < length; i++) {
            int index = level.getOrder()[i];
            int r = index / Config.SUDOKU_SIZE;
            int c = index % Config.SUDOKU_SIZE;

            if (lowerBoundInPuzzle < remainedNodesInPuzzle) {
                int lowerBoundInEachRowAndColumn = level
                        .randomMinInEachRowAndCol();
                if (lowerBoundInEachRowAndColumn < remainedNodesInEachRow[r]
                        && lowerBoundInEachRowAndColumn < remainedNodesInEachColumn[c]) {
                    boolean dig = true;

                    ret[r][c] = 0;

                    if (dig) {
                        remainedNodesInPuzzle--;
                        remainedNodesInEachRow[r]--;
                        remainedNodesInEachColumn[c]--;
                    } else {
                        ret[r][c] = nodes[r][c];
                    }
                } else {
                    continue;
                }
            } else {
                break;
            }
        }

        return ret;
    }

    public int[][] generateUnique(int[][] nodes, Level level) {
        return generateUnique(nodes, level, null);
    }

    /*
     * I have no clue what I was doing.
     */
    public int[][] generateUnique(int[][] nodes, Level level, Listener listener) {
        long startTime = System.currentTimeMillis();

        int[][] ret = arrayClone(nodes);

        PuzzleSolver solver = new PuzzleSolver();
        solver.setBoard(arrayClone(ret));

        int[] remainedNodesInEachRow = new int[Config.SUDOKU_SIZE];
        int[] remainedNodesInEachColumn = new int[Config.SUDOKU_SIZE];
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            remainedNodesInEachRow[i] = Config.SUDOKU_SIZE;
            remainedNodesInEachColumn[i] = Config.SUDOKU_SIZE;
        }

        int maxDigCount = Config.SUDOKU_SIZE * Config.SUDOKU_SIZE
                - level.randomMinInPuzzle();
        if (listener != null) {
            listener.onGenerateTotalPending(maxDigCount);
        }

        int lowerBoundInEachRowAndColumn = level.randomMinInEachRowAndCol();

        int length = Config.SUDOKU_SIZE * Config.SUDOKU_SIZE;
        int hasDigedCount = 0;

        int digGapSize = Config.SUDOKU_SIZE;
        SparseIntArray digBooleanGap = new SparseIntArray();
        SparseIntArray valueCache = new SparseIntArray();
        boolean isAlright = true;

        for (int i = 0; i < length; i++) {
            int index = level.getOrder()[i];
            int r = index / Config.SUDOKU_SIZE;
            int c = index % Config.SUDOKU_SIZE;

            if (listener != null) {
                listener.onHandledPending(hasDigedCount);
            }

            if (!isAlright || hasDigedCount < maxDigCount) {

                if (!isAlright
                        || (lowerBoundInEachRowAndColumn < remainedNodesInEachRow[r]
                        && lowerBoundInEachRowAndColumn < remainedNodesInEachColumn[c])) {

                    if (isAlright && digBooleanGap.size() == 0) {
                        if (hasDigedCount >= 2 * Config.SUDOKU_SIZE
                                && hasDigedCount < 3 * Config.SUDOKU_SIZE) {
                            digGapSize = Config.SUDOKU_SIZE
                                    - Config.SUDOKU_BLOCK_SIZE / 2;
                        } else if (hasDigedCount >= 3 * Config.SUDOKU_SIZE
                                && hasDigedCount < 4 * Config.SUDOKU_SIZE) {
                            digGapSize = Config.SUDOKU_SIZE
                                    - Config.SUDOKU_BLOCK_SIZE;
                        } else if (hasDigedCount >= 4 * Config.SUDOKU_SIZE
                                && hasDigedCount < 5 * Config.SUDOKU_SIZE) {
                            digGapSize = Config.SUDOKU_BLOCK_SIZE;
                        } else if (hasDigedCount >= 5 * Config.SUDOKU_SIZE
                                && hasDigedCount < 6 * Config.SUDOKU_SIZE) {
                            digGapSize = 2;
                        } else if (hasDigedCount >= 6 * Config.SUDOKU_SIZE) {
                            digGapSize = 1;
                        }
                    }

                    boolean dig = false;

                    if (valueCache.get(i) == 0) {
                        valueCache.put(i, ret[r][c]);
                    }

                    ret[r][c] = 0;
                    solver.updateBoard(r, c, ret[r][c]);

                    if (digGapSize == 1) {
                        // normal
                        solver.updateKnow(r, c, ret[r][c]);
                        if (solver.hasUniqueSolution()) {
                            dig = true;

                            hasDigedCount++;
                            remainedNodesInEachRow[r]--;
                            remainedNodesInEachColumn[c]--;
                        } else {
                            dig = false;
                            ret[r][c] = valueCache.get(i);
                            solver.updateBoard(r, c, ret[r][c]);
                        }
                    } else {
                        int size = digBooleanGap.size();
                        if (size == digGapSize) {
                            // !
                            for (int i1 = 0; i1 < size - 1; i1++) {
                                if (digBooleanGap.get(i1) == -1) {
                                    // check this hole
                                    isAlright = true;
                                    solver.updateKnow(r, c, ret[r][c]);
                                    if (solver.hasUniqueSolution()) {
                                        // not this one caused the seeking
                                        dig = true;
                                        // means checked
                                        digBooleanGap.put(i1, 1);

                                        hasDigedCount++;
                                        remainedNodesInEachRow[r]--;
                                        remainedNodesInEachColumn[c]--;

                                        break;
                                    } else {
                                        // maybe it is the one caused the
                                        // seeking
                                        dig = false;
                                        ret[r][c] = valueCache.get(i);
                                        solver.updateBoard(r, c, ret[r][c]);

                                        // digBooleanGap.put(i1, 0);
                                        digBooleanGap.clear();

                                        break;
                                    }
                                } else if (digBooleanGap.get(i1) == 1) {
                                    if (i1 == size - 2) {
                                        // due to i = size - 1 already check,
                                        // and it was false
                                        isAlright = true;
                                        dig = false;
                                        ret[r][c] = valueCache.get(i);
                                        solver.updateBoard(r, c, ret[r][c]);

                                        digBooleanGap.clear();
                                        break;
                                    } else {
                                        continue;
                                    }
                                }
                            }
                        } else if (size == digGapSize - 1) {
                            solver.updateKnow(r, c, ret[r][c]);
                            if (solver.hasUniqueSolution()) {
                                // the digs before are all right, reset the gap
                                isAlright = true;
                                dig = true;
                                digBooleanGap.clear();

                                hasDigedCount++;
                                remainedNodesInEachRow[r]--;
                                remainedNodesInEachColumn[c]--;
                            } else {
                                isAlright = false;
                                dig = false;
                                digBooleanGap.put(size, 0);

                                hasDigedCount++;
                                remainedNodesInEachRow[r]--;
                                remainedNodesInEachColumn[c]--;

                                for (int ii = i; i - ii <= size; ii--) {
                                    int indexx = level.getOrder()[ii];
                                    int rr = indexx / Config.SUDOKU_SIZE;
                                    int cc = indexx % Config.SUDOKU_SIZE;

                                    ret[rr][cc] = valueCache.get(ii);
                                    solver.updateBoard(rr, cc, ret[rr][cc]);

                                    hasDigedCount--;
                                    remainedNodesInEachRow[r]++;
                                    remainedNodesInEachColumn[c]++;
                                }

                                i -= (size + 1);
                            }
                        } else {
                            // without check, just dig first
                            isAlright = false;
                            dig = true;
                            digBooleanGap.append(size, -1);

                            hasDigedCount++;
                            remainedNodesInEachRow[r]--;
                            remainedNodesInEachColumn[c]--;
                        }
                    }
//                    Log.d(TAG, "i = " + i + ", [" + r + "]" + "[" + c + "] = " + ret[r][c] + ", dig: " + dig + ", hasDigedCount: " + hasDigedCount);
                } else {
                    continue;
                }
            } else {
                break;
            }
        }

        Log.d(TAG, "Time used: " + (System.currentTimeMillis() - startTime));
        return ret;
    }

    public int[][] getImcompleteSudokuByInitRandomizedBlocks(int[] blockIndexes) {
        int[][] ret = null;
        int blockIndexesLength = blockIndexes.length;

        if (blockIndexesLength < Config.SUDOKU_BLOCKS_COUNT) {
            ret = new int[Config.SUDOKU_SIZE][Config.SUDOKU_SIZE];
            int blockCellesCount = Config.SUDOKU_BLOCK_SIZE * Config.SUDOKU_BLOCK_SIZE;

            for (int i = 0; i < blockIndexesLength; i++) {
                int[] disorderValues = disorder(1, blockCellesCount);

                for (int j = 0; j < blockCellesCount; j++) {
                    ret[j / Config.SUDOKU_BLOCK_SIZE
                            + (blockIndexes[i] / Config.SUDOKU_BLOCK_SIZE)
                            * Config.SUDOKU_BLOCK_SIZE][j % Config.SUDOKU_BLOCK_SIZE
                            + (blockIndexes[i] / Config.SUDOKU_BLOCK_SIZE)
                            * Config.SUDOKU_BLOCK_SIZE] = disorderValues[j];
                }
            }
        }
        return ret;
    }

    /**
     * @param start
     * @param end
     * @return int[end - start + 1] - [start, end]
     */
    public static final int[] disorder(final int start, final int end) {
        int length = end - start + 1;
        int[] ret = new int[length];
        // init value
        for (int i = 0; i < length; i++) {
            ret[i] = i + start;
        }

        // switch by randomizing length times
        Mersenne mMersenne = new Mersenne();
        for (int i = 0; i < length; i++) {
            int temp = ret[i];
            int randomIndex = Math.abs(mMersenne.nextInt(length));
            ret[i] = ret[randomIndex];
            ret[randomIndex] = temp;
        }

        return ret;
    }

    public static int[][] arrayClone(final int[][] array) {
        int rowsCount = array.length;
        int columnsCount = rowsCount > 0 ? array[0].length : 0;
        int[][] ret = new int[rowsCount][columnsCount];
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                ret[i][j] = array[i][j];
            }
        }
        return ret;
    }

    // //sulution = 1
    // new int[][]{
    // {0,0,0,0,0,0,0,0,0},
    // {0,0,4,1,0,2,6,0,0},
    // {0,3,0,0,5,0,0,2,0},
    // {0,2,0,0,1,0,0,3,0},
    // {0,0,6,5,0,4,1,0,0},
    // {0,8,0,0,7,0,0,4,0},
    // {0,7,0,0,2,0,0,6,0},
    // {0,0,1,4,0,3,5,0,0},
    // {0,0,0,0,0,0,0,0,0}};
    // // solution = 2
    // new int[][]{
    // {0,0,3,4,5,6,7,8,9},
    // {4,5,6,7,8,9,1,2,3},
    // {7,8,9,1,2,3,4,5,6},
    // {0,0,4,3,6,5,8,9,7},
    // {3,6,5,8,9,7,2,1,4},
    // {8,9,7,2,1,4,3,6,5},
    // {5,3,1,6,4,2,9,7,8},
    // {6,4,2,9,7,8,5,3,1},
    // {9,7,8,5,3,1,6,4,2}};
    // //sulution = 188
    // new int[][]{
    // {0,0,0,0,7,0,9,4,0},
    // {0,7,0,0,9,0,0,0,5},
    // {3,0,0,0,0,5,0,7,0},
    // {0,0,7,4,0,0,1,0,0},
    // {4,6,3,0,8,0,0,0,0},
    // {0,0,0,0,0,7,0,8,0},
    // {8,0,0,7,0,0,0,0,0},
    // {7,0,0,0,0,0,0,2,8},
    // {0,5,0,0,6,8,0,0,0}};
    // new int[][]{
    // {0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0},
    // {0,0,0,0,0,0,0,0,0}};
}
