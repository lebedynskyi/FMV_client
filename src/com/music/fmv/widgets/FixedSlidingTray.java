package com.music.fmv.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 9/30/13
 * Time: 9:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class FixedSlidingTray extends SlidingTray{
    private int deviceHeight;

    public FixedSlidingTray(Context context, View handle, View content, Orientation orientation) {
        super(context, handle, content, orientation);
        init();
    }

    public FixedSlidingTray(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FixedSlidingTray(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        deviceHeight = wm.getDefaultDisplay().getHeight();
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
        Opener opener = new Opener();
        opener.start();
    }

    @Override
    public void animateClose() {
        super.animateClose();
    }


   private class Opener extends Thread {
       @Override
       public void run() {

           //Make content visible
           post(new Runnable() {
               @Override
               public void run() {
                   prepareContent();
                   getContent().setVisibility(VISIBLE);
                   invalidate();
               }
           });

           //calculate height for scroll
           int maxY = (getHeight() - getHandle().getHeight()) * -1;
           int currY = getScrollY();
           while (currY > maxY){
               currY -= 25;
                if (currY <= maxY){
                    screollInUI(0, maxY);
                }else screollInUI(0, currY);
               try {
                   sleep(5);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }

           post(new Runnable() {
               @Override
               public void run() {
                   open();
                   scrollTo(0,0);
               }
           });
       }
   }

    private void screollInUI(final int x, final int y) {
        post(new Runnable() {
            @Override
            public void run() {
                scrollTo(x, y);
            }
        });
    }
}
