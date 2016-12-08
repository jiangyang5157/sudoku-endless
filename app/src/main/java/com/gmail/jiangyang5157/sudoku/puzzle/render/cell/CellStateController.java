package com.gmail.jiangyang5157.sudoku.puzzle.render.cell;

import com.gmail.jiangyang5157.sudoku.puzzle.render.State;
import com.gmail.jiangyang5157.sudoku.puzzle.render.StateController;

import java.util.HashMap;

public class CellStateController extends StateController {

    @Override
    public HashMap<Integer, State> getStates() {
        HashMap<Integer, State> states = new HashMap<>();
        states.put(NormalCell.ID, new NormalCell());
        states.put(SelectedCell.ID, new SelectedCell());
        return states;
    }
}
