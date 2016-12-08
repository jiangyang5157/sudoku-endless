package com.gmail.jiangyang5157.sudoku.ui.settings;

import android.graphics.Color;

public class ColorPreference {

    String title;
    String key;
    int r;
    int g;
    int b;
    int a;

    ColorPreference(String title, String key, int color) {
        this(title, key, Color.red(color), Color.green(color), Color.blue(color), Color.alpha(color));
    }

    ColorPreference(String title, String key, int r, int g, int b, int a) {
        this.title = title;
        this.key = key;
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    int getColor() {
        return Color.argb(a, r, g, b);
    }

    @Override
    public String toString() {
        return title + " (" + r + ", " + g + ", " + b + ", " + a + ")";
    }
}
