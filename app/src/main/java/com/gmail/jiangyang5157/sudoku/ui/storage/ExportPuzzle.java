package com.gmail.jiangyang5157.sudoku.ui.storage;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

import com.gmail.jiangyang5157.kotlin_core.utils.IoUtils;
import com.gmail.jiangyang5157.sudoku.sql.AppDatabaseApi;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleTable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;

class ExportPuzzle extends AsyncTask<HashSet<Long>, Void, File> {

    static final String JSON_PREFIX = "#";

    public interface Listener {
        void onPreExecute();

        void onPostExecute(File result);
    }

    private Context mContext = null;
    private String mFilePath = null;
    private Listener mListener = null;

    ExportPuzzle(Context context, String filePath, Listener listener) {
        this.mContext = context;
        this.mFilePath = filePath;
        this.mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mListener != null) {
            mListener.onPreExecute();
        }
    }

    @Override
    protected final File doInBackground(HashSet<Long>... params) {
        HashSet<Long> rowIds = params[0];
        StringBuilder content = new StringBuilder();

        for (Long rowId1 : rowIds) {
            String rowId = String.valueOf(rowId1);
            Cursor cursor = AppDatabaseApi.getInstance(mContext).queryPuzzle(PuzzleTable.KEY_ROWID, rowId, PuzzleTable.KEY_DATE + " " + AppDatabaseApi.ORDER_BY_DESC);
            String cacheString = cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_CACHE));
            content.append(JSON_PREFIX).append(cacheString);
        }

        File file = new File(mFilePath);
        InputStream in = null;
        try {
            in = new ByteArrayInputStream(content.toString().getBytes(StandardCharsets.UTF_8));
            IoUtils.INSTANCE.write(in, file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(File result) {
        super.onPostExecute(result);
        if (mListener != null) {
            mListener.onPostExecute(result);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
