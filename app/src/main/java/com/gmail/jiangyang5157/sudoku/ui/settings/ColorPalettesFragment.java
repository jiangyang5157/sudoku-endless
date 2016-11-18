package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jiangyang5157.sudoku.component.BaseExpandableListFragment;
import com.gmail.jiangyang5157.sudoku.Pref;

import java.util.ArrayList;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 11:44
 */
public class ColorPalettesFragment extends BaseExpandableListFragment {

    public static final String FRAGMENT_TAG = "color_palettes_fragment";

    private ColorPalettesExpandableListAdapter mColorPalettesExpandableListAdapter = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pref.fetchColors(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mColorPalettesExpandableListAdapter = new ColorPalettesExpandableListAdapter(getActivity(), buildColorPrefs());
        setListAdapter(mColorPalettesExpandableListAdapter);
    }

    public void reset() {
        mColorPalettesExpandableListAdapter.setColorPrefs(buildColorPrefs());
        mColorPalettesExpandableListAdapter.notifyDataSetChanged();
    }

    public ArrayList<ColorPref> buildColorPrefs() {
        ArrayList<ColorPref> ret = new ArrayList<ColorPref>();
        ret.add(new ColorPref("Puzzle Background", "PUZZLE_BACKGROUND_COLOR", Pref.PUZZLE_BACKGROUND_COLOR));

        ret.add(new ColorPref("Normal Node Text", "NORMAL_NODE_TEXT_COLOR", Pref.NORMAL_NODE_TEXT_COLOR));
        ret.add(new ColorPref("Related Node Text", "SELECTED_NODE_TEXT_COLOR", Pref.SELECTED_NODE_TEXT_COLOR));

        ret.add(new ColorPref("Node Border", "NORMAL_NODE_BORDER_COLOR_EDITABLE", Pref.NORMAL_NODE_BORDER_COLOR_EDITABLE));

        ret.add(new ColorPref("Normal Editable Node Background", "NORMAL_NODE_BACKGROUND_COLOR_EDITABLE", Pref.NORMAL_NODE_BACKGROUND_COLOR_EDITABLE));
        ret.add(new ColorPref("Normal Non-Editable Node Background", "NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE", Pref.NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE));
        ret.add(new ColorPref("Related Node Background", "RELATED_NODE_BACKGROUND_COLOR_EDITABLE", Pref.RELATED_NODE_BACKGROUND_COLOR_EDITABLE));
        return ret;
    }
}
