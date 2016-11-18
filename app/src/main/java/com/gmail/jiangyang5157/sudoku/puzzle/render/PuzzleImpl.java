package com.gmail.jiangyang5157.sudoku.puzzle.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.SparseIntArray;
import android.view.MotionEvent;

import com.gmail.jiangyang5157.sudoku.Config;
import com.gmail.jiangyang5157.sudoku.Pref;
import com.gmail.jiangyang5157.sudoku.puzzle.*;
import com.gmail.jiangyang5157.sudoku.puzzle.render.cell.Cell;
import com.gmail.jiangyang5157.sudoku.puzzle.render.node.*;

import java.util.ArrayList;

public class PuzzleImpl extends EditableComponent implements Puzzle {

    private Node[][] nodes = null;

    private Node nodeOnTouchDown = null;
    private Node nodeOnTouchUp = null;
    private Node mNode = null;

    private Point onTouchDown = null;
    private Point onTouching = null;
    private Point onTouchUp = null;

    private int filledNodeCounter = 0;

    public PuzzleImpl() {
        super();
        nodes = new Node[Config.SUDOKU_SIZE][Config.SUDOKU_SIZE];
    }

    @Override
    public void generatePuzzle(int[][] puzzleValues, int[][] values) {
        filledNodeCounter = 0;
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                if (nodes[i][j] == null) {
                    nodes[i][j] = new Node(i, j);
                }

                nodes[i][j].setPuzzleValue(puzzleValues[i][j]);
                nodes[i][j].setValue(values[i][j]);

                nodes[i][j].setEditable(values[i][j] == 0 ? true : false);
                nodes[i][j].getStateControler().setState(NormalNode.ID);

                nodes[i][j].clearCells(true);

                filledNodeCounter += nodes[i][j].getValue() == 0 ? 0 : 1;
            }
        }
    }

    @Override
    public void generatePuzzle(NodeCache[][] nodesCache) {
        filledNodeCounter = 0;
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                NodeCache nodeCache = nodesCache[i][j];

                if (nodes[i][j] == null) {
                    nodes[i][j] = new Node(i, j);
                }

                nodes[i][j].setPuzzleValue(nodeCache.getPuzzleValue());
                nodes[i][j].setValue(nodeCache.getValue());

                nodes[i][j].setEditable(nodeCache.isEditable());
                nodes[i][j].getStateControler().setState(NormalNode.ID);

                ArrayList<Integer> cellsValue = nodeCache.getCellsValue();
                for (int k = 0; k < Node.CELLS_COUNT; k++) {
                    nodes[i][j].setCellValue(k, cellsValue.get(k));
                }

                filledNodeCounter += nodes[i][j].getValue() == 0 ? 0 : 1;
            }
        }
    }

    @Override
    public void clearPuzzle() {
        filledNodeCounter = 0;

        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                if (nodes[i][j].isEditable()) {
                    nodes[i][j].setValue(0);
                }

                nodes[i][j].getStateControler().setState(NormalNode.ID);

                nodes[i][j].clearCells(true);

                filledNodeCounter += nodes[i][j].getValue() == 0 ? 0 : 1;
            }
        }
    }

    @Override
    public void setInappropriate(SparseIntArray inappropriate) {
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                int index = PuzzleSolver.getIndex(i, j);
                if (inappropriate.get(index, -1) >= 0) {
                    nodes[i][j].getStateControler().setState(RelatedNode.ID);
                } else {
                    nodes[i][j].getStateControler().setState(NormalNode.ID);
                }
            }
        }
    }

    @Override
    public int[][] getValues() {
        int[][] ret = new int[Config.SUDOKU_SIZE][Config.SUDOKU_SIZE];
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                ret[i][j] = nodes[i][j].getValue();
            }
        }
        return ret;
    }

    @Override
    public int[][] getPuzzleValues() {
        int[][] ret = new int[Config.SUDOKU_SIZE][Config.SUDOKU_SIZE];
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                ret[i][j] = nodes[i][j].getPuzzleValue();
            }
        }
        return ret;
    }

    @Override
    public NodeCache[][] getNodesCache() {
        NodeCache[][] ret = new NodeCache[Config.SUDOKU_SIZE][Config.SUDOKU_SIZE];
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                ret[i][j] = new NodeCache();
                ret[i][j].setPuzzleValue(nodes[i][j].getPuzzleValue());
                ret[i][j].setValue(nodes[i][j].getValue());
                ret[i][j].setEditable(nodes[i][j].isEditable());

                ArrayList<Integer> cellsValue = new ArrayList<Integer>();
                ArrayList<Cell> cells = nodes[i][j].getCells();
                for (int index = 0; index < Node.CELLS_COUNT; index++) {
                    cellsValue.add(cells.get(index).getValue());
                }

                ret[i][j].setCellsValue(cellsValue);
            }
        }
        return ret;
    }

    private Node getTouchingNode(int x, int y) {
        Node ret = null;

        // searching in vertical
        int c = -1;
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            if (y <= nodes[i][0].bottom) {
                c = i;
                break;
            }
        }

        // searching in horizontal
        int r = -1;
        if (c != -1) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                if (x <= nodes[c][j].right) {
                    r = j;
                    break;
                }
            }
        }

        if (c != -1 && r != -1) {
            ret = nodes[c][r];
        }

        return ret;
    }

    public void onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setOnTouchDown(x, y);
                setOnTouching(x, y);

                nodeOnTouchDown = getTouchingNode(onTouchDown.x, onTouchDown.y);

                if (nodeOnTouchUp != null && nodeOnTouchUp.isEditable()) {
                    if (nodeOnTouchDown != nodeOnTouchUp) {
                        nodeOnTouchUp.setCellMode(false);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                setOnTouching(x, y);
                setOnTouchUp(x, y);

                Node nodeOnTouchUpThisTime = getTouchingNode(onTouchUp.x, onTouchUp.y);
                if (nodeOnTouchUp != null && nodeOnTouchUp.isEditable()) {
                    if (nodeOnTouchUp == nodeOnTouchUpThisTime) {
                        if (nodeOnTouchDown != null && nodeOnTouchDown == nodeOnTouchUp) {
                            nodeOnTouchUp.trigger();
                        }
                    } else {
                        nodeOnTouchUp.setCellMode(false);
                        nodeOnTouchUp = nodeOnTouchUpThisTime;
                    }
                } else {
                    nodeOnTouchUp = nodeOnTouchUpThisTime;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                setOnTouching(x, y);
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }
    }

    @Override
    public void update() {
        boolean changed = false;
        if (onTouching != null) {
            Node node = getTouchingNode(onTouching.x, onTouching.y);
            if (node != null && node != mNode) {
                changed = true;
                mNode = node;
            }

            if (mNode != null) {
                refreshNodes();
            }
        }

        if (changed) {
            if (nodeOnTouchUp != null && nodeOnTouchUp.isEditable()) {
                nodeOnTouchUp.setCellMode(false);
                nodeOnTouchUp = null;
            }
        }
    }

    public void refreshNodes() {
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                boolean sameI = (i == mNode.i);
                boolean sameJ = (j == mNode.j);
                boolean sameBlock = (PuzzleSolver.getBlock(i, j) == PuzzleSolver.getBlock(mNode.i, mNode.j));
                boolean sameValue = (mNode.getValue() == nodes[i][j].getValue());

                NodeStateControler stateControler = nodes[i][j].getStateControler();
                if (sameI && sameJ) {
                    stateControler.setState(SelectedNode.ID);
                } else {
                    int stateId = stateControler.getState().getId();
                    if (mNode.getValue() == 0) {
                        if (stateId == SameValueNode.ID || (stateId == SelectedNode.ID && nodes[i][j].getValue() != 0)) {
                            stateControler.setState(SameValueNode.ID);
                            if (sameI || sameJ || sameBlock) {
                                stateControler.getState().setBackgroundColor(
                                        nodes[i][j].isEditable() ? Pref.RELATED_NODE_BACKGROUND_COLOR_EDITABLE : Pref.RELATED_NODE_BACKGROUND_COLOR_NONEDITABLE);
                            } else {
                                stateControler.getState().setBackgroundColor(
                                        nodes[i][j].isEditable() ? Pref.NORMAL_NODE_BACKGROUND_COLOR_EDITABLE : Pref.NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE);
                            }
                        } else {
                            if (sameI || sameJ || sameBlock) {
                                stateControler.setState(RelatedNode.ID);
                            } else {
                                stateControler.setState(NormalNode.ID);
                            }
                        }
                    } else {
                        if (sameValue) {
                            stateControler.setState(SameValueNode.ID);
                            if (sameI || sameJ || sameBlock) {
                                stateControler.getState().setBackgroundColor(
                                        nodes[i][j].isEditable() ? Pref.RELATED_NODE_BACKGROUND_COLOR_EDITABLE : Pref.RELATED_NODE_BACKGROUND_COLOR_NONEDITABLE);
                            } else {
                                stateControler.getState().setBackgroundColor(
                                        nodes[i][j].isEditable() ? Pref.NORMAL_NODE_BACKGROUND_COLOR_EDITABLE : Pref.NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE);
                            }
                        } else {
                            if (sameI || sameJ || sameBlock) {
                                stateControler.setState(RelatedNode.ID);
                            } else {
                                stateControler.setState(NormalNode.ID);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        renderBackground(canvas, paint, Pref.PUZZLE_BACKGROUND_COLOR);
        renderNodes(canvas, paint);
    }

    public void renderNodes(Canvas canvas, Paint paint) {
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                nodes[i][j].update();
                nodes[i][j].render(canvas, paint);
            }
        }
    }

    @Override
    public void set(int left, int top, int right, int bottom) {
        super.set(left, top, right, bottom);

        int nodeWidth = (right
                - left
                - ((1 + Config.SUDOKU_BLOCK_SIZE) * Pref.PUZZLE_BLOCKS_SEPARATOR_WIDTH)
                - ((Config.SUDOKU_BLOCK_SIZE - 1) * Config.SUDOKU_BLOCK_SIZE)
                * Pref.PUZZLE_NODES_SEPARATOR_WIDTH)
                / Config.SUDOKU_SIZE;

        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                int offsetX = (1 + j / Config.SUDOKU_BLOCK_SIZE)
                        * Pref.PUZZLE_BLOCKS_SEPARATOR_WIDTH
                        + (j - j / Config.SUDOKU_BLOCK_SIZE)
                        * Pref.PUZZLE_NODES_SEPARATOR_WIDTH;
                int offsetY = (1 + i / Config.SUDOKU_BLOCK_SIZE)
                        * Pref.PUZZLE_BLOCKS_SEPARATOR_WIDTH
                        + (i - i / Config.SUDOKU_BLOCK_SIZE)
                        * Pref.PUZZLE_NODES_SEPARATOR_WIDTH;

                int x = left + j * nodeWidth + offsetX;
                int y = top + i * nodeWidth + offsetY;

                if (nodes[i][j] == null) {
                    nodes[i][j] = new Node(i, j);
                }
                nodes[i][j].set(x, y, x + nodeWidth, y + nodeWidth);
            }
        }
    }

    private void setOnTouching(int x, int y) {
        if (onTouching == null) {
            onTouching = new Point();
        }
        onTouching.set(x, y);
    }

    private void setOnTouchDown(int x, int y) {
        if (onTouchDown == null) {
            onTouchDown = new Point();
        }
        onTouchDown.set(x, y);
    }

    private void setOnTouchUp(int x, int y) {
        if (onTouchUp == null) {
            onTouchUp = new Point();
        }
        onTouchUp.set(x, y);
    }

    public Node getNode(int i, int j) {
        return nodes[i][j];
    }

    public Node getNode() {
        return mNode;
    }

    public void reset() {
        if (mNode != null && mNode.isCellMode()) {
            mNode.setCellMode(false);
        }
        onTouching = null;
        mNode = null;
    }

    public int getFilledNodeCounter() {
        return filledNodeCounter;
    }

    public void setFilledNodeCounter(int filledNodeCounter) {
        this.filledNodeCounter = filledNodeCounter;
    }
}
