package com.music.fms.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.music.fms.R;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 11/29/13
 * Time: 9:23 AM
 * To change this template use File | Settings | File Templates.
 */

public class PlayerSliding extends ViewGroup{
    public static final int ANIMATION_TIME = 400;

    private View mHandle;
    private View mContent;

    private int handleID;
    private int contentID;

    private ScrollerCompat scroller;
    private SliderListener listener;

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

        scroller  = ScrollerCompat.create(getContext());
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            int y = scroller.getCurrY();
            if (y == scroller.getFinalY() && listener != null){
                if (y == 0){
                    listener.onClose();
                }else {
                    listener.onOpened();
                }
            }
            scrollTo(scroller.getCurrX(), y);
        }
    }

    public void open(){
        scroller.startScroll(0, 0, 0, -mContent.getMeasuredHeight(), ANIMATION_TIME);
    }

    public void close(){
        scroller.startScroll(0, getScrollY(), 0, mContent.getMeasuredHeight(), ANIMATION_TIME);
    }

    public View getHandle(){
        return mHandle;
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
        return getScrollY() < 0 ;
    }

    public void setListener(SliderListener listener) {
        this.listener = listener;
    }

    public static interface SliderListener{
        public void onOpened();
        public void onClose();
    }
}