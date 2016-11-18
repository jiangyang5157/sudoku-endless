package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.os.Bundle;

import com.gmail.jiangyang5157.sudoku.puzzle.*;
import com.gmail.jiangyang5157.sudoku.ui.puzzle.BasePuzzleFragment;

public class ColorPalettesPuzzleFragment extends BasePuzzleFragment {

    public static final String FRAGMENT_TAG = "color_palettes_puzzle_fragment";

    private NodeCache[][] mNodesCache = null;

    @Override
    public void onPuzzleViewCreated(Bundle savedInstanceState) {
        mNodesCache = (NodeCache[][]) savedInstanceState.get(BasePuzzleFragment.KEY_NODES_CACHE);

        if (mNodesCache == null) {
            throw new IllegalArgumentException("[NodesCache] must not be null");
        }

        puzzleView.generatePuzzle(mNodesCache);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //save transient data for configuration change
        outState.putSerializable(BasePuzzleFragment.KEY_NODES_CACHE, mNodesCache);
        super.onSaveInstanceState(outState);
    }

    public void refreshPuzzleView() {
        puzzleView.refresh();
    }
}
