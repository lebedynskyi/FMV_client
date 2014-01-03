package com.music.fms.utils;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 7/14/13
 * Time: 8:24 AM
 * To change this template use File | Settings | File Templates.
 */
public final class Log {
    public static final boolean IS_DEBUG = true;

    public static final void e(String tag, String message) {
        if (IS_DEBUG) android.util.Log.e(tag, message);
    }

    public static final void w(String tag, String message) {
        if (IS_DEBUG) android.util.Log.w(tag, message);
    }

    public static final void d(String tag, String message) {
        if (IS_DEBUG) android.util.Log.w(tag, message);
    }
}
