package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.ArrayList;

public class ColorPaletteExpandableListAdapter extends BaseExpandableListAdapter {

    private Context mContext = null;

    private ArrayList<ColorPreference> colorPreferences = null;

    public ColorPaletteExpandableListAdapter(Context context) {
        this(context, null);
    }

    public ColorPaletteExpandableListAdapter(Context context, ArrayList<ColorPreference> colorPreferences) {
        this.mContext = context;
        this.colorPreferences = colorPreferences;
    }

    @Override
    public int getGroupCount() {
        return colorPreferences == null ? 0 : colorPreferences.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public ColorPreference getGroup(int groupPosition) {
        return colorPreferences.get(groupPosition);
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
        ColorPaletteExpandableListGroupItemView.ViewHolder holder = null;

        if (convertView == null) {
            holder = new ColorPaletteExpandableListGroupItemView.ViewHolder(mContext);
            convertView = holder.self;
            convertView.setTag(holder);
        } else {
            holder = (ColorPaletteExpandableListGroupItemView.ViewHolder) convertView.getTag();
        }
        holder.self.setExpanded(isExpanded);
        holder.self.setTitle(colorPreferences.get(groupPosition).title);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ColorPaletteExpandableListChildItemView.ViewHolder holder = null;

        if (convertView == null) {
            holder = new ColorPaletteExpandableListChildItemView.ViewHolder(mContext, (ColorPaletteActivity) mContext);
            convertView = holder.self;
            convertView.setTag(holder);
        } else {
            holder = (ColorPaletteExpandableListChildItemView.ViewHolder) convertView.getTag();
        }

        holder.self.setColorPref(colorPreferences.get(groupPosition));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public ArrayList<ColorPreference> getColorPreferences() {
        return colorPreferences;
    }

    public void setColorPreferences(ArrayList<ColorPreference> colorPreferences) {
        this.colorPreferences = colorPreferences;
    }
}
