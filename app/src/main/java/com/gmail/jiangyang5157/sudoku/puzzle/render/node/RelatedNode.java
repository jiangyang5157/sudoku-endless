package com.gmail.jiangyang5157.sudoku.puzzle.render.node;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.gmail.jiangyang5157.sudoku.SudokuAppUtils;
import com.gmail.jiangyang5157.sudoku.puzzle.render.Component;
import com.gmail.jiangyang5157.sudoku.puzzle.render.State;

public class RelatedNode extends State {

    public static final int ID = 2;

    public RelatedNode() {
        setId(ID);
    }

    @Override
    public void update(Component component) {
        Node node = (Node) component;
        if (node.isEditable()) {
            setBackgroundColor(SudokuAppUtils.RELATED_NODE_BACKGROUND_COLOR_EDITABLE);
            setBorderColor(SudokuAppUtils.RELATED_NODE_BORDER_COLOR_EDITABLE);
            setBorderWidth(SudokuAppUtils.RELATED_NODE_BORDER_WIDTH_EDITABLE);
        } else {
            setBackgroundColor(SudokuAppUtils.RELATED_NODE_BACKGROUND_COLOR_NONEDITABLE);
            setBorderColor(SudokuAppUtils.RELATED_NODE_BORDER_COLOR_NONEDITABLE);
            setBorderWidth(SudokuAppUtils.RELATED_NODE_BORDER_WIDTH_NONEDITABLE);
        }
    }

    @Override
    public void render(Component component, Canvas canvas, Paint paint) {
        component.renderBackground(canvas, paint, getBackgroundColor());
        component.renderBorder(canvas, paint, getBorderWidth(), getBorderColor());

        Node node = (Node) component;
        node.renderValue(canvas, paint, SudokuAppUtils.RELATED_NODE_TEXT_COLOR, component.width() * SudokuAppUtils.RELATED_NODE_TEXT_SCALE);
    }
}
