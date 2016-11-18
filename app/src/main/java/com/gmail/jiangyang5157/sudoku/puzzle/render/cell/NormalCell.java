package com.gmail.jiangyang5157.sudoku.puzzle.render.cell;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.gmail.jiangyang5157.sudoku.Pref;
import com.gmail.jiangyang5157.sudoku.puzzle.render.Component;
import com.gmail.jiangyang5157.sudoku.puzzle.render.State;

public class NormalCell extends State {

    public static final int ID = 0;

    public NormalCell() {
        setId(ID);
    }

    @Override
    public void update(Component component) {

    }

    @Override
    public void render(Component component, Canvas canvas,
                       Paint paint) {
        Cell cell = (Cell) component;
        cell.renderValue(canvas, paint, Pref.NORMAL_CELL_TEXT_COLOR, (component.width()) / 5 * 4);
    }
}
