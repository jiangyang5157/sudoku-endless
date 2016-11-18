package com.gmail.jiangyang5157.sudoku.component;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gmail.jiangyang5157.sudoku.R;

public class ProcessingDialog extends BaseDialogFragment {

    public final static String TAG = "[ProcessingDialog]";

    public static final String MASSAGE_KEY = "MASSAGE_KEY";

    private TextView tvMassage = null;

    @Override
    public View getContentView() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialogfragment_processing, null, false);
        tvMassage = (TextView) view.findViewById(R.id.tv_massage);

        if (getArguments() != null) {
            setMassage(getArguments().getString(MASSAGE_KEY));
        }

        return view;
    }

    public void setMassage(String msg) {
        tvMassage.setText(msg);
    }

    @Override
    public boolean isTransparent() {
        return true;
    }

    @Override
    public boolean isCancelable() {
        return false;
    }
}
