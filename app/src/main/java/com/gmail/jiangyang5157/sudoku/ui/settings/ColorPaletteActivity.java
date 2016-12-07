package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gmail.jiangyang5157.sudoku.SudokuAppUtils;
import com.gmail.jiangyang5157.sudoku.component.BaseActivity;
import com.gmail.jiangyang5157.sudoku.component.BaseExpandableListFragment;
import com.gmail.jiangyang5157.sudoku.component.BaseFragment;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.puzzle.NodeCache;
import com.gmail.jiangyang5157.sudoku.puzzle.render.PuzzleImpl;
import com.gmail.jiangyang5157.sudoku.puzzle.render.node.Node;
import com.gmail.jiangyang5157.sudoku.ui.puzzle.BasePuzzleFragment;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 11:44
 */
public class ColorPaletteActivity extends BaseActivity implements ColorPaletteExpandableListChildItemView.Listener {

    private ColorPalettePuzzleFragment mColorPalettePuzzleFragment = null;

    private ColorPaletteFragment mColorPaletteFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_palette);

        setupViews(savedInstanceState);
    }

    private void setupViews(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(BasePuzzleFragment.KEY_NODES_CACHE, buildColorPalettePuzzle());

            mColorPalettePuzzleFragment = BaseFragment.newInstance(ColorPalettePuzzleFragment.class, bundle);
            getFragmentManager().beginTransaction()
                    .add(R.id.puzzle_container, mColorPalettePuzzleFragment, ColorPalettePuzzleFragment.FRAGMENT_TAG)
                    .commit();

            mColorPaletteFragment = BaseExpandableListFragment.newInstance(ColorPaletteFragment.class, getIntent().getExtras());
            getFragmentManager().beginTransaction()
                    .add(R.id.color_palette_container, mColorPaletteFragment, ColorPaletteFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            mColorPalettePuzzleFragment = (ColorPalettePuzzleFragment) getFragmentManager().findFragmentByTag(ColorPalettePuzzleFragment.FRAGMENT_TAG);
            mColorPaletteFragment = (ColorPaletteFragment) getFragmentManager().findFragmentByTag(ColorPaletteFragment.FRAGMENT_TAG);
        }
    }

    private NodeCache[][] buildColorPalettePuzzle() {
        int[][] puzzleValues = new int[][]{
                {8, 1, 5, 2, 3, 4, 7, 6, 9,},
                {9, 4, 6, 1, 5, 7, 3, 2, 8,},
                {3, 7, 2, 6, 9, 8, 1, 5, 4,},
                {2, 6, 8, 4, 7, 1, 5, 9, 3,},
                {1, 9, 3, 5, 8, 6, 4, 7, 2,},
                {4, 5, 7, 9, 2, 3, 8, 1, 6,},
                {5, 2, 1, 8, 4, 9, 6, 3, 7,},
                {6, 3, 4, 7, 1, 2, 9, 8, 5,},
                {7, 8, 9, 3, 6, 5, 2, 4, 1,},};
        int[][] values = new int[][]{
                {8, 0, 5, 0, 0, 4, 7, 0, 0,},
                {0, 4, 6, 0, 5, 0, 3, 0, 0,},
                {3, 7, 0, 6, 9, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 1, 0, 0, 3,},
                {1, 0, 0, 0, 8, 0, 4, 0, 2,},
                {0, 5, 0, 0, 0, 0, 8, 0, 6,},
                {5, 0, 0, 8, 4, 0, 0, 3, 0,},
                {0, 0, 4, 7, 0, 2, 9, 8, 0,},
                {0, 8, 9, 0, 0, 0, 0, 4, 0,},};
        PuzzleImpl puzzle = new PuzzleImpl();
        puzzle.generatePuzzle(puzzleValues, values);

        Node node;

        node = puzzle.getNode(0, 3);
        node.inputNumber(1);
        node = puzzle.getNode(0, 4);
        node.inputNumber(2);
        node = puzzle.getNode(0, 7);
        node.inputNumber(3);

        node = puzzle.getNode(2, 2);
        node.inputNumber(2);
        node = puzzle.getNode(2, 5);
        node.inputNumber(3);
        node = puzzle.getNode(2, 7);
        node.inputNumber(4);

        node = puzzle.getNode(4, 1);
        node.inputNumber(3);
        node = puzzle.getNode(4, 3);
        node.inputNumber(4);
        node = puzzle.getNode(4, 5);
        node.inputNumber(5);

        node = puzzle.getNode(6, 5);
        node.inputNumber(4);
        node = puzzle.getNode(6, 6);
        node.inputNumber(5);
        node = puzzle.getNode(6, 8);
        node.inputNumber(6);

        node = puzzle.getNode(8, 3);
        node.inputNumber(5);
        node = puzzle.getNode(8, 4);
        node.inputNumber(6);
        node = puzzle.getNode(8, 6);
        node.inputNumber(7);


        node = puzzle.getNode(1, 0);
        node.trigger();
        node.inputNumber(9);
        node.inputNumber(8);
        node = puzzle.getNode(1, 5);
        node.trigger();
        node.inputNumber(7);
        node.inputNumber(6);
        node.inputNumber(5);
        node = puzzle.getNode(1, 8);
        node.inputNumber(4);
        node.trigger();
        node.inputNumber(4);
        node.inputNumber(3);

        node = puzzle.getNode(3, 1);
        node.trigger();
        node.inputNumber(9);
        node.inputNumber(8);
        node = puzzle.getNode(3, 7);
        node.trigger();
        node.inputNumber(7);
        node.inputNumber(6);
        node.inputNumber(5);
        node = puzzle.getNode(3, 8);
        node.inputNumber(4);
        node.trigger();
        node.inputNumber(4);
        node.inputNumber(3);

        node = puzzle.getNode(5, 2);
        node.trigger();
        node.inputNumber(9);
        node.inputNumber(8);
        node = puzzle.getNode(5, 4);
        node.trigger();
        node.inputNumber(7);
        node.inputNumber(6);
        node.inputNumber(5);
        node = puzzle.getNode(5, 7);
        node.inputNumber(4);
        node.trigger();
        node.inputNumber(4);
        node.inputNumber(3);

        node = puzzle.getNode(7, 0);
        node.trigger();
        node.inputNumber(9);
        node.inputNumber(8);
        node = puzzle.getNode(7, 1);
        node.trigger();
        node.inputNumber(7);
        node.inputNumber(6);
        node.inputNumber(5);
        node = puzzle.getNode(7, 4);
        node.inputNumber(4);
        node.trigger();
        node.inputNumber(4);
        node.inputNumber(3);

        return puzzle.getNodesCache();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.color_palette, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.menu_reset:
                new AlertDialog.Builder(ColorPaletteActivity.this)
                        .setCancelable(true)
                        .setTitle(R.string.reset)
                        .setMessage(R.string.msg_reset)
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        SudokuAppUtils.resetColors(ColorPaletteActivity.this);
                                        mColorPaletteFragment.reset();
                                        mColorPalettePuzzleFragment.refreshPuzzleView();
                                    }
                                })
                        .setNegativeButton(R.string.cancel, null).create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onColorChanged(ColorPreference colorPreference) {
        SudokuAppUtils.syncColor(this, colorPreference.key, colorPreference.getColor());
        mColorPalettePuzzleFragment.refreshPuzzleView();
    }
}
