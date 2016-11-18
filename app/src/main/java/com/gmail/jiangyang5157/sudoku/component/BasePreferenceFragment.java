package com.gmail.jiangyang5157.sudoku.component;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.gmail.jiangyang5157.tookit.android.base.AppUtils;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 1:15
 */
public abstract class BasePreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String KEY_TITLE = "title";

    private String mTitle = null;

    public BasePreferenceFragment() {

    }

    public static <T extends BasePreferenceFragment> T newInstance(Class<T> cls) {
        return newInstance(cls, null);
    }

    public static <T extends BasePreferenceFragment> T newInstance(Class<T> cls, Bundle args) {
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

    public abstract void setupPreferencesScreen();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        setupPreferencesScreen();
        AppUtils.registerOnSharedPreferenceChangeListener(getActivity(), this);

        Bundle bundle = savedInstanceState == null ? getArguments() : savedInstanceState;
        if (bundle != null) {
            mTitle = bundle.getString(BasePreferenceFragment.KEY_TITLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mTitle != null) {
            getActivity().setTitle(mTitle);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AppUtils.unregisterOnSharedPreferenceChangeListener(getActivity(), this);
    }
}
