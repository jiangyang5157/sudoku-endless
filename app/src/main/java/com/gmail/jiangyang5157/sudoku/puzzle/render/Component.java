package com.gmail.jiangyang5157.sudoku.puzzle.render;

import android.graphics.Canvas;
import android.graphics.Paint;

public abstract class Component implements Renderable {

    public int left;
    public int top;
    public int right;
    public int bottom;

    public Component() {
        setEmpty();
    }

    public Component(int left, int top, int right, int bottom) {
        set(left, top, right, bottom);
    }

    public Component(Component r) {
        if (r == null) {
            setEmpty();
        } else {
            set(r.left, r.top, r.right, r.bottom);
        }
    }

    public boolean renderBackground(Canvas canvas, Paint paint, int backgroundColor) {
        paint.reset();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(left, top, right, bottom, paint);

        return true;
    }

    public boolean renderBorder(Canvas canvas, Paint paint, int borderWidth, int borderColor) {
        boolean ret = false;

        if (borderWidth > 0) {
            paint.reset();
            paint.setColor(borderColor);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(borderWidth < 0 ? 0 : borderWidth);
            canvas.drawRect(left, top, right, bottom, paint);

            ret = true;
        }

        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Component r = (Component) o;
        return left == r.left && top == r.top && right == r.right && bottom == r.bottom;
    }

    public final int width() {
        return right - left;
    }

    public final int height() {
        return bottom - top;
    }

    public final int centerX() {
        return (left + right) >> 1;
    }

    public final int centerY() {
        return (top + bottom) >> 1;
    }

    public final boolean isEmpty() {
        return left >= right || top >= bottom;
    }

    public void setEmpty() {
        left = right = top = bottom = 0;
    }

    /**
     * Set the rectangle's coordinates to the specified values. Note: no range
     * checking is performed, so it is up to the caller to ensure that
     * left <= right and top <= bottom.
     *
     * @param left   The X coordinate of the left side of the rectangle
     * @param top    The Y coordinate of the top of the rectangle
     * @param right  The X coordinate of the right side of the rectangle
     * @param bottom The Y coordinate of the bottom of the rectangle
     */
    public void set(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    /**
     * Offset the rectangle by adding dx to its left and right coordinates, and
     * adding dy to its top and bottom coordinates.
     *
     * @param dx The amount to add to the rectangle's left and right coordinates
     * @param dy The amount to add to the rectangle's top and bottom coordinates
     */
    public void offset(int dx, int dy) {
        left += dx;
        top += dy;
        right += dx;
        bottom += dy;
    }

    /**
     * Offset the rectangle to a specific (left, top) position,
     * keeping its width and height the same.
     *
     * @param newLeft The new "left" coordinate for the rectangle
     * @param newTop  The new "top" coordinate for the rectangle
     */
    public void offsetTo(int newLeft, int newTop) {
        right += newLeft - left;
        bottom += newTop - top;
        left = newLeft;
        top = newTop;
    }

    public boolean contains(int x, int y) {
        return !isEmpty()
                && x >= left && x < right && y >= top && y < bottom;
    }

    public boolean contains(int left, int top, int right, int bottom) {
        return !isEmpty()
                && this.left <= left && this.top <= top
                && this.right >= right && this.bottom >= bottom;
    }

    public boolean contains(Component r) {
        return contains(r.left, r.top, r.right, r.bottom);
    }

    /**
     * Scales up the rect by the given scale.
     *
     * @hide
     */
    public void scale(float scale) {
        if (scale != 1.0f) {
            left = (int) (left * scale + 0.5f);
            top = (int) (top * scale + 0.5f);
            right = (int) (right * scale + 0.5f);
            bottom = (int) (bottom * scale + 0.5f);
        }
    }

    /**
     * Scales up the rect by the given scale, rounding values toward the inside.
     *
     * @hide
     */
    public void scaleRoundIn(float scale) {
        if (scale != 1.0f) {
            left = (int) Math.ceil(left * scale);
            top = (int) Math.ceil(top * scale);
            right = (int) Math.floor(right * scale);
            bottom = (int) Math.floor(bottom * scale);
        }
    }


    @Override
    public int hashCode() {
        int result = left;
        result = 31 * result + top;
        result = 31 * result + right;
        result = 31 * result + bottom;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(32);
        sb.append("Rect(");
        sb.append(left);
        sb.append(", ");
        sb.append(top);
        sb.append(" - ");
        sb.append(right);
        sb.append(", ");
        sb.append(bottom);
        sb.append(")");
        return sb.toString();
    }
}
