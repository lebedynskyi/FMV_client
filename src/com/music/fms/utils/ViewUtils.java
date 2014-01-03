package com.music.fms.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii lebedynskyi
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

    public static int getOrientation(Context c) {
        return getScrenWidth(c) > getScrenHeight(c) ? LANDSCAPE : PORTRAIT;
    }

    public static void selectButton(View selected, View... unselected) {
        selected.setSelected(true);
        for (View v : unselected) {
            v.setSelected(false);
        }
    }

    public static void setVisible(View visible, int type, View... invisible) {
        visible.setVisibility(View.VISIBLE);
        for (View v : invisible) {
            v.setVisibility(type);
        }
    }

    public static void setUpKeyBoardHider(View view, final Activity activity) {
        if (view == null) return;
        if (!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    ViewUtils.hideSoftKeyboard(activity);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setUpKeyBoardHider(innerView, activity);
            }
        }
    }

    public static void setVisible(View v, String s) {
        if (TextUtils.isEmpty(s)) v.setVisibility(View.GONE);
        else v.setVisibility(View.VISIBLE);
    }

    public static void setVisible(View v, Object o) {
        if (o == null) v.setVisibility(View.GONE);
        else v.setVisibility(View.VISIBLE);
    }

    public static void setVisible(View v, List<?> list) {
        if (list != null && list.size() > 0) v.setVisibility(View.VISIBLE);
        else v.setVisibility(View.GONE);
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    public static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return (int) dp;
    }
}
