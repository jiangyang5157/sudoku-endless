package com.gmail.jiangyang5157.sudoku.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gmail.jiangyang5157.sudoku.R;

public abstract class BaseExpandableListFragment extends ExpandableListFragment {

    public BaseExpandableListFragment() {

    }

    public static <T extends BaseExpandableListFragment> T newInstance(Class<T> cls) {
        return newInstance(cls, null);
    }

    public static <T extends BaseExpandableListFragment> T newInstance(Class<T> cls, Bundle args) {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expandable_list, container, false);
    }
}
