package com.gmail.jiangyang5157.sudoku.ui.puzzle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.gmail.jiangyang5157.sudoku.SudokuAppUtils;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.component.BaseFragment;

public abstract class BasePuzzleFragment extends BaseFragment implements PuzzleView.Listener {

    protected PuzzleView puzzleView = null;

    public static final String KEY_ROWID = "KEY_ROWID";
    public static final String KEY_NODES_CACHE = "KEY_NODES_CACHE";
    public static final String KEY_LEVEL_CACHE = "KEY_LEVEL_CACHE";
    public static final String KEY_TIMER = "KEY_TIMER";
    public static final String KEY_BEST_TIME = "KEY_BEST_TIME";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SudokuAppUtils.fetchColors(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_puzzle, container, false);
    }

    public abstract void onPuzzleViewCreated(Bundle savedInstanceState);

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        puzzleView = new PuzzleView(getActivity());
        puzzleView.setListener(this);

        FrameLayout container = (FrameLayout) getView().findViewById(R.id.container);
        container.addView(puzzleView);

        Bundle bundle = savedInstanceState == null ? getArguments() : savedInstanceState;
        if (bundle == null) {
            throw new IllegalArgumentException("[bundle] must not be null");
        }

        onPuzzleViewCreated(bundle);
    }

    public boolean validate(boolean highlight) {
        return puzzleView.validate(highlight);
    }

    @Override
    public void onCompleted() {
    }
}
