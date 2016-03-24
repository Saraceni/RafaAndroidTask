package com.rafa.rafaandroidtask.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by rafaelgontijo on 3/23/16.
 */
public class Prefs {

    private static SharedPreferences cache;

    private static final String PREFS_KEY = "com.rafa.rafaandroidtask.PREFS_KEY";
    private static final String WINDOW_KEY = "WINDOW_KEY";
    private static final String SORT_KEY = "SORT_KEY";
    private static final String SHOW_VIRAL_KEY = "SHOW_VIRAL_KEY";
    private static final String VIEW_MODE_KEY = "VIEW_MODE";

    public static final int VIEW_MODE_LIST = 0;
    public static final int VIEW_MODE_GRID = 1;

    public static SharedPreferences getPrefs(Context ctx) {
        if (Prefs.cache == null) {
            Context mainContext = ctx.getApplicationContext();
            SharedPreferences sharedPref = mainContext.getSharedPreferences(
                    PREFS_KEY, Context.MODE_PRIVATE);
            Prefs.cache = sharedPref;
        }
        return Prefs.cache;
    }

    public static String getWindow(Context ctx)
    {
        SharedPreferences prefs = getPrefs(ctx);
        return prefs.getString(WINDOW_KEY, "day");
    }

    public static String getSort(Context ctx)
    {
        SharedPreferences prefs = getPrefs(ctx);
        return prefs.getString(SORT_KEY, "viral");
    }

    public static boolean getShowViral(Context ctx)
    {
        SharedPreferences prefs = getPrefs(ctx);
        return prefs.getBoolean(SHOW_VIRAL_KEY, true);
    }

    public static int getViewMode(Context ctx) {
        SharedPreferences prefs = getPrefs(ctx);
        return prefs.getInt(VIEW_MODE_KEY, VIEW_MODE_GRID);
    }

    public static boolean saveCurrentState(Context ctx, String window, String sort, boolean showViral, int viewMode) {
        SharedPreferences prefs = getPrefs(ctx);
        return prefs.edit().putString(WINDOW_KEY, window).putString(SORT_KEY, sort).putBoolean(SHOW_VIRAL_KEY, showViral).putInt(VIEW_MODE_KEY, viewMode).commit();
    }

}
