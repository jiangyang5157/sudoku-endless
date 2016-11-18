package com.gmail.jiangyang5157.sudoku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.gmail.jiangyang5157.sudoku.component.BaseActivity;
import com.gmail.jiangyang5157.sudoku.component.BaseFragment;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.ui.settings.SettingsActivity;
import com.gmail.jiangyang5157.sudoku.ui.storage.PuzzleStorageActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDisplayHomeAsUpEnabled(false);

        if (savedInstanceState == null) {
            setupViews();
        }
    }

    private void setupViews() {
        getFragmentManager().beginTransaction()
                .add(R.id.container, BaseFragment.newInstance(MainFragment.class))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.menu_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.menu_puzzle_storage:
                startActivity(new Intent(this, PuzzleStorageActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
