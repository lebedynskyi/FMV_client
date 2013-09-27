package com.music.fmv.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.sileria.android.view.SlidingTray;
import com.sileria.util.Side;

/**
 * User: vitaliylebedinskiy
 * Date: 9/27/13
 * Time: 1:20 PM
 */
public class FixedSlidigTray extends SlidingTray {

    public FixedSlidigTray(Context context, View handle, View content, int orientation) {
        super(context, handle, content, orientation);
        init();
    }

    public FixedSlidigTray(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FixedSlidigTray(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setHandlePosition(Side.TOP);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return false;
    }

    @Override
    public void animateOpen() {
        //TODO new implemented animation
        super.open();
    }

    @Override
    public void animateClose() {
        //TODO new implemented animation
        super.close();
    }
}
