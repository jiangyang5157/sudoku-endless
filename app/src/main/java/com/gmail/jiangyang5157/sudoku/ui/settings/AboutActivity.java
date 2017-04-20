package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gmail.jiangyang5157.sudoku.component.BaseActivity;
import com.gmail.jiangyang5157.sudoku.component.BaseFragment;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 11:44
 */
public class AboutActivity extends BaseActivity {

    private AboutFragment mAboutFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setupViews(savedInstanceState);
    }

    private void setupViews(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mAboutFragment = BaseFragment.newInstance(AboutFragment.class);

            getFragmentManager().beginTransaction()
                    .add(R.id.container, mAboutFragment, AboutFragment.FRAGMENT_TAG)
                    .commit();
        } else {
            mAboutFragment = (AboutFragment) getFragmentManager().findFragmentByTag(AboutFragment.FRAGMENT_TAG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
//            case R.id.menu_rating:
//                String appPackageName = null;
//                try {
//                    appPackageName = AppUtils.getAppPackageName(this);
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//                } catch (android.content.ActivityNotFoundException anfe) {
//                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
//                }
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
