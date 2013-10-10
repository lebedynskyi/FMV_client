package com.music.fmv.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

/**
 * User: vitaliylebedinskiy
 * Date: 7/30/13
 * Time: 4:29 PM
 */
public class ImageUtils {
    public static Bitmap decodeFromBytes(byte[] b) {
        try {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap decodeScaledBytes(byte[] b, View v) {
        if (v == null) {
            return decodeFromBytes(b);
        }
        return decodeScaledBytes(b, v.getMeasuredWidth(), v.getMeasuredHeight());
    }

    public static Bitmap decodeScaledBytes(byte[] b, int width, int height) {
        try {

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }
}
