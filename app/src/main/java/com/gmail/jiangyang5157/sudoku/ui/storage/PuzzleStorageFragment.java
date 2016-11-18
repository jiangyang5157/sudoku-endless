package com.gmail.jiangyang5157.sudoku.ui.storage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ListView;

import com.gmail.jiangyang5157.sudoku.component.BaseActivity;
import com.gmail.jiangyang5157.sudoku.component.BaseListFragment;
import com.gmail.jiangyang5157.sudoku.component.SpecialKeyListener;
import com.gmail.jiangyang5157.sudoku.Config;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.XmlUtils;
import com.gmail.jiangyang5157.sudoku.sql.AppDatabaseApi;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleCursorLoader;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleTable;
import com.gmail.jiangyang5157.sudoku.ui.puzzle.BasePuzzleFragment;
import com.gmail.jiangyang5157.sudoku.ui.puzzle.SudokuActivity;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;
import com.gmail.jiangyang5157.tookit.android.base.DeviceUtils;
import com.gmail.jiangyang5157.tookit.base.data.RegularExpressionUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 11:44
 */
public class PuzzleStorageFragment extends BaseListFragment implements LoaderManager.LoaderCallbacks<Cursor>, PuzzleStorageCursorAdapter.Listener, SpecialKeyListener, XmlWriterTask.Listener {
    private final static String TAG = "[PuzzleStorageFragment]";

    public static final String FRAGMENT_TAG = "puzzle_storage_fragment";

    private static final int REQUESTCODE_FILE_CHOOSER = 100;

    private View emptyPuzzleStorageListFooter = null;

    private PuzzleStorageCursorAdapter mAdapter = null;

    private XmlWriterTask task = null;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        emptyPuzzleStorageListFooter = inflater.inflate(R.layout.empty_puzzle_storage_list_footer, null, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAdapter = new PuzzleStorageCursorAdapter(getActivity(), null, this);
        setListAdapter(mAdapter);

        getLoaderManager().initLoader(PuzzleCursorLoader.QUERY_PUZZLES, null, this);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        Log.d(TAG, "onPrepareOptionsMenu");
        menu.clear();
        if (mAdapter != null && mAdapter.getSelectedRowIdCount() > 0) {
            getActivity().getMenuInflater().inflate(R.menu.puzzle_storage_selected, menu);
        } else {
            getActivity().getMenuInflater().inflate(R.menu.puzzle_storage_normal, menu);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        switch (id) {
            case R.id.menu_select_all:
                mAdapter.selectedAllRowIds();
                mAdapter.notifyDataSetChanged();
                return true;
            case R.id.menu_import_puzzle:
                launchFileChooser();
                return true;
            case R.id.menu_share:
                if (DeviceUtils.hasSdcard()) {
                    String dir = null;
                    try {
                        dir = DeviceUtils.getSdacrdFile() + File.separator + AppUtils.getAppPackageName(getActivity());
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    File file = new File(dir);
                    if (!file.exists() || !file.isDirectory()) {
                        file.mkdirs();
                    }

                    String name = new SimpleDateFormat(RegularExpressionUtils.DATE_REGEX_FILE_NAME, Locale.getDefault()).format(new Date()) + "." + Config.PUZZLE_FILE_END;
                    String path = dir + File.separator + name;

                    if (task == null || task.getStatus() == AsyncTask.Status.FINISHED) {
                        task = new XmlWriterTask(getActivity(), path, PuzzleStorageFragment.this);
                    }
                    if (task.getStatus() == AsyncTask.Status.PENDING) {
                        task.execute(mAdapter.getSelectedRowIDs());
                    }
                } else {
                    AppUtils.buildToast(getActivity(), R.string.msg_no_external_storage);
                }
                return true;
            case R.id.menu_discard:
                new AlertDialog.Builder(getActivity())
                        .setCancelable(true)
                        .setTitle(R.string.discard)
                        .setMessage(R.string.msg_discard)
                        .setPositiveButton(R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        mAdapter.deleteClickedRowIDs();
                                        getLoaderManager().restartLoader(PuzzleCursorLoader.QUERY_PUZZLES, null, PuzzleStorageFragment.this);
                                    }
                                })
                        .setNegativeButton(R.string.cancel, null).create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPreExecute() {
        ((BaseActivity) getActivity()).showProcessingDialog();
    }

    @Override
    public void onPostExecute(File result) {
        if (getActivity() == null) {
            return;
        }

        ((BaseActivity) getActivity()).hideProcessingDialog();

        sendEmail(getActivity(), result);
    }

    public static final boolean sendEmail(Context context, File file) {
        boolean ret = false;
        String subject = null;
        try {
            subject = String.format("%s %s", AppUtils.getString(context, R.string.app_name), AppUtils.getAppVersionName(context));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        final String TYPE = "application/octet-stream";

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        intent.setType(TYPE);

        try {
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.share)));
            ret = true;
        } catch (android.content.ActivityNotFoundException ex) {
            AppUtils.buildToast(context, R.string.msg_no_related_launcher);
        }
        return ret;
    }

    private void launchFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, AppUtils.getString(getActivity(), R.string.import_puzzle)), REQUESTCODE_FILE_CHOOSER);
        } catch (ActivityNotFoundException ex) {
            AppUtils.buildToast(getActivity(), R.string.msg_no_related_launcher);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onSelectedRowIdCountChanged(int size) {
        getActivity().setTitle(size > 0 ? String.valueOf(size) : AppUtils.getString(getActivity(), R.string.puzzle_storage));
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long rowId) {
        Log.d(TAG, "onListItemClick - position=" + position + ", rowId=" + rowId);
        if (rowId == -1) {
            // empty puzzle storage header/footer view click
            getActivity().onNavigateUpFromChild(getActivity());
        } else {
            Intent intent = new Intent(getActivity(), SudokuActivity.class);
            intent.putExtra(BasePuzzleFragment.KEY_ROWID, rowId);
            startActivityForResult(intent, SudokuActivity.REQUESTCODE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader - id = " + id);
        return new PuzzleCursorLoader(getActivity(), AppDatabaseApi.buildOrderByDesc(PuzzleTable.KEY_DATE));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished - id = " + loader.getId() + ", count = " + data.getCount());
        int count = data.getCount();
        if (count == 0) {
            getListView().removeFooterView(emptyPuzzleStorageListFooter);
            getListView().addFooterView(emptyPuzzleStorageListFooter);
            // low sdk version require reset adapter after add footer view
            setListAdapter(mAdapter);
        } else {
            getListView().removeFooterView(emptyPuzzleStorageListFooter);
        }

        mAdapter.changeCursor(data);
    }

    /**
     * Called when a previously created loader is being reset, and thus
     * making its data unavailable. The application should at this point
     * remove any references it has to the Loader's data.
     *
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SudokuActivity.REQUESTCODE:
                mAdapter.unSelectedAllRowIds();
                getLoaderManager().restartLoader(PuzzleCursorLoader.QUERY_PUZZLES, null, this);
                break;
            case REQUESTCODE_FILE_CHOOSER:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String path = DeviceUtils.getPath(getActivity(), data.getData());
                    if (path.endsWith("." + Config.PUZZLE_FILE_END)) {
                        XmlUtils.read(getActivity(), path);
                        getLoaderManager().restartLoader(PuzzleCursorLoader.QUERY_PUZZLES, null, this);
                    } else {
                        AppUtils.buildToast(getActivity(), R.string.msg_wrong_format);
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onBackPressed() {
        boolean ret = false;
        if (mAdapter.getSelectedRowIdCount() > 0) {
            mAdapter.unSelectedAllRowIds();
            mAdapter.notifyDataSetChanged();
            ret = true;
        }
        return ret;
    }
}
