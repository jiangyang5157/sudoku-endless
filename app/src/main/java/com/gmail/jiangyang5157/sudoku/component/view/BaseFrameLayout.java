package com.gmail.jiangyang5157.sudoku.component.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.gmail.jiangyang5157.sudoku.R;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 21:57
 */
public class BaseFrameLayout extends FrameLayout {

    private boolean square = false;

    public BaseFrameLayout(Context context) {
        this(context, null, 0);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseFrameLayout, 0, 0);
        square = typedArray.getBoolean(R.styleable.BaseFrameLayout_square, false);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (square) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            int size = height > width ? width : height;
            super.onMeasure(
                    MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY));
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public boolean isSquare() {
        return square;
    }
}
