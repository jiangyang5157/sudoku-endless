package com.gmail.jiangyang5157.sudoku.component;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;

import java.lang.reflect.Field;

/**
 * User: Yang
 * Date: 2014/11/17
 * Time: 23:28
 */
public abstract class BaseActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    public static final String PROCESSING_DIALOG_TAG = "PROCESSING_DIALOG_TAG";

    public Fragment getFragment(String tag) {
        return getFragmentManager().findFragmentByTag(tag);
    }

    public ProcessingDialog getProcessingDialog() {
        return (ProcessingDialog) getFragment(PROCESSING_DIALOG_TAG);
    }

    public void showProcessingDialog() {
        Bundle args = new Bundle();
        args.putString(ProcessingDialog.MASSAGE_KEY, AppUtils.getString(this, R.string.processing));
        ProcessingDialog processingDialog = BaseDialogFragment.newInstance(ProcessingDialog.class, args);
        processingDialog.show(getFragmentManager(), PROCESSING_DIALOG_TAG);
    }

    public boolean hideProcessingDialog() {
        boolean ret = false;
        ProcessingDialog processingDialog = getProcessingDialog();
        if (processingDialog != null) {
            // in case we dismiss the dialog after onSaveInstanceState()
            processingDialog.dismissAllowingStateLoss();
            ret = true;
        }

        return ret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.registerOnSharedPreferenceChangeListener(this);

        setDisplayHomeAsUpEnabled(true);
        overflowAlwaysShow();
    }

    public void setDisplayHomeAsUpEnabled(boolean enable) {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(enable);
        }
    }

    /**
     * Force device to show overflow in the actionBar
     */
    private void overflowAlwaysShow() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * If called, this method will occur before onStop(). There are no guarantees about whether it will occur before or after onPause().
     * <p/>
     * Usually onSaveInstanceState will be called after onPause but before onStop but not always.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /**
     * By overriding this method you can stash an object of a class of your choice when an Activity is being destroyed,
     * and then retrieve it using getLastCustomNonConfigurationInstance().
     *
     * @return
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return super.onRetainCustomNonConfigurationInstance();
    }

    /**
     * This method is called after onStart() when the activity is being re-initialized from a previously saved state.
     * Usually onRestoreInstanceState is redundant because you can easily restore state in onCreate.
     * <p/>
     * Most implementations will simply use onCreate(Bundle) to restore their state,
     * but it is sometimes convenient to do it here after all of the initialization has been done
     * or to allow subclasses to decide whether to use your default implementation.
     * <p/>
     * So for best practice, lay out your view hierarchy in onCreate and restore the previous state in onRestoreInstanceState.
     * If you do that, anyone who subclasses your Activity can chose to override your onRestoreInstanceState
     * to augment or replace your restore state logic.
     * This is a long way of saying onRestoreInstanceState serves as a template method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
