package com.gmail.jiangyang5157.sudoku.puzzle.render;

import android.graphics.Canvas;
import android.graphics.Paint;

public interface Renderable {

    void update();

    void render(Canvas canvas, Paint paint);
}
