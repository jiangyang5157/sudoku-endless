package com.gmail.jiangyang5157.sudoku;

import android.content.Context;
import android.preference.PreferenceManager;

import com.gmail.jiangyang5157.tookit.android.base.AppUtils;
import com.gmail.jiangyang5157.tookit.base.data.structure.contact.EmailAddress;
import com.gmail.jiangyang5157.tookit.base.data.structure.contact.Type;
import com.gmail.jiangyang5157.tookit.base.data.structure.contact.person.Author;

import java.util.ArrayList;

/**
 * Utilities and constants related to app preferences.
 */
public class SudokuAppUtils extends AppUtils {

    private static ArrayList<Author> authors = new ArrayList<Author>();

    static {
        Author author = new Author("Yang", "Jiang");
        author.putEmailAddress(Type.HOME, new EmailAddress("jiangyang5157", "gmail.com"));
        authors.add(author);
    }

    public static ArrayList<Author> getAuthors() {
        return authors;
    }

    // width
    public static final int PUZZLE_BLOCKS_SEPARATOR_WIDTH = 8;
    public static final int PUZZLE_NODES_SEPARATOR_WIDTH = PUZZLE_BLOCKS_SEPARATOR_WIDTH / 2;
    public static final int NORMAL_NODE_BORDER_WIDTH_EDITABLE = 1;
    public static final int NORMAL_NODE_BORDER_WIDTH_NONEDITABLE = NORMAL_NODE_BORDER_WIDTH_EDITABLE;
    public static final int RELATED_NODE_BORDER_WIDTH_EDITABLE = NORMAL_NODE_BORDER_WIDTH_EDITABLE;
    public static final int RELATED_NODE_BORDER_WIDTH_NONEDITABLE = NORMAL_NODE_BORDER_WIDTH_EDITABLE;
    public static final int SELECTED_NODE_BORDER_WIDTH_EDITABLE = PUZZLE_NODES_SEPARATOR_WIDTH;
    public static final int SELECTED_NODE_BORDER_WIDTH_NONEDITABLE = SELECTED_NODE_BORDER_WIDTH_EDITABLE;
    public static final int SAME_VALUE_NODE_BORDER_WIDTH_EDITABLE = NORMAL_NODE_BORDER_WIDTH_EDITABLE;
    public static final int SAME_VALUE_NODE_BORDER_WIDTH_NONEDITABLE = NORMAL_NODE_BORDER_WIDTH_NONEDITABLE;
    public static final int SELECTED_CELL_BORDER_WIDTH = NORMAL_NODE_BORDER_WIDTH_EDITABLE;

    // scale
    public static final float NORMAL_NODE_TEXT_SCALE = 0.5f;
    public static final float RELATED_NODE_TEXT_SCALE = NORMAL_NODE_TEXT_SCALE;
    public static final float SELECTED_NODE_TEXT_SCALE = 0.7f;
    public static final float SAME_VALUE_NODE_TEXT_SCALE = SELECTED_NODE_TEXT_SCALE;

    // color
    public static int PUZZLE_BACKGROUND_COLOR_DEFAULT = 0xffE8E8E8;
    public static int PUZZLE_BACKGROUND_COLOR;
    public static int NORMAL_NODE_BACKGROUND_COLOR_EDITABLE_DEFAULT = 0xeeE8E8E8;
    public static int NORMAL_NODE_BACKGROUND_COLOR_EDITABLE;
    public static int NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE_DEFAULT = 0x77B8B8B8;
    public static int NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE;
    public static int NORMAL_NODE_BORDER_COLOR_EDITABLE_DEFAULT = 0xff000000;
    public static int NORMAL_NODE_BORDER_COLOR_EDITABLE;
    public static int NORMAL_NODE_BORDER_COLOR_NONEDITABLE;
    public static int NORMAL_NODE_TEXT_COLOR_DEFAULT = 0xff000000;
    public static int NORMAL_NODE_TEXT_COLOR;
    public static int RELATED_NODE_BACKGROUND_COLOR_EDITABLE_DEFAULT = 0x7727B2E1;
    public static int RELATED_NODE_BACKGROUND_COLOR_EDITABLE;
    public static int RELATED_NODE_BACKGROUND_COLOR_NONEDITABLE;
    public static int RELATED_NODE_BORDER_COLOR_EDITABLE;
    public static int RELATED_NODE_BORDER_COLOR_NONEDITABLE;
    public static int RELATED_NODE_TEXT_COLOR;
    public static int SELECTED_NODE_BACKGROUND_COLOR_EDITABLE;
    public static int SELECTED_NODE_BACKGROUND_COLOR_NONEDITABLE;
    public static int SELECTED_NODE_TEXT_COLOR_DEFAULT = 0xffdb4437;
    public static int SELECTED_NODE_TEXT_COLOR = 0;
    public static int SELECTED_NODE_BORDER_COLOR_EDITABLE;
    public static int SELECTED_NODE_BORDER_COLOR_NONEDITABLE;
    public static int SAME_VALUE_NODE_BORDER_COLOR_EDITABLE;
    public static int SAME_VALUE_NODE_BORDER_COLOR_NONEDITABLE;
    public static int SAME_VALUE_NODE_TEXT_COLOR;
    public static int NORMAL_CELL_TEXT_COLOR;
    public static int SELECTED_CELL_BORDER_COLOR;
    public static int SELECTED_CELL_TEXT_COLOR;

    private static boolean resetColor(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).commit();
    }

    public static void resetColors(Context context) {
        resetColor(context, "PUZZLE_BACKGROUND_COLOR");
        resetColor(context, "NORMAL_NODE_BACKGROUND_COLOR_EDITABLE");
        resetColor(context, "NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE");
        resetColor(context, "NORMAL_NODE_BORDER_COLOR_EDITABLE");
        resetColor(context, "NORMAL_NODE_TEXT_COLOR");
        resetColor(context, "RELATED_NODE_BACKGROUND_COLOR_EDITABLE");
        resetColor(context, "SELECTED_NODE_TEXT_COLOR");

        fetchColors(context);
    }

    private static int fetchColor(Context context, String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    public static void fetchColors(Context context) {
        PUZZLE_BACKGROUND_COLOR = fetchColor(context, "PUZZLE_BACKGROUND_COLOR", PUZZLE_BACKGROUND_COLOR_DEFAULT);
        NORMAL_NODE_BACKGROUND_COLOR_EDITABLE = fetchColor(context, "NORMAL_NODE_BACKGROUND_COLOR_EDITABLE", NORMAL_NODE_BACKGROUND_COLOR_EDITABLE_DEFAULT);
        SELECTED_NODE_BACKGROUND_COLOR_EDITABLE = NORMAL_NODE_BACKGROUND_COLOR_EDITABLE;
        NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE = fetchColor(context, "NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE", NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE_DEFAULT);
        SELECTED_NODE_BACKGROUND_COLOR_NONEDITABLE = NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE;
        NORMAL_NODE_BORDER_COLOR_EDITABLE = fetchColor(context, "NORMAL_NODE_BORDER_COLOR_EDITABLE", NORMAL_NODE_BORDER_COLOR_EDITABLE_DEFAULT);
        NORMAL_NODE_BORDER_COLOR_NONEDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
        RELATED_NODE_BORDER_COLOR_EDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
        RELATED_NODE_BORDER_COLOR_NONEDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
        SAME_VALUE_NODE_BORDER_COLOR_EDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
        SAME_VALUE_NODE_BORDER_COLOR_NONEDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
        NORMAL_NODE_TEXT_COLOR = fetchColor(context, "NORMAL_NODE_TEXT_COLOR", NORMAL_NODE_TEXT_COLOR_DEFAULT);
        RELATED_NODE_TEXT_COLOR = NORMAL_NODE_TEXT_COLOR;
        NORMAL_CELL_TEXT_COLOR = NORMAL_NODE_TEXT_COLOR;
        RELATED_NODE_BACKGROUND_COLOR_EDITABLE = fetchColor(context, "RELATED_NODE_BACKGROUND_COLOR_EDITABLE", RELATED_NODE_BACKGROUND_COLOR_EDITABLE_DEFAULT);
        RELATED_NODE_BACKGROUND_COLOR_NONEDITABLE = RELATED_NODE_BACKGROUND_COLOR_EDITABLE;
        SELECTED_NODE_TEXT_COLOR = fetchColor(context, "SELECTED_NODE_TEXT_COLOR", SELECTED_NODE_TEXT_COLOR_DEFAULT);
        SELECTED_NODE_BORDER_COLOR_EDITABLE = SELECTED_NODE_TEXT_COLOR;
        SELECTED_NODE_BORDER_COLOR_NONEDITABLE = SELECTED_NODE_TEXT_COLOR;
        SAME_VALUE_NODE_TEXT_COLOR = SELECTED_NODE_TEXT_COLOR;
        SELECTED_CELL_BORDER_COLOR = SELECTED_NODE_TEXT_COLOR;
        SELECTED_CELL_TEXT_COLOR = SELECTED_NODE_TEXT_COLOR;
    }

    public static boolean syncColor(Context context, String key, int value) {
        if (PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).commit()) {
            if (key.equalsIgnoreCase("PUZZLE_BACKGROUND_COLOR")) {
                PUZZLE_BACKGROUND_COLOR = value;
            } else if (key.equalsIgnoreCase("NORMAL_NODE_BACKGROUND_COLOR_EDITABLE")) {
                NORMAL_NODE_BACKGROUND_COLOR_EDITABLE = value;
                SELECTED_NODE_BACKGROUND_COLOR_EDITABLE = NORMAL_NODE_BACKGROUND_COLOR_EDITABLE;
            } else if (key.equalsIgnoreCase("NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE")) {
                NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE = value;
                SELECTED_NODE_BACKGROUND_COLOR_NONEDITABLE = NORMAL_NODE_BACKGROUND_COLOR_NONEDITABLE;
            } else if (key.equalsIgnoreCase("NORMAL_NODE_BORDER_COLOR_EDITABLE")) {
                NORMAL_NODE_BORDER_COLOR_EDITABLE = value;
                NORMAL_NODE_BORDER_COLOR_NONEDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
                RELATED_NODE_BORDER_COLOR_EDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
                RELATED_NODE_BORDER_COLOR_NONEDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
                SAME_VALUE_NODE_BORDER_COLOR_EDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
                SAME_VALUE_NODE_BORDER_COLOR_NONEDITABLE = NORMAL_NODE_BORDER_COLOR_EDITABLE;
            } else if (key.equalsIgnoreCase("NORMAL_NODE_TEXT_COLOR")) {
                NORMAL_NODE_TEXT_COLOR = value;
                RELATED_NODE_TEXT_COLOR = NORMAL_NODE_TEXT_COLOR;
                NORMAL_CELL_TEXT_COLOR = NORMAL_NODE_TEXT_COLOR;
            } else if (key.equalsIgnoreCase("RELATED_NODE_BACKGROUND_COLOR_EDITABLE")) {
                RELATED_NODE_BACKGROUND_COLOR_EDITABLE = value;
                RELATED_NODE_BACKGROUND_COLOR_NONEDITABLE = RELATED_NODE_BACKGROUND_COLOR_EDITABLE;
            } else if (key.equalsIgnoreCase("SELECTED_NODE_TEXT_COLOR")) {
                SELECTED_NODE_TEXT_COLOR = value;
                SELECTED_NODE_BORDER_COLOR_EDITABLE = SELECTED_NODE_TEXT_COLOR;
                SELECTED_NODE_BORDER_COLOR_NONEDITABLE = SELECTED_NODE_TEXT_COLOR;
                SAME_VALUE_NODE_TEXT_COLOR = SELECTED_NODE_TEXT_COLOR;
                SELECTED_CELL_BORDER_COLOR = SELECTED_NODE_TEXT_COLOR;
                SELECTED_CELL_TEXT_COLOR = SELECTED_NODE_TEXT_COLOR;
            }
            return true;
        } else {
            return false;
        }
    }
}
