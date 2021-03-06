package com.gmail.jiangyang5157.sudoku.ui.puzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gmail.jiangyang5157.sudoku.Config;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.puzzle.*;
import com.gmail.jiangyang5157.sudoku.puzzle.render.PuzzleImpl;
import com.gmail.jiangyang5157.sudoku.puzzle.render.RenderThread;
import com.gmail.jiangyang5157.sudoku.puzzle.render.node.Node;
import com.gmail.jiangyang5157.tookit.android.base.DeviceUtils;
import com.gmail.jiangyang5157.tookit.android.base.EncodeUtils;
import com.google.gson.Gson;

import java.io.IOException;

public class PuzzleView extends SurfaceView implements SurfaceHolder.Callback, KeypadFragment.Listener, Puzzle {
    private final static String TAG = "[PuzzleView]";

    private Listener mListener = null;

    public interface Listener {
        void onCompleted();
    }

    private SurfaceHolder surfaceHolder = null;

    private static final int FPS = 60;
    private RenderThread renderThread = null;

    private PuzzleImpl puzzleImpl = null;

    public PuzzleView(Context context) {
        super(context);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        setClickable(true);

        puzzleImpl = new PuzzleImpl();
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    public void refresh() {
        if (renderThread != null) {
            renderThread.refresh();
        }
    }


    @Override
    public void generatePuzzle(int[][] puzzleValues, int[][] values) {
        puzzleImpl.generatePuzzle(puzzleValues, values);
        puzzleImpl.reset();

        refresh();
    }

    @Override
    public void generatePuzzle(NodeCache[][] nodesCache) {
        puzzleImpl.generatePuzzle(nodesCache);
        puzzleImpl.reset();
        validate(false);

        refresh();
    }

    @Override
    public void clearPuzzle() {
        puzzleImpl.clearPuzzle();
        puzzleImpl.reset();

        refresh();
    }

    @Override
    public void setInappropriate(SparseIntArray inappropriate) {
        puzzleImpl.setInappropriate(inappropriate);
        puzzleImpl.reset();
        refresh();
    }

    @Override
    public int[][] getValues() {
        return puzzleImpl.getValues();
    }

    @Override
    public int[][] getPuzzleValues() {
        return puzzleImpl.getPuzzleValues();
    }

    private void setCanvasSize(int width, int height) {
        puzzleImpl.set(0, 0, width, height);
        refresh();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
//        Log.d(TAG, "surfaceChanged - " + width + ", " + height);
        setCanvasSize(width, height);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
//        Log.d(TAG, "surfaceCreated");
        if (renderThread == null || renderThread.getState() == Thread.State.TERMINATED) {
            renderThread = new RenderThread(surfaceHolder, puzzleImpl, FPS);
        }

        renderThread.onStart();
        renderThread.onPause();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//        Log.d(TAG, "surfaceDestroyed");
        renderThread.onStop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        puzzleImpl.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                renderThread.onResume();
                break;
            case MotionEvent.ACTION_UP:
                renderThread.onPause(true);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                renderThread.onPause();
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public boolean inputNumber(int number) {
        boolean ret = false;

        Node mNode = puzzleImpl.getNode();
        if (mNode != null) {
            int b4 = mNode.getValue();
            ret = mNode.inputNumber(number);
            int after = mNode.getValue();

            if (b4 != 0 && after == 0) {
                puzzleImpl.setFilledNodeCounter(puzzleImpl.getFilledNodeCounter() - 1);
            } else if (b4 == 0 && after != 0) {
                puzzleImpl.setFilledNodeCounter(puzzleImpl.getFilledNodeCounter() + 1);
            }

            if (ret) {
                puzzleImpl.refreshNodes();
                refresh();
            }

            if (isCompleted(false)) {
                if (mListener != null) {
                    mListener.onCompleted();
                }
            }
        }

        return ret;
    }

    /**
     * Validate puzzle if it is fine
     *
     * @param highlight true if we want to highlight inappropriate nodes
     * @return true if puzzle is fine
     */
    public boolean validate(boolean highlight) {
        boolean ret = false;
        int[][] board = getValues();
        SparseIntArray inappropriate = getInappropriate(board);
        if (inappropriate.size() == 0) {
            ret = true;
        } else if (highlight) {
            setInappropriate(inappropriate);
        }
        return ret;
    }

    public SparseIntArray getInappropriate(int[][] board) {
        SparseIntArray ret = new SparseIntArray();
        SparseIntArray[] rows = new SparseIntArray[Config.SUDOKU_SIZE];
        SparseIntArray[] columns = new SparseIntArray[Config.SUDOKU_SIZE];
        SparseIntArray[] blocks = new SparseIntArray[Config.SUDOKU_SIZE];
        if (board != null) {
            for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
                if (rows[i] == null) {
                    rows[i] = new SparseIntArray();
                }
                for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                    if (board[i][j] != 0) {
                        //need to be checked

                        int index = PuzzleGeneratorTask.getIndex(i, j);

                        //index of the same value(board[i][j]) in current row
                        int prevIndexFromRows = rows[i].get(board[i][j], -1);

                        if (prevIndexFromRows >= 0) {
                            ret.put(prevIndexFromRows, rows[i].keyAt(rows[i].indexOfValue(prevIndexFromRows)));
                            ret.put(index, board[i][j]);
                        } else {
                            rows[i].put(board[i][j], index);
                        }

                        if (columns[j] == null) {
                            columns[j] = new SparseIntArray();
                        }

                        //index of the same value(board[i][j]) in current column
                        int prevIndexFromColumns = columns[j].get(board[i][j], -1);

                        if (prevIndexFromColumns >= 0) {
                            ret.put(prevIndexFromColumns, columns[j].keyAt(columns[j].indexOfValue(prevIndexFromColumns)));
                            ret.put(index, board[i][j]);
                        } else {
                            columns[j].put(board[i][j], index);
                        }

                        int blockIndex = PuzzleGeneratorTask.getBlock(i, j);
                        if (blocks[blockIndex] == null) {
                            blocks[blockIndex] = new SparseIntArray();
                        }

                        //index of the same value(board[i][j]) in current block
                        int prevIndexFromBlocks = blocks[blockIndex].get(board[i][j], -1);
                        if (prevIndexFromBlocks >= 0) {
                            ret.put(prevIndexFromBlocks, blocks[blockIndex].keyAt(blocks[blockIndex].indexOfValue(prevIndexFromBlocks)));
                            ret.put(index, board[i][j]);
                        } else {
                            blocks[blockIndex].put(board[i][j], index);
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * Validate puzzle if it is completed
     *
     * @param highlight true if we want to highlight inappropriate nodes
     * @return true if puzzle is completed
     */
    public boolean isCompleted(boolean highlight) {
        return validate(highlight) && puzzleImpl.getFilledNodeCounter() == Config.SUDOKU_CELL_COUNT;
    }

    @Override
    public boolean onErase() {
        return inputNumber(0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (renderThread != null) {
            renderThread.onWindowFocusChanged(hasFocus);
        }
    }

    private Bitmap screenshot(int dstWidth, int dstHeight) {
        if (getWidth() <= 0 || getHeight() <= 0) {
            return null;
        }
        Bitmap screenshotBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        renderThread.screenshot(screenshotBitmap);
        return Bitmap.createScaledBitmap(screenshotBitmap, dstWidth, dstHeight, true);
    }

    public String getPuzzleDrawable() {
        int dstWidth = DeviceUtils.getDimensionPixelOffset(getContext().getResources(), R.dimen.puzzle_drawable_width);
        int dstHeight = DeviceUtils.getDimensionPixelOffset(getContext().getResources(), R.dimen.puzzle_drawable_height);
        try {
            return EncodeUtils.encodeBitmap(screenshot(dstWidth, dstHeight), 100);
        } catch (IOException e) {
            Log.e(TAG, "Create puzzle drawable string failed.");
        }
        return null;
    }

    public String getPuzzleCache(NodeCache[][] nodesCache, Level level) {
        Gson gson = new Gson();
        return gson.toJson(new PuzzleCache(nodesCache, level));
    }

    @Override
    public NodeCache[][] getNodesCache() {
        return puzzleImpl.getNodesCache();
    }
}
