package com.gmail.jiangyang5157.sudoku.puzzle.render.node;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.gmail.jiangyang5157.sudoku.Pref;
import com.gmail.jiangyang5157.sudoku.puzzle.render.Component;
import com.gmail.jiangyang5157.sudoku.puzzle.render.State;

public class SameValueNode extends State {

    public static final int ID = 3;

    public SameValueNode() {
        setId(ID);
    }

    @Override
    public void update(Component component) {
        Node node = (Node) component;
        if (node.isEditable()) {
            setBorderColor(Pref.SAME_VALUE_NODE_BORDER_COLOR_EDITABLE);
            setBorderWidth(Pref.SAME_VALUE_NODE_BORDER_WIDTH_EDITABLE);
        } else {
            setBorderColor(Pref.SAME_VALUE_NODE_BORDER_COLOR_NONEDITABLE);
            setBorderWidth(Pref.SAME_VALUE_NODE_BORDER_WIDTH_NONEDITABLE);
        }
    }

    @Override
    public void render(Component component, Canvas canvas, Paint paint) {
        component.renderBackground(canvas, paint, getBackgroundColor());
        component.renderBorder(canvas, paint, getBorderWidth(),
                getBorderColor());

        Node node = (Node) component;
        node.renderValue(canvas, paint, Pref.SAME_VALUE_NODE_TEXT_COLOR, component.width() * Pref.SAME_VALUE_NODE_TEXT_SCALE);
    }
}
