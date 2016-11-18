package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.os.Bundle;

import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.component.BaseActivity;
import com.gmail.jiangyang5157.sudoku.component.BasePreferenceFragment;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;

public class SettingsActivity extends BaseActivity {

    private SettingsFragment mSettingsFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupViews(savedInstanceState);
    }

    private void setupViews(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString(BasePreferenceFragment.KEY_TITLE, AppUtils.getString(this, R.string.settings));
            mSettingsFragment = BasePreferenceFragment.newInstance(SettingsFragment.class, bundle);

            getFragmentManager().beginTransaction()
                    .add(R.id.container, mSettingsFragment, SettingsFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            mSettingsFragment = (SettingsFragment) getFragmentManager().findFragmentByTag(SettingsFragment.FRAGMENT_TAG);
        }
    }
}
