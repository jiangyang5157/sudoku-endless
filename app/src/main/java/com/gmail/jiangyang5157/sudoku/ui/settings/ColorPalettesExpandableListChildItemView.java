package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gmail.jiangyang5157.sudoku.R;
import com.gmail.jiangyang5157.tookit.android.base.AppUtils;

public class ColorPalettesExpandableListChildItemView extends FrameLayout {

    private ColorPref mColorPref = null;

    private ImageView ivColorDrawable = null;
    private TextView tvHexColor = null;

    private Listener mListener = null;

    public interface Listener {
        public void onColorChanged(ColorPref colorPref);
    }

    private SeekBar sbRed = null;
    private SeekBar sbGreen = null;
    private SeekBar sbBlue = null;
    private SeekBar sbAlpha = null;

    public ColorPalettesExpandableListChildItemView(Context context, Listener listener) {
        super(context);
        this.mListener = listener;

        View view = LayoutInflater.from(getContext()).inflate(R.layout.color_palttes_expandable_list_child_item, null, false);
        ivColorDrawable = (ImageView) view.findViewById(R.id.iv_color_drawable);
        tvHexColor = (TextView) view.findViewById(R.id.tv_hex_color);

        sbRed = (SeekBar) view.findViewById(R.id.sb_red);
        sbGreen = (SeekBar) view.findViewById(R.id.sb_green);
        sbBlue = (SeekBar) view.findViewById(R.id.sb_blue);
        sbAlpha = (SeekBar) view.findViewById(R.id.sb_alpha);

        sbRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mColorPref.r = progress;
                    int color = mColorPref.getColor();
                    setColorDrawable(color);
                    setHexColor(color);

                    if (mListener != null) {
                        mListener.onColorChanged(mColorPref);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sbGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mColorPref.g = progress;
                    int color = mColorPref.getColor();
                    setColorDrawable(color);
                    setHexColor(color);

                    if (mListener != null) {
                        mListener.onColorChanged(mColorPref);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sbBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mColorPref.b = progress;
                    int color = mColorPref.getColor();
                    setColorDrawable(color);
                    setHexColor(color);

                    if (mListener != null) {
                        mListener.onColorChanged(mColorPref);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        sbAlpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mColorPref.a = progress;
                    int color = mColorPref.getColor();
                    setColorDrawable(color);
                    setHexColor(color);

                    if (mListener != null) {
                        mListener.onColorChanged(mColorPref);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        this.addView(view);
    }

    public void setColorPref(ColorPref colorPref) {
        this.mColorPref = colorPref;
        int color = mColorPref.getColor();
        setColorDrawable(color);
        setHexColor(color);
        setColor(mColorPref);
    }

    private void setColorDrawable(int color) {
        setColorDrawable(new ColorDrawable(color));
    }

    public void setHexColor(int color) {
        tvHexColor.setText(String.format("%s%s", AppUtils.getString(getContext(), R.string.hash_key), Integer.toHexString(color).toUpperCase()));
    }

    private void setColorDrawable(ColorDrawable colorDrawable) {
        ivColorDrawable.setImageDrawable(colorDrawable);
    }

    private void setColor(ColorPref colorPref) {
        setColor(colorPref.r, colorPref.g, colorPref.b, colorPref.a);
    }

    private void setColor(int r, int g, int b, int a) {
        sbRed.setProgress(r);
        sbGreen.setProgress(g);
        sbBlue.setProgress(b);
        sbAlpha.setProgress(a);
    }

    static class ViewHolder {
        ColorPalettesExpandableListChildItemView self = null;

        ViewHolder(Context context, Listener listener) {
            self = new ColorPalettesExpandableListChildItemView(context, listener);
        }
    }
}
