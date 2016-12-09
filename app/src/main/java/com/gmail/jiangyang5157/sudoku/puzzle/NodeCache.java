package com.gmail.jiangyang5157.sudoku.puzzle;

import java.io.Serializable;
import java.util.ArrayList;

public class NodeCache implements Serializable {

    private static final long serialVersionUID = 2305843009213693953L;

    private int puzzleValue = 0;

    private int value = 0;

    // Indicate this value is editable or not. eg: for restart usage
    private boolean editable = false;

    private ArrayList<Integer> cellsValue = null;

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

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append("NodeCache[");
        ret.append("puzzleValue=").append(puzzleValue).append(",");
        ret.append("value=").append(value).append(",");
        ret.append("editable=").append(editable).append(",");
        ret.append("cellsValue=").append(cellsValue);
        ret.append("]");
        return ret.toString();
    }
}