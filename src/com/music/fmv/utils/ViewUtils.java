package com.music.fmv.utils;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:23 AM
 * To change this template use File | Settings | File Templates.
 */

public class ViewUtils {
    public static final int LANDSCAPE = 0;
    public static final int PORTRAIT = 1;

    public static int getScrenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getScrenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }

    public static int getOrientation(Context c){
        return getScrenWidth(c) > getScrenHeight(c) ? LANDSCAPE : PORTRAIT;
    }
}
