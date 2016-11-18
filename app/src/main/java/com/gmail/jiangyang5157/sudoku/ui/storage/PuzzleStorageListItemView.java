package com.gmail.jiangyang5157.sudoku.ui.storage;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.sudoku.puzzle.Difficulty;
import com.gmail.jiangyang5157.sudoku.sql.PuzzleTable;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;
import com.gmail.jiangyang5157.tookit.base.data.RegularExpressionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PuzzleStorageListItemView extends FrameLayout {

    private Listener mListener = null;

    public interface Listener {
        void onStateChanged(long rowId, boolean selected);
    }

    private FrameLayout puzzleDrawableContainer = null;
    private ImageView ivPuzzleDrawable = null;
    private ProgressBar pbProgress = null;
    private TextView tvTimer = null;
    private TextView tvDate = null;
    private LinearLayout bestTimeContainer = null;
    private TextView tvBestTime = null;
    private TextView tvDifficulty = null;

    private Drawable mPuzzleDrawable = null;

    private long rowId = PuzzleTable.INVALID_ROWID;
    private boolean isSelected = false;

    private Animation animIn = null;
    private Animation animOut = null;

    public PuzzleStorageListItemView(Context context, Listener listener) {
        super(context);
        this.mListener = listener;

        View view = LayoutInflater.from(getContext()).inflate(R.layout.puzzle_storage_list_item, null, false);
        puzzleDrawableContainer = (FrameLayout) view.findViewById(R.id.puzzle_drawable_container);
        ivPuzzleDrawable = (ImageView) view.findViewById(R.id.iv_puzzle_drawable);
        pbProgress = (ProgressBar) view.findViewById(R.id.pb_progress);
        tvTimer = (TextView) view.findViewById(R.id.tv_timer);
        tvDate = (TextView) view.findViewById(R.id.tv_date);
        bestTimeContainer = (LinearLayout) view.findViewById(R.id.best_time_container);
        tvBestTime = (TextView) view.findViewById(R.id.tv_best_time);
        tvDifficulty = (TextView) view.findViewById(R.id.tv_difficulty);

        puzzleDrawableContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerStateChange();
                if (mListener != null) {
                    mListener.onStateChanged(rowId, isSelected);
                }
            }
        });

        this.addView(view);
    }

    private void triggerStateChange() {
        setSelected(!isSelected, false);
    }

    private void animOut(final View view) {
        if (animOut == null) {
            animOut = AnimationUtils.loadAnimation(getContext(), R.anim.mid_scale_out);
            animOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    updatePuzzleDrawable();
                    animIn(view);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }
            });
        }
        view.startAnimation(animOut);
    }

    private void animIn(final View view) {
        if (animIn == null) {
            animIn = AnimationUtils.loadAnimation(getContext(), R.anim.mid_scale_in);
            animIn.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationStart(Animation animation) {
                }
            });
        }
        view.startAnimation(animIn);
    }

    public void setRowId(long rowId) {
        this.rowId = rowId;
    }

    public void setDate(long date) {
        tvDate.setText(new SimpleDateFormat(RegularExpressionUtils.DATE_REGEX_LABEL, Locale.getDefault()).format(new Date(date)));
    }

    public void setTimer(int timer) {
        tvTimer.setText(buildTimer(timer));
    }

    public void setBestTime(int best_time) {
        tvBestTime.setText(buildTimer(best_time));

        if (best_time > 0) {
            bestTimeContainer.setVisibility(View.VISIBLE);
        } else {
            bestTimeContainer.setVisibility(View.INVISIBLE);
        }
    }

    public static String buildTimer(int timer) {
        StringBuffer ret = new StringBuffer();
        ret.append((timer / 36000) % 10);
        ret.append((timer / 3600) % 10);
        ret.append(":");
        ret.append((timer / 600) % 6);
        ret.append((timer / 60) % 10);
        ret.append(":");
        ret.append((timer / 10) % 6);
        ret.append(timer % 10);
        return ret.toString();
    }

    public void setPuzzleDrawable(Drawable drawable) {
        mPuzzleDrawable = drawable;
    }

    private void updatePuzzleDrawable() {
        if (isSelected) {
            ivPuzzleDrawable.setImageDrawable(AppUtils.getDrawable(getContext(), R.drawable.ic_action_accept, null));
        } else {
            ivPuzzleDrawable.setImageDrawable(mPuzzleDrawable);
        }
    }

    public void setDifficulty(Difficulty difficulty) {
        tvDifficulty.setText(difficulty.toString());
    }

    public void setProgress(int progress, int max) {
        if (pbProgress.getMax() != max) {
            pbProgress.setMax(max);
        }
        pbProgress.setProgress(progress);
    }

    public void setSelected(boolean selected, boolean quite) {
        this.isSelected = selected;

        if (quite) {
            updatePuzzleDrawable();
        } else {
            animOut(puzzleDrawableContainer);
        }
    }

    static class ViewHolder {
        PuzzleStorageListItemView self = null;

        ViewHolder(Context context, Listener listener) {
            self = new PuzzleStorageListItemView(context, listener);
        }
    }
}
