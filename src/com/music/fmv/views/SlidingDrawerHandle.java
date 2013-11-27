package com.music.fmv.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import com.music.fmv.R;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 11/27/13
 * Time: 5:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SlidingDrawerHandle extends RelativeLayout{
    private View mainView;

    public SlidingDrawerHandle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlidingDrawerHandle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context c){
        inflate(c, R.layout.slider_handle_layout, this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
