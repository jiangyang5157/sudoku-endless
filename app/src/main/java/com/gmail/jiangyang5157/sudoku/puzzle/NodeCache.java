package com.gmail.jiangyang5157.sudoku.puzzle;

import java.io.Serializable;
import java.util.ArrayList;

public class NodeCache implements Serializable {

    private static final long serialVersionUID = 2305843009213693953L;

    private int puzzleValue = 0;
    private int value = 0;
    private boolean editable = false;
    private ArrayList<Integer> cellsValue = null;

    public NodeCache() {
    }

    public NodeCache(int puzzleValue, int value, boolean editable) {
        setPuzzleValue(puzzleValue);
        setValue(value);
        setEditable(editable);
    }

    public NodeCache(int puzzleValue, int value, boolean editable, ArrayList<Integer> cellsValue) {
        setPuzzleValue(puzzleValue);
        setValue(value);
        setEditable(editable);
        setCellsValue(cellsValue);
    }

    public int getPuzzleValue() {
        return puzzleValue;
    }

    public void setPuzzleValue(int puzzleValue) {
        this.puzzleValue = puzzleValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public ArrayList<Integer> getCellsValue() {
        return cellsValue;
    }

    public void setCellsValue(ArrayList<Integer> cellsValue) {
        this.cellsValue = cellsValue;
    }
}