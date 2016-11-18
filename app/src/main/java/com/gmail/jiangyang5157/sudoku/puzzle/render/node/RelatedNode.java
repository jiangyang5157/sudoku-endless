package com.gmail.jiangyang5157.sudoku.puzzle.render.node;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.gmail.jiangyang5157.sudoku.Pref;
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
            setBackgroundColor(Pref.RELATED_NODE_BACKGROUND_COLOR_EDITABLE);
            setBorderColor(Pref.RELATED_NODE_BORDER_COLOR_EDITABLE);
            setBorderWidth(Pref.RELATED_NODE_BORDER_WIDTH_EDITABLE);
        } else {
            setBackgroundColor(Pref.RELATED_NODE_BACKGROUND_COLOR_NONEDITABLE);
            setBorderColor(Pref.RELATED_NODE_BORDER_COLOR_NONEDITABLE);
            setBorderWidth(Pref.RELATED_NODE_BORDER_WIDTH_NONEDITABLE);
        }
    }

    @Override
    public void render(Component component, Canvas canvas, Paint paint) {
        component.renderBackground(canvas, paint, getBackgroundColor());
        component.renderBorder(canvas, paint, getBorderWidth(), getBorderColor());

        Node node = (Node) component;
        node.renderValue(canvas, paint, Pref.RELATED_NODE_TEXT_COLOR, component.width() * Pref.RELATED_NODE_TEXT_SCALE);
    }
}
