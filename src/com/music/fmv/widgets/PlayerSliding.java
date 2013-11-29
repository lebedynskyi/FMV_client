package com.music.fmv.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.music.fmv.R;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 11/29/13
 * Time: 9:23 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerSliding extends ViewGroup{
    public static final int ANIMATION_TIME = 200;
    public static final int SLEEP_TIME = 15;

    private View mHandle;
    private View mContent;

    private int handleID;
    private int contentID;

    private boolean isLocked;

    public PlayerSliding(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerSliding(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.PlayerSliding);
        handleID = arr.getResourceId(R.styleable.PlayerSliding_handleID, -1);
        contentID = arr.getResourceId(R.styleable.PlayerSliding_contentID, -1);
    }

    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {
        mContent.layout(0, 0 - mContent.getMeasuredHeight(), mContent.getMeasuredWidth(), 0);
        mHandle.layout(0, 0, mHandle.getMeasuredWidth(), mHandle.getMeasuredHeight());
    }

    @Override
    protected void onFinishInflate() {
        mHandle = findViewById(handleID);
        mContent = findViewById(contentID);

        if (mHandle == null || mContent == null){
            throw new IllegalStateException("View must contain reference on exist child");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        measureChild(mHandle, widthMeasureSpec, heightMeasureSpec);
        measureChild(mContent, widthMeasureSpec, heightMeasureSpec - mHandle.getMeasuredHeight());
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    public boolean isOpen() {
        return getScrollY() <= mContent.getMeasuredHeight() * -1;
    }

    public void open(){
        if (isLocked) return;
        new Thread(new Opener()).start();
    }

    public void close(){
        if (isLocked) return;
        new Thread(new Closer()).start();
    }

    public View getHandle(){
        return mHandle;
    }

    private class Opener implements Runnable{
        @Override
        public void run() {
            int DELTA_Y = calculateDeltaY();
            isLocked = true;
            int maxScrollY = mContent.getMeasuredHeight() * -1;
            while (getScrollY() > maxScrollY){
                int futureY = getScrollY() - DELTA_Y;
                if (futureY < maxScrollY){
                    futureY = maxScrollY;
                }

                final int finalFutureY = futureY;
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        scrollTo(getScrollX(), finalFutureY);
                    }
                });
                try { Thread.sleep(SLEEP_TIME); } catch (InterruptedException ignored) {}
            }

            isLocked = false;
        }
    }


    private class Closer implements Runnable{
        @Override
        public void run() {
            int DELTA_Y = calculateDeltaY();
            isLocked = true;
            while (getScrollY() < 0){
                int futureY = getScrollY() + DELTA_Y;
                if (futureY > 0){
                    futureY = 0;
                }

                final int finalFutureY = futureY;
                getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        scrollTo(getScrollX(), finalFutureY);
                    }
                });
                try { Thread.sleep(SLEEP_TIME); } catch (InterruptedException ignored) {}
            }

            isLocked = false;
        }
    }

    private int calculateDeltaY(){
        int height = mContent.getMeasuredHeight();
        return  height / ANIMATION_TIME * SLEEP_TIME;
    }
}
