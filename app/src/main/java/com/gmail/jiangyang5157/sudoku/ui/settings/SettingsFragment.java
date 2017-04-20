package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.content.Intent;
import android.preference.Preference;

import com.gmail.jiangyang5157.sudoku.component.BasePreferenceFragment;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;

public class SettingsFragment extends BasePreferenceFragment {

    public static final String FRAGMENT_TAG = "SettingsFragment";

    @Override
    public void setupPreferencesScreen() {
        addPreferencesFromResource(R.xml.preferences_settings);

        Preference pColorPalette = findPreference(AppUtils.getString(getActivity(), R.string.color_palette));
        pColorPalette.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(getActivity(), ColorPaletteActivity.class));
            return true;
        });

        Preference pAbout = findPreference(AppUtils.getString(getActivity(), R.string.about));
        pAbout.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(getActivity(), AboutActivity.class));
            return true;
        });
    }
}