package com.gmail.jiangyang5157.sudoku.puzzle.render.node;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.gmail.jiangyang5157.sudoku.Pref;
import com.gmail.jiangyang5157.sudoku.puzzle.render.Component;
import com.gmail.jiangyang5157.sudoku.puzzle.render.State;

public class NormalNode extends State {

    public static final int ID = 0;

    public NormalNode() {
        setId(ID);
    }

    @Override
    public void update(Component component) {
        Node node = (Node) component;
        if (node.isEditable()) {
            setBackgroundColor(Pref.NORMAL_NODE_BACKGROUND_COLOR_EDITABLE);
            setBorderColor(Pref.NORMAL_NODE_BORDER_COLOR_EDITABLE);
            setBorderWidth(Pref.NORMAL_NODE_BORDER_WIDTH_EDITABLE);
        } else {
            setBackgroundColor(Pref.NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE);
            setBorderColor(Pref.NORMAL_NODE_BORDER_COLOR_NONEDITABLE);
            setBorderWidth(Pref.NORMAL_NODE_BORDER_WIDTH_NONEDITABLE);
        }
    }

    @Override
    public void render(Component component, Canvas canvas, Paint paint) {
        component.renderBackground(canvas, paint, getBackgroundColor());
        component.renderBorder(canvas, paint, getBorderWidth(), getBorderColor());

        Node node = (Node) component;
        node.renderValue(canvas, paint, Pref.NORMAL_NODE_TEXT_COLOR, component.width() * Pref.NORMAL_NODE_TEXT_SCALE);
    }
}
