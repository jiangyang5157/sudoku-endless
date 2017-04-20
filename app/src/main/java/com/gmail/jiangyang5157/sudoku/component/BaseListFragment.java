package com.gmail.jiangyang5157.sudoku.component;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jiangyang5157.sudoku.R;

public abstract class BaseListFragment extends ListFragment {

    private View mRoot = null;
    private String mContentDescription = null;

    public BaseListFragment() {

    }

    public static <T extends BaseListFragment> T newInstance(Class<T> cls) {
        return newInstance(cls, null);
    }

    public static <T extends BaseListFragment> T newInstance(Class<T> cls, Bundle args) {
        T t = null;
        try {
            t = cls.newInstance();
            t.setArguments(args);
        } catch (java.lang.InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.fragment_list, container, false);
        if (mContentDescription != null) {
            mRoot.setContentDescription(mContentDescription);
        }
        return mRoot;
    }

    public void setContentDescription(String desc) {
        mContentDescription = desc;
        if (mRoot != null) {
            mRoot.setContentDescription(mContentDescription);
        }
    }
}
