package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;

public class ColorPaletteExpandableListGroupItemView extends FrameLayout {

    private ImageView indicator = null;

    private TextView tvTitle = null;

    public ColorPaletteExpandableListGroupItemView(Context context) {
        super(context);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.color_paltte_expandable_list_group_item, null, false);
        indicator = (ImageView) view.findViewById(R.id.indicator);
        tvTitle = (TextView) view.findViewById(R.id.title);

        this.addView(view);
    }

    public void setExpanded(boolean isExpanded) {
        if (isExpanded) {
            indicator.setImageDrawable(AppUtils.getDrawable(getContext(), R.drawable.ic_arrow_drop_up_black_24px, null));
        } else {
            indicator.setImageDrawable(AppUtils.getDrawable(getContext(), R.drawable.ic_arrow_drop_down_black_24px, null));
        }
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    static class ViewHolder {
        ColorPaletteExpandableListGroupItemView self = null;

        ViewHolder(Context context) {
            self = new ColorPaletteExpandableListGroupItemView(context);
        }
    }
}
