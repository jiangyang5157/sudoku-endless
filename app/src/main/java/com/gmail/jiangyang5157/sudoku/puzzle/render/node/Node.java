package com.gmail.jiangyang5157.sudoku.puzzle.render.node;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.SparseIntArray;

import com.gmail.jiangyang5157.sudoku.puzzle.render.EditableComponent;
import com.gmail.jiangyang5157.sudoku.puzzle.render.cell.Cell;
import com.gmail.jiangyang5157.sudoku.puzzle.render.cell.NormalCell;
import com.gmail.jiangyang5157.sudoku.puzzle.render.cell.SelectedCell;

import java.util.ArrayList;

/**
 * Created by 洋 on 2014/12/13.
 */
public class Node extends EditableComponent {

    private NodeStateControler stateControler = null;

    public int i = 0;
    public int j = 0;

    private int puzzleValue = 0;

    private boolean cellMode = false;

    public static final int CELLS_COUNT = 4;

    private ArrayList<Cell> cells = null;
    private SparseIntArray cellsIndex = null;

    public Node(int i, int j) {
        super();

        this.i = i;
        this.j = j;

        stateControler = new NodeStateControler();
        stateControler.setState(NormalNode.ID);

        cells = new ArrayList<Cell>(CELLS_COUNT);
        cellsIndex = new SparseIntArray(CELLS_COUNT);

        for (int k = 0; k < CELLS_COUNT; k++) {
            Cell cell = new Cell();
            cell.setEditable(true);
            cells.add(k, cell);
        }
    }

    @Override
    public void update() {
        stateControler.getState().update(this);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        stateControler.getState().render(this, canvas, paint);
        renderCells(canvas, paint);
    }

    private void renderCells(Canvas canvas, Paint paint) {
        for (int i = 0; i < CELLS_COUNT; i++) {
            Cell cell = cells.get(i);
            cell.update();
            cell.render(canvas, paint);
        }
    }

    private void inputCellValue(int number) {
        int containerkey = cellsIndex.get(number, -1);
        if (containerkey == -1) {
            for (int i = 0; i < CELLS_COUNT; i++) {
                Cell cell = cells.get(i);
                if (cell.getValue() == 0) {
                    setCellValue(i, number);
                    break;
                }
            }
        } else {
            cells.get(containerkey).onErase();
            cellsIndex.delete(number);
        }
    }

    public void setCellValue(int index, int number) {
        cells.get(index).inputNumber(number);
        cellsIndex.put(number, index);
    }

    @Override
    public void set(int left, int top, int right, int bottom) {
        super.set(left, top, right, bottom);

        int cellWidth = (right - left) / 3;

        int size = cells.size();
        for (int i = 0; i < size; i++) {
            Cell cell = cells.get(i);
            Rect r = getCellRect(i, cellWidth);
            cell.set(r.left, r.top, r.right, r.bottom);
        }
    }

    private Rect getCellRect(int index, int cellWidth) {
        Rect ret = null;
        switch (index) {
            case 0:
                ret = new Rect();
                ret.left = left;
                ret.top = top;
                ret.right = left + cellWidth;
                ret.bottom = top + cellWidth;
                break;
            case 1:
                ret = new Rect();
                ret.left = right - cellWidth;
                ret.top = top;
                ret.right = right;
                ret.bottom = top + cellWidth;
                break;
            case 2:
                ret = new Rect();
                ret.left = right - cellWidth;
                ret.top = bottom - cellWidth;
                ret.right = right;
                ret.bottom = bottom;
                break;
            case 3:
                ret = new Rect();
                ret.left = left;
                ret.top = bottom - cellWidth;
                ret.right = left + cellWidth;
                ret.bottom = bottom;
                break;
            default:
                break;
        }

        return ret;
    }

    public boolean isCellMode() {
        return cellMode;
    }

    public void trigger() {
        setCellMode(!isCellMode());
    }

    public void setCellMode(boolean cellMode) {
        this.cellMode = cellMode;

        for (int i = 0; i < CELLS_COUNT; i++) {
            Cell cell = cells.get(i);
            cell.getStateControler().setState(cellMode ? SelectedCell.ID : NormalCell.ID);
        }
    }

    public void clearCells(boolean clearState) {
        cellsIndex.clear();
        for (int i = 0; i < CELLS_COUNT; i++) {
            Cell cell = cells.get(i);
            cell.onErase();

            if (clearState) {
                cell.getStateControler().setState(NormalCell.ID);
            }
        }
    }

    @Override
    public boolean inputNumber(int number) {
        boolean ret = false;

        if (isEditable()) {
            if (cellMode) {
                if (number == 0) {
                    clearCells(false);
                } else {
                    inputCellValue(number);
                }
            } else {
                setValue(number);
            }

            ret = true;
        }

        return ret;
    }

    public int getPuzzleValue() {
        return puzzleValue;
    }

    public void setPuzzleValue(int puzzleValue) {
        this.puzzleValue = puzzleValue;
    }

    public NodeStateControler getStateControler() {
        return stateControler;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }
}
