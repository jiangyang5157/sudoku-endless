package com.gmail.jiangyang5157.sudoku.ui.storage;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.gmail.jiangyang5157.sudoku.Config;
import com.gmail.jiangyang5157.sudoku.puzzle.NodeCache;
import com.gmail.jiangyang5157.sudoku.puzzle.PuzzleCache;
import com.gmail.jiangyang5157.sudoku.sql.AppDatabaseApi;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleTable;
import com.gmail.jiangyang5157.tookit.android.base.EncodeUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

public class PuzzleStorageCursorAdapter extends CursorAdapter implements PuzzleStorageListItemView.Listener {
    private Context mContext = null;
    private Listener mListener = null;

    public interface Listener {
        void onSelectedRowIdCountChanged(int size);
    }

    private HashSet<Long> selectedRowIDs = null;

    public PuzzleStorageCursorAdapter(Context context, Cursor c, Listener listener) {
        // I don't want to pass it FLAG_REGISTER_CONTENT_OBSERVER, since I'm using a CursorLoader with my CursorAdapter.
        // I definitely don't want to pass it FLAG_AUTO_REQUERY, since that flag is deprecated.
        // so I pass 0 flag
        super(context, c, 0);
        this.mContext = context;
        this.mListener = listener;
        selectedRowIDs = new HashSet<Long>();
    }

    public HashSet<Long> getSelectedRowIDs() {
        return selectedRowIDs;
    }

    private void selectedRowId(long rowId) {
        selectedRowIDs.add(rowId);
        if (mListener != null) {
            mListener.onSelectedRowIdCountChanged(selectedRowIDs.size());
        }
    }

    private void unSelectedRowId(long rowId) {
        selectedRowIDs.remove(rowId);
        if (mListener != null) {
            mListener.onSelectedRowIdCountChanged(selectedRowIDs.size());
        }
    }

    public void selectedAllRowIds() {
        Cursor c = getCursor();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            long rowId = Long.parseLong(c.getString(c.getColumnIndexOrThrow(PuzzleTable.KEY_ROWID)));
            selectedRowIDs.add(rowId);
        }

        if (mListener != null) {
            mListener.onSelectedRowIdCountChanged(selectedRowIDs.size());
        }
    }

    public void unSelectedAllRowIds() {
        selectedRowIDs.clear();

        if (mListener != null) {
            mListener.onSelectedRowIdCountChanged(selectedRowIDs.size());
        }
    }

    public int getSelectedRowIdCount() {
        return selectedRowIDs.size();
    }

    public void deleteClickedRowIDs() {
        Iterator<Long> it = selectedRowIDs.iterator();
        while (it.hasNext()) {
            long rowId = it.next();
            if (AppDatabaseApi.getInstance(mContext).deletePuzzle(PuzzleTable.KEY_ROWID, String.valueOf(rowId)) > 0) {
                it.remove();
            }
        }

        if (mListener != null) {
            mListener.onSelectedRowIdCountChanged(selectedRowIDs.size());
        }
    }

    @Override
    public void onStateChanged(long rowId, boolean selected) {
        if (selected) {
            selectedRowId(rowId);
        } else {
            unSelectedRowId(rowId);
        }
    }

    /**
     * @param position
     * @return long rowId ("_id")
     */
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    /**
     * @param position
     * @return a Cursor
     */
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        PuzzleStorageListItemView.ViewHolder holder = new PuzzleStorageListItemView.ViewHolder(context, this);
        holder.self.setTag(holder);
        return holder.self;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        PuzzleStorageListItemView.ViewHolder holder = (PuzzleStorageListItemView.ViewHolder) view.getTag();

        long rowId = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_ROWID)));

        holder.self.setRowId(rowId);
        holder.self.setDate(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_DATE))));
        holder.self.setTimer(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_TIMER))));
        holder.self.setBestTime(Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_BEST_TIME))));

        try {
            PuzzleCache cache = (PuzzleCache) EncodeUtils.decodeObject(cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_CACHE)));
            if (cache == null) {
                // wtf cache is null - happens when launch app from IDE when the app still runing in device
            } else {
                holder.self.setDifficulty(cache.getLevel().getDifficulty());
                int[] puzzleProgress = getPuzzleProgress(cache.getNodesCache());
                holder.self.setProgress(puzzleProgress[0], puzzleProgress[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            holder.self.setPuzzleDrawable(EncodeUtils.decodeDrawable(context, cursor.getString(cursor.getColumnIndexOrThrow(PuzzleTable.KEY_DRAWABLE))));
            holder.self.setSelected(selectedRowIDs.contains(rowId), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param nodesCache
     * @return progress, max
     */
    private int[] getPuzzleProgress(NodeCache[][] nodesCache) {
        int[] ret = new int[2];
        for (int i = 0; i < Config.SUDOKU_SIZE; i++) {
            for (int j = 0; j < Config.SUDOKU_SIZE; j++) {
                if (nodesCache[i][j].getValue() != 0) {
                    ret[0]++;
                }
            }
        }
        ret[1] = Config.SUDOKU_SIZE * Config.SUDOKU_SIZE;
        return ret;
    }
}
