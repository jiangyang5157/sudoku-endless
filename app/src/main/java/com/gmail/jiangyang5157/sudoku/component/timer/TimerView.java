package com.gmail.jiangyang5157.sudoku.component.timer;

public interface TimerView {
    int getTime();

    void setTime(int mTime, boolean update);

    void reset();

    void pause();

    void start();

    void start(int mTime);
}
