package com.music.fmv.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 11/27/13
 * Time: 6:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class RotatedSlidingDrawer extends SlidingDrawer {
    private int rotationAngle;

    public RotatedSlidingDrawer(Context context, View handle, View content, Orientation orientation) {
        super(context, handle, content, orientation);
    }

    public RotatedSlidingDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotatedSlidingDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
