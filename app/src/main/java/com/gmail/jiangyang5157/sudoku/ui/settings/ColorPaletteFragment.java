package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jiangyang5157.sudoku.SudokuAppUtils;
import com.gmail.jiangyang5157.sudoku.component.BaseExpandableListFragment;

import java.util.ArrayList;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 11:44
 */
public class ColorPaletteFragment extends BaseExpandableListFragment {

    public static final String FRAGMENT_TAG = "ColorPaletteFragment";

    private ColorPaletteExpandableListAdapter mColorPaletteExpandableListAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SudokuAppUtils.fetchColors(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mColorPaletteExpandableListAdapter = new ColorPaletteExpandableListAdapter(getActivity(), buildColorPrefs());
        setListAdapter(mColorPaletteExpandableListAdapter);
    }

    public void reset() {
        mColorPaletteExpandableListAdapter.setColorPreferences(buildColorPrefs());
        mColorPaletteExpandableListAdapter.notifyDataSetChanged();
    }

    public ArrayList<ColorPreference> buildColorPrefs() {
        ArrayList<ColorPreference> ret = new ArrayList<ColorPreference>();
        ret.add(new ColorPreference("Normal Node Text", "NORMAL_NODE_TEXT_COLOR", SudokuAppUtils.NORMAL_NODE_TEXT_COLOR));
        ret.add(new ColorPreference("Selected Node Text", "SELECTED_NODE_TEXT_COLOR", SudokuAppUtils.SELECTED_NODE_TEXT_COLOR));
        ret.add(new ColorPreference("Normal Node Border", "NORMAL_NODE_BORDER_COLOR_EDITABLE", SudokuAppUtils.NORMAL_NODE_BORDER_COLOR_EDITABLE));
        ret.add(new ColorPreference("Puzzle Background", "PUZZLE_BACKGROUND_COLOR", SudokuAppUtils.PUZZLE_BACKGROUND_COLOR));
        ret.add(new ColorPreference("Associated Node Background", "RELATED_NODE_BACKGROUND_COLOR_EDITABLE", SudokuAppUtils.RELATED_NODE_BACKGROUND_COLOR_EDITABLE));
        ret.add(new ColorPreference("Normal Editable Node Background", "NORMAL_NODE_BACKGROUND_COLOR_EDITABLE", SudokuAppUtils.NORMAL_NODE_BACKGROUND_COLOR_EDITABLE));
        ret.add(new ColorPreference("Normal Non Editable Node Background", "NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE", SudokuAppUtils.NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE));
        return ret;
    }
}
