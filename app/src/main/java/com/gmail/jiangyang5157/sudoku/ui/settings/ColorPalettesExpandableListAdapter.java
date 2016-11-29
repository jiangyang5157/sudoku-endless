package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;

public class ColorPalettesExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext = null;

    private ArrayList<ColorPref> colorPrefs = null;

    public ColorPalettesExpandableListAdapter(Context context) {
        this(context, null);
    }

    public ColorPalettesExpandableListAdapter(Context context, ArrayList<ColorPref> colorPrefs) {
        this.mContext = context;
        this.colorPrefs = colorPrefs;
    }

    @Override
    public int getGroupCount() {
        return colorPrefs == null ? 0 : colorPrefs.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public ColorPref getGroup(int groupPosition) {
        return colorPrefs.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ColorPalettesExpandableListGroupItemView.ViewHolder holder = null;

        if (convertView == null) {
            holder = new ColorPalettesExpandableListGroupItemView.ViewHolder(mContext);
            convertView = holder.self;
            convertView.setTag(holder);
        } else {
            holder = (ColorPalettesExpandableListGroupItemView.ViewHolder) convertView.getTag();
        }
        holder.self.setExpanded(isExpanded);
        holder.self.setTitle(colorPrefs.get(groupPosition).title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ColorPalettesExpandableListChildItemView.ViewHolder holder = null;

        if (convertView == null) {
            holder = new ColorPalettesExpandableListChildItemView.ViewHolder(mContext, (ColorPalettesActivity) mContext);
            convertView = holder.self;
            convertView.setTag(holder);
        } else {
            holder = (ColorPalettesExpandableListChildItemView.ViewHolder) convertView.getTag();
        }

        holder.self.setColorPref(colorPrefs.get(groupPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public ArrayList<ColorPref> getColorPrefs() {
        return colorPrefs;
    }

    public void setColorPrefs(ArrayList<ColorPref> colorPrefs) {
        this.colorPrefs = colorPrefs;
    }
}
