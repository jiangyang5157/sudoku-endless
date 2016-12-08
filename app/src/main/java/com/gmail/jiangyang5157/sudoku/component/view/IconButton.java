package com.gmail.jiangyang5157.sudoku.component.view;

import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.gmail.jiangyang5157.sudoku.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IconButton extends LinearLayout {

    private GradientsLayout ibIconBg = null;
    private ImageView ibIconCenter = null;
    private TextView ibText = null;

    public IconButton(Context context) {
        this(context, null, 0);
    }

    public IconButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClickable(true);

        LayoutInflater.from(getContext()).inflate(R.layout.icon_button, this, true);
        ibIconBg = (GradientsLayout) findViewById(R.id.ib_icon_bg);
        ibIconCenter = (ImageView) findViewById(R.id.ib_icon_center);
        ibText = (TextView) findViewById(R.id.ib_tv_text);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.IconButton);
        setText(typedArray.getString(R.styleable.IconButton_text));
        int iconBgResId = typedArray.getResourceId(R.styleable.IconButton_icon_bg, 0);
        if (iconBgResId > 0) {
            setIconBg(iconBgResId);
        }
        int iconCenterResId = typedArray.getResourceId(R.styleable.IconButton_icon_center, 0);
        if (iconCenterResId > 0) {
            setIconCenter(iconCenterResId);
        }
        typedArray.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    public void setIconBg(int resId) {
        ibIconBg.setBackgroundResource(resId);
    }

    public void setIconCenter(int resId) {
        ibIconCenter.setBackgroundResource(resId);
    }

    public void setText(CharSequence text) {
        ibText.setText(text);
    }

    public void setText(int resId) {
        ibText.setText(resId);
    }
}
