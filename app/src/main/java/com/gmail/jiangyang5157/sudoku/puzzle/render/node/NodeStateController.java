package com.gmail.jiangyang5157.sudoku.puzzle.render.node;

import com.gmail.jiangyang5157.sudoku.puzzle.render.State;
import com.gmail.jiangyang5157.sudoku.puzzle.render.StateController;

import java.util.HashMap;

public class NodeStateController extends StateController {

    @Override
    public HashMap<Integer, State> getStates() {
        HashMap<Integer, State> states = new HashMap<Integer, State>();
        states.put(NormalNode.ID, new NormalNode());
        states.put(SelectedNode.ID, new SelectedNode());
        states.put(RelatedNode.ID, new RelatedNode());
        states.put(SameValueNode.ID, new SameValueNode());
        return states;
    }
}
