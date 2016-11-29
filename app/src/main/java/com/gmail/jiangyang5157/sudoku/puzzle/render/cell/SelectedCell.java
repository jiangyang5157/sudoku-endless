package com.gmail.jiangyang5157.sudoku.puzzle.render.cell;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.gmail.jiangyang5157.sudoku.SudokuAppUtils;
import com.gmail.jiangyang5157.sudoku.puzzle.render.Component;
import com.gmail.jiangyang5157.sudoku.puzzle.render.State;

public class SelectedCell extends State {

    public static final int ID = 1;

    public SelectedCell() {
        setId(ID);
    }

    @Override
    public void update(Component component) {

    }

    @Override
    public void render(Component component, Canvas canvas, Paint paint) {
        component.renderBorder(canvas, paint, SudokuAppUtils.SELECTED_CELL_BORDER_WIDTH, SudokuAppUtils.SELECTED_CELL_BORDER_COLOR);

        Cell cell = (Cell) component;
        cell.renderValue(canvas, paint, SudokuAppUtils.SELECTED_CELL_TEXT_COLOR, component.width());
    }
}
