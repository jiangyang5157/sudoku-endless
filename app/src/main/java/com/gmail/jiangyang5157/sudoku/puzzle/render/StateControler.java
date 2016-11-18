package com.gmail.jiangyang5157.sudoku.puzzle.render;

import java.util.HashMap;

public abstract class StateControler {
    private HashMap<Integer, State> states = null;

    private State state = null;

    public abstract HashMap<Integer, State> getStates();

    public StateControler() {
        states = getStates();
    }

    public State getState() {
        return state;
    }

    public void setState(int id) {
        this.state = states.get(id);
    }
}
