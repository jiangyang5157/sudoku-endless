package com.gmail.jiangyang5157.sudoku.component.view;

import com.gmail.jiangyang5157.sudoku.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class GradientsLayout extends BaseFrameLayout {

    public interface Listener {
        public void onGradientsFinish(GradientsLayout gradientsLayout);
    }

    private Listener listener = null;

    private final int DELAY_REFRESH = 2;
    private int transparenceSrength = 20;

    private int transparenceMinium = 55;
    private int transparenceMax = 255;
    private int transparenceCurrent = transparenceMax;

    private boolean interceptTouchEvent = false;

    public GradientsLayout(Context context) {
        this(context, null, 0);
    }

    public GradientsLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradientsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.GradientsLayout);
        transparenceSrength = typedArray.getInteger(
                R.styleable.GradientsLayout_transparence_strength,
                transparenceSrength);
        transparenceMinium = typedArray.getInteger(
                R.styleable.GradientsLayout_transparence_minium,
                transparenceMinium);
        transparenceMax = typedArray.getInteger(
                R.styleable.GradientsLayout_transparence_max,
                transparenceMax);
        interceptTouchEvent = typedArray.getBoolean(
                R.styleable.GradientsLayout_intercept_touch_event,
                interceptTouchEvent);
        typedArray.recycle();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (getBackground() != null) {
            int what = this.isPressed() ? -transparenceSrength : transparenceSrength;
            handler.removeMessages(-what);
            handler.sendEmptyMessage(what);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            int what = message.what;
            transparenceCurrent += what;

            if (transparenceCurrent >= transparenceMinium && transparenceCurrent <= transparenceMax) {
                getBackground().setAlpha(transparenceCurrent);

                handler.sendEmptyMessageDelayed(what, DELAY_REFRESH);
            } else {
                transparenceCurrent = (transparenceCurrent > transparenceMinium) ? transparenceCurrent : transparenceMinium;
                transparenceCurrent = (transparenceCurrent < transparenceMax) ? transparenceCurrent : transparenceMax;
                getBackground().setAlpha(transparenceCurrent);

                if (transparenceCurrent == transparenceMax) {
                    if (listener != null) {
                        listener.onGradientsFinish(GradientsLayout.this);
                    }
                }
            }
        }
    };

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return interceptTouchEvent;
    }

    public Listener getListener() {
        return listener;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public int getTransparenceMinium() {
        return transparenceMinium;
    }

    public void setTransparenceMinium(int transparenceMinium) {
        this.transparenceMinium = transparenceMinium;
    }

    public int getTransparenceMax() {
        return transparenceMax;
    }

    public void setTransparenceMax(int transparenceMax) {
        this.transparenceMax = transparenceMax;
    }

    public int getTransparenceSrength() {
        return transparenceSrength;
    }

    public void setTransparenceSrength(int transparenceSrength) {
        this.transparenceSrength = transparenceSrength;
    }

    public boolean isInterceptTouchEvent() {
        return interceptTouchEvent;
    }

    public void setInterceptTouchEvent(boolean interceptTouchEvent) {
        this.interceptTouchEvent = interceptTouchEvent;
    }
}
