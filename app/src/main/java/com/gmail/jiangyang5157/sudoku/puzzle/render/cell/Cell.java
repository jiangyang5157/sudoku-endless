package com.gmail.jiangyang5157.sudoku.puzzle.render.cell;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.gmail.jiangyang5157.sudoku.puzzle.render.EditableComponent;

public class Cell extends EditableComponent {

    private CellStateController stateControler = null;

    public Cell() {
        super();

        stateControler = new CellStateController();
        stateControler.setState(NormalCell.ID);
    }

    @Override
    public void update() {
        stateControler.getState().update(this);
    }

    @Override
    public void render(Canvas canvas, Paint paint) {
        stateControler.getState().render(this, canvas, paint);
    }

    public CellStateController getStateControler() {
        return stateControler;
    }
}
