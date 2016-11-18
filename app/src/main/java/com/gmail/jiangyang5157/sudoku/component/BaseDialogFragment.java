package com.gmail.jiangyang5157.sudoku.component;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.*;
import android.widget.FrameLayout;

import com.gmail.jiangyang5157.sudoku.R;

public abstract class BaseDialogFragment extends DialogFragment {

    public BaseDialogFragment() {

    }

    public static <T extends BaseDialogFragment> T newInstance(Class<T> cls) {
        return newInstance(cls, null);
    }

    public static <T extends BaseDialogFragment> T newInstance(Class<T> cls, Bundle args) {
        T t = null;
        try {
            t = cls.newInstance();
            t.setArguments(args);
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public abstract boolean isTransparent();

    public abstract boolean isCancelable();

    public abstract View getContentView();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_DialogFragment_Base);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setCanceledOnTouchOutside(false);
        setCancelable(isCancelable());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        if (isTransparent()) {
            window.setBackgroundDrawableResource(R.color.transparent);
        }

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialogfragment_base, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((FrameLayout) view.findViewById(R.id.container)).addView(getContentView());
    }
}
