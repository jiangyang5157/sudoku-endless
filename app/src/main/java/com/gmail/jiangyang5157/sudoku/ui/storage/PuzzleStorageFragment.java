package com.gmail.jiangyang5157.sudoku.ui.storage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.*;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.*;
import android.widget.ListView;

import com.gmail.jiangyang5157.kotlin_core.utils.IoUtils;
import com.gmail.jiangyang5157.kotlin_core.utils.RegexUtils;
import com.gmail.jiangyang5157.sudoku.component.BaseActivity;
import com.gmail.jiangyang5157.sudoku.component.BaseListFragment;
import com.gmail.jiangyang5157.sudoku.component.SpecialKeyListener;
import com.gmail.jiangyang5157.sudoku.Config;
import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.sql.AppDatabaseApi;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleCursorLoader;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleTable;
import com.gmail.jiangyang5157.sudoku.ui.puzzle.BasePuzzleFragment;
import com.gmail.jiangyang5157.sudoku.ui.puzzle.SudokuActivity;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;
import com.gmail.jiangyang5157.tookit.android.base.DeviceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.support.v4.content.PermissionChecker.checkSelfPermission;

/**
 * User: Yang
 * Date: 2014/11/23
 * Time: 11:44
 */
public class PuzzleStorageFragment extends BaseListFragment implements LoaderManager.LoaderCallbacks<Cursor>, PuzzleStorageCursorAdapter.Listener, SpecialKeyListener, ExportPuzzle.Listener {
    private final static String TAG = "[PuzzleStorageFragment]";

    public static final String FRAGMENT_TAG = "PuzzleStorageFragment";

    private static final int FILE_CHOOSER_REQUESTCODE = 200;

    private static final int RPERMISSIONS_REQUESTCODE_HANDLE_FILE_CHOOSER_URI = 1000;
    private static final int RPERMISSIONS_REQUESTCODE_HANDLE_SHARE = 1001;

    private View emptyPuzzleStorageListFooter = null;

    private PuzzleStorageCursorAdapter mAdapter = null;

    private ExportPuzzle mExportPuzzle = null;

    private Uri uriFromFileChooser;

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
        menu.clear();
        if (mAdapter != null && mAdapter.getSelectedRowIdCount() > 0) {
            getActivity().getMenuInflater().inflate(R.menu.puzzle_storage_selected, menu);
        } else {
            getActivity().getMenuInflater().inflate(R.menu.puzzle_storage_normal, menu);
        }
        super.onPrepareOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
                if (checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                        // Explain to the user why we need to read the contacts
                    }
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RPERMISSIONS_REQUESTCODE_HANDLE_SHARE);
                } else {
                    handleShare();
                }
                return true;
            case R.id.menu_discard:
                new AlertDialog.Builder(getActivity())
                        .setCancelable(true)
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

    private void handleShare() {
        if (DeviceUtils.isExternalStorageWritable()) {
            String dirPath = null;
            try {
                dirPath = DeviceUtils.getSdacrdFile() + File.separator + AppUtils.getAppPackageName(getActivity());
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Access external storage failed.");
                return;
            }
            File directory = new File(dirPath);
            if (!directory.exists() || !directory.isDirectory()) {
                boolean mkdirs = directory.mkdirs();
            }
            String name = new SimpleDateFormat(RegexUtils.INSTANCE.getDATE_FILE_NAME(), Locale.getDefault()).format(new Date()) + "." + Config.PUZZLE_FILE_END;
            String path = dirPath + File.separator + name;

            if (mExportPuzzle == null || mExportPuzzle.getStatus() == AsyncTask.Status.FINISHED) {
                mExportPuzzle = new ExportPuzzle(getActivity(), path, this);
            }
            if (mExportPuzzle.getStatus() == AsyncTask.Status.PENDING) {
                mExportPuzzle.execute(mAdapter.getSelectedRowIDs());
            }
        } else {
            AppUtils.buildToast(getActivity(), R.string.msg_no_external_storage);
        }
    }

    @Override
    public void onPreExecute() {
        ((BaseActivity) getActivity()).showProcessingDialog();
    }

    @Override
    public void onPostExecute(File file) {
        if (getActivity() == null) {
            return;
        }
        ((BaseActivity) getActivity()).hideProcessingDialog();
        sendEmail(getActivity(), file);
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
            startActivityForResult(Intent.createChooser(intent, AppUtils.getString(getActivity(), R.string.import_puzzle)), FILE_CHOOSER_REQUESTCODE);
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
        if (size > 0) {
            getActivity().setTitle(String.valueOf(size) + " " + AppUtils.getString(getActivity(), R.string.selected));
        } else {
            getActivity().setTitle(AppUtils.getString(getActivity(), R.string.puzzle_storage));
        }
        getActivity().invalidateOptionsMenu();
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long rowId) {
        if (rowId == -1) {
            // empty puzzle storage header/footer view click
            getActivity().onNavigateUpFromChild(getActivity());
        } else {
            Intent intent = new Intent(getActivity(), SudokuActivity.class);
            intent.putExtra(BasePuzzleFragment.KEY_ROWID, rowId);
            startActivityForResult(intent, SudokuActivity.ACTIVITY_REQUESTCODE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        Log.d(TAG, "onCreateLoader - id = " + id);
        return new PuzzleCursorLoader(getActivity(), AppDatabaseApi.buildOrderByDesc(PuzzleTable.KEY_DATE));
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        Log.d(TAG, "onLoadFinished - id = " + loader.getId() + ", count = " + data.getCount());
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SudokuActivity.ACTIVITY_REQUESTCODE:
                mAdapter.unSelectedAllRowIds();
                getLoaderManager().restartLoader(PuzzleCursorLoader.QUERY_PUZZLES, null, this);
                break;
            case FILE_CHOOSER_REQUESTCODE:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    uriFromFileChooser = data.getData();
                    if (checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            // Explain to the user why we need to read the contacts
                        }
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RPERMISSIONS_REQUESTCODE_HANDLE_FILE_CHOOSER_URI);
                        return;
                    } else {
                        handleFileChooserUri();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void handleFileChooserUri() {
        String filePath = DeviceUtils.getPath(getActivity(), uriFromFileChooser);
        if (filePath.endsWith("." + Config.PUZZLE_FILE_END)) {
            importPuzzle(getActivity(), filePath);
            getLoaderManager().restartLoader(PuzzleCursorLoader.QUERY_PUZZLES, null, this);
        } else {
            AppUtils.buildToast(getActivity(), R.string.msg_wrong_format);
        }
    }

    public static void importPuzzle(Context context, String filePath) {
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        try {
            in = new FileInputStream(new File(filePath));
            IoUtils.INSTANCE.read(in, line -> {
                if (line == null) {
                    return false;
                } else {
                    sb.append(line);
                    return true;
                }
            });
        } catch (IOException e) {
            Log.e(TAG, "ERROR: import puzzle");
            return;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String[] jsons = sb.toString().split(ExportPuzzle.JSON_PREFIX);
        int length = jsons.length;
        // empty json[0]
        for (int i = 1; i < length; i++) {
            String cache = jsons[i];
            String drawable = "";
            String longDate = String.valueOf((new Date()).getTime());
            String timer = "0";
            String best_time = "0";
            long rowId = AppDatabaseApi.getInstance(context).insertPuzzle(cache, drawable, longDate, timer, best_time);
        }
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length <= 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            // User refused to grant permission.
            return;
        }

        switch (requestCode) {
            case RPERMISSIONS_REQUESTCODE_HANDLE_FILE_CHOOSER_URI:
                handleFileChooserUri();
                break;
            case RPERMISSIONS_REQUESTCODE_HANDLE_SHARE:
                handleShare();
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
