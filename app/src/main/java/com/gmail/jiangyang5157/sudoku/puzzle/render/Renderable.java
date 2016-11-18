package com.gmail.jiangyang5157.sudoku.puzzle.render;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Renderable {
    public void update();

    public void render(Canvas canvas, Paint paint);
}
