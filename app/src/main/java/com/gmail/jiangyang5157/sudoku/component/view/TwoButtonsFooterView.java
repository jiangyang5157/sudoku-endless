package com.gmail.jiangyang5157.sudoku.component.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.gmail.jiangyang5157.sudoku.R;

public class TwoButtonsFooterView extends FrameLayout {

    private Button btnNegative = null;
    private Button btnPositive = null;

    public TwoButtonsFooterView(Context context) {
        this(context, null, 0);
    }

    public TwoButtonsFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TwoButtonsFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.footer_twobuttons, null, false);
        btnNegative = (Button) view.findViewById(R.id.btn_negative);
        btnPositive = (Button) view.findViewById(R.id.btn_positive);

        this.addView(view);
    }

    public void initButtons(int resNegativeButtonText, OnClickListener onNegativeButtonClickListener,
                            int resPositiveButtonText, OnClickListener onPositiveButtonClickListener) {
        initNegativeButton(resNegativeButtonText, onNegativeButtonClickListener);
        initPositiveButton(resPositiveButtonText, onPositiveButtonClickListener);
    }

    public void initNegativeButton(int resNegativeButtonText, OnClickListener onNegativeButtonClickListener) {
        btnNegative.setVisibility(View.VISIBLE);
        btnNegative.setText(resNegativeButtonText);
        btnNegative.setOnClickListener(onNegativeButtonClickListener);
    }

    public void initPositiveButton(int resPositiveButtonText, OnClickListener onPositiveButtonClickListener) {
        btnPositive.setVisibility(View.VISIBLE);
        btnPositive.setText(resPositiveButtonText);
        btnPositive.setOnClickListener(onPositiveButtonClickListener);
    }
}
