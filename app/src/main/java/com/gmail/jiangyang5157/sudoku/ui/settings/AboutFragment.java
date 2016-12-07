package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.jiangyang5157.sudoku.component.BaseFragment;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 11:44
 */
public class AboutFragment extends BaseFragment {

    public static final String FRAGMENT_TAG = "AboutFragment";
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView test = (TextView) view.findViewById(R.id.test);
        try {
            test.setText(String.format("%s %s", AppUtils.getString(getActivity(), R.string.app_name), AppUtils.getAppVersionName(getActivity())));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
