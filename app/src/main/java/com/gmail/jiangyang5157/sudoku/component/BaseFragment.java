package com.gmail.jiangyang5157.sudoku.component;

import android.app.Fragment;
import android.os.Bundle;

public abstract class BaseFragment extends Fragment {

    public BaseFragment() {

    }

    public static <T extends BaseFragment> T newInstance(Class<T> cls) {
        return newInstance(cls, null);
    }

    public static <T extends BaseFragment> T newInstance(Class<T> cls, Bundle args) {
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
}
