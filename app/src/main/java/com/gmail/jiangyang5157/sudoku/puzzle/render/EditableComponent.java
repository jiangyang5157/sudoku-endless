package com.gmail.jiangyang5157.sudoku.puzzle.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.gmail.jiangyang5157.sudoku.ui.puzzle.KeypadFragment;

public abstract class EditableComponent extends Component implements KeypadFragment.Listener {

    private int value = 0;
    private boolean editable = true;

    public EditableComponent() {
        super();
    }

    public EditableComponent(int left, int top, int right, int bottom) {
        super(left, top, right, bottom);
    }

    public boolean renderValue(Canvas canvas, Paint paint, int textColor, float textSize) {
        boolean ret = false;

        if (getValue() > 0) {
            paint.reset();
            paint.setAntiAlias(true);

            paint.setColor(textColor);
            paint.setTextSize(textSize);
            paint.setTypeface(Typeface.SANS_SERIF);

            paint.setTextAlign(Paint.Align.CENTER);

            String text = String.valueOf(value);
            Rect textBounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), textBounds);
            int textHeight = textBounds.bottom - textBounds.top;
            canvas.drawText(text, centerX(), centerY() + textHeight / 2, paint);

            ret = true;
        }

        return ret;
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

    @Override
    public boolean inputNumber(int number) {
        boolean ret = false;
        if (isEditable()) {
            setValue(number);
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean onErase() {
        return inputNumber(0);
    }
}
