package com.gmail.jiangyang5157.sudoku.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jiangyang5157.sudoku.component.BaseFragment;
import com.gmail.jiangyang5157.sudoku.component.view.IconButton;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.puzzle.Difficulty;
import com.gmail.jiangyang5157.sudoku.puzzle.Level;
import com.gmail.jiangyang5157.sudoku.sql.AppDatabaseApi;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleTable;
import com.gmail.jiangyang5157.sudoku.ui.puzzle.BasePuzzleFragment;
import com.gmail.jiangyang5157.sudoku.ui.puzzle.SudokuActivity;

public class MainFragment extends BaseFragment {

    private IconButton ibResume = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ibResume = (IconButton) view.findViewById(R.id.ib_resume);

        IconButton ibLevelEasy = (IconButton) view.findViewById(R.id.ib_level_easy);
        IconButton ibLevelNormal = (IconButton) view.findViewById(R.id.ib_level_normal);
        IconButton ibLevelNightmare = (IconButton) view.findViewById(R.id.ib_level_nightmare);
        IconButton ibLevelHell = (IconButton) view.findViewById(R.id.ib_level_hell);

        ibLevelEasy.setOnClickListener(v -> actionNewPuzzle(new Level(Difficulty.EASY)));
        ibLevelNormal.setOnClickListener(v -> actionNewPuzzle(new Level(Difficulty.NORMAL)));
        ibLevelNightmare.setOnClickListener(v -> actionNewPuzzle(new Level(Difficulty.NIGHTMARE)));
        ibLevelHell.setOnClickListener(v -> actionNewPuzzle(new Level(Difficulty.HELL)));
    }

    private void validateResume() {
        final Cursor cursor = AppDatabaseApi.getInstance(getActivity()).queryPuzzles(AppDatabaseApi.buildOrderByDesc(PuzzleTable.KEY_DATE));
        ibResume.setOnClickListener(v -> {
            long rowId = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_ROWID)));
            actionNewPuzzle(rowId);
        });

        if (cursor.getCount() <= 0) {
            ibResume.setVisibility(View.INVISIBLE);
        } else {
            ibResume.setVisibility(View.VISIBLE);
        }
    }

    public void actionNewPuzzle(long rowId) {
        Intent intent = new Intent(getActivity(), SudokuActivity.class);
        intent.putExtra(BasePuzzleFragment.KEY_ROWID, rowId);
        startActivity(intent);
    }

    public void actionNewPuzzle(Level level) {
        Intent intent = new Intent(getActivity(), SudokuActivity.class);
        intent.putExtra(BasePuzzleFragment.KEY_LEVEL_CACHE, level);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        validateResume();
    }
}
