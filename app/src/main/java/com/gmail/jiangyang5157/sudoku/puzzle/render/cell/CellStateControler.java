package com.gmail.jiangyang5157.sudoku.puzzle.render.cell;

import com.gmail.jiangyang5157.sudoku.puzzle.render.State;
import com.gmail.jiangyang5157.sudoku.puzzle.render.StateControler;

import java.util.HashMap;

public class CellStateControler extends StateControler {

    @Override
    public HashMap<Integer, State> getStates() {
        HashMap<Integer, State> states = new HashMap<Integer, State>();
        states.put(NormalCell.ID, new NormalCell());
        states.put(SelectedCell.ID, new SelectedCell());
        return states;
    }
}
