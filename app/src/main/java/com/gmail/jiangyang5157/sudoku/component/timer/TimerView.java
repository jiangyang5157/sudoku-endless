package com.gmail.jiangyang5157.sudoku.component.timer;

public interface TimerView {
    public int getTime();

    public void setTime(int mTime, boolean update);

    public void reset();

    public void pause();

    public void start();

    public void start(int mTime);
}
