package com.gmail.jiangyang5157.sudoku.component.timer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.gmail.jiangyang5157.kotlin_core.utils.TimeUtils;
import com.gmail.jiangyang5157.sudoku.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * User: Yang
 * Date: 2014/9/5
 * Time: 12:55
 */
public class TimerViewImpl extends FrameLayout implements TimerView {

    private NumberView mSecondOnes = null;
    private NumberView mSecondTens = null;
    private NumberView mMinuteOnes = null;
    private NumberView mMinuteTens = null;
    private NumberView mHourOnes = null;
    private NumberView mHourTens = null;

    private Timer mTimer = null;

    private int mTime = 0;

    private boolean mStarted = false;

    public TimerViewImpl(Context context) {
        this(context, null, 0);
    }

    public TimerViewImpl(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimerViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setupViews();
        initialization();
    }

    private void setupViews() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.timer_view, null, false);
        this.addView(view);

        TextView tvHourColonMinute = (TextView) findViewById(R.id.tv_hour_colon_minute);
        TextView tvMinuteColonSecond = (TextView) findViewById(R.id.tv_minute_colon_second);

        mSecondOnes = (NumberView) findViewById(R.id.nv_second_ones);
        mSecondTens = (NumberView) findViewById(R.id.nv_second_tens);
        mMinuteOnes = (NumberView) findViewById(R.id.nv_minute_ones);
        mMinuteTens = (NumberView) findViewById(R.id.nv_minute_tens);
        mHourOnes = (NumberView) findViewById(R.id.nv_hour_ones);
        mHourTens = (NumberView) findViewById(R.id.nv_hour_tens);

        mSecondTens.setSequence(new int[]{0, 1, 2, 3, 4, 5});
        mMinuteTens.setSequence(new int[]{0, 1, 2, 3, 4, 5});
    }

    private void initialization() {
        mTime = 0;
    }

    private void update() {
        mSecondOnes.advance(mTime % 10);
        mSecondTens.advance((mTime / 10) % 6);
        mMinuteOnes.advance((mTime / 60) % 10);
        mMinuteTens.advance((mTime / 600) % 6);
        mHourOnes.advance((mTime / 3600) % 10);
        mHourTens.advance((mTime / 36000) % 10);
    }

    @Override
    public void start() {
        if (!mStarted) {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new UpdateTask(), 0, TimeUtils.INSTANCE.getMILLI_IN_SECOND());
        }
        mStarted = true;
    }

    @Override
    public void start(int time) {
        setTime(time, false);
        start();
    }

    @Override
    public void pause() {
        if (mStarted) {
            mTimer.cancel();
            mTimer.purge();
        }
        mStarted = false;
    }

    @Override
    public void reset() {
        pause();
        setTime(0, true);
    }

    @Override
    public int getTime() {
        return mTime;
    }

    @Override
    public void setTime(int time, boolean update) {
        this.mTime = time;
        if (!mStarted && update) {
            update();
        }
    }

    private class UpdateTask extends TimerTask {
        @Override
        public void run() {
            mTime++;
            update();
        }
    }
}
