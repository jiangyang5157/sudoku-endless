package com.gmail.jiangyang5157.sudoku.ui.storage;

import android.content.Context;
import android.os.AsyncTask;

import com.gmail.jiangyang5157.sudoku.XmlUtils;

import java.io.File;
import java.util.HashSet;

public class XmlWriterTask extends AsyncTask<HashSet<Long>, Integer, File> {

    public interface Listener {
        public void onPreExecute();

        public void onPostExecute(File result);
    }

    private Context mContext = null;
    private String mFilePath = null;
    private Listener mListener = null;

    public XmlWriterTask(Context context, String filePath, Listener listener) {
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
    protected File doInBackground(HashSet<Long>... params) {
        return XmlUtils.write(mContext, mFilePath, params[0]);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
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
