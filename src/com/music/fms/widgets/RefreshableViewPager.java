package com.music.fms.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import com.music.fms.R;
import com.music.fms.adapters.FragmentAdapter;

/**
 * User: Vitalii Lebedynskyi
 * Date: 8/5/13
 * Time: 12:30 PM
 */
public class RefreshableViewPager extends ViewPager {
    private boolean canScroll = true;
    private boolean isAutoRefresh = false;
    private OnPageChangeListener foreignPageListener;
    private int refreshDelay;

    public RefreshableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.canScroll && super.onTouchEvent(event);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RefreshableViewPager);
        this.canScroll = arr.getBoolean(R.styleable.RefreshableViewPager_scrollable, true);
        this.isAutoRefresh = arr.getBoolean(R.styleable.RefreshableViewPager_autoRefresh, true);
        this.refreshDelay = arr.getInt(R.styleable.RefreshableViewPager_refreshDelay, 1);

        super.setOnPageChangeListener(nativePageListener);

        setFadingEdgeLength(0);
    }

    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.foreignPageListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.canScroll && super.onInterceptTouchEvent(event);
    }

    public boolean isAutoRefresh() {
        return isAutoRefresh;
    }

    public void setAutoRefresh(boolean autoRefresh) {
        isAutoRefresh = autoRefresh;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
        refreshItem(item);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
        refreshItem(item);
    }

    private void refreshItem(int item) {
        if (!isAutoRefresh) return;

        final Fragment fr = getFragment(item);
        if (fr != null && fr instanceof Refreshable){
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((Refreshable) fr).refresh();
                }
            }, refreshDelay);
        }
    }

    public Fragment getFragment(int pos){
        PagerAdapter adapter = getAdapter();
        if (adapter instanceof FragmentAdapter){
            return ((FragmentAdapter) adapter).getItem(pos);
        }
        return null;
    }

    public abstract static class BasePageChangeListener implements OnPageChangeListener{
        @Override public void onPageScrollStateChanged(int i) {}
        @Override public void onPageScrolled(int i, float v, int i2) {}
        @Override public void onPageSelected(int i) {}
    }

    private BasePageChangeListener nativePageListener = new BasePageChangeListener() {
        @Override
        public void onPageSelected(int i) {
            refreshItem(i);
            if (foreignPageListener != null) foreignPageListener.onPageSelected(i);
        }

        @Override
        public void onPageScrolled(int i, float v, int i2) {
            if (foreignPageListener != null) foreignPageListener.onPageScrolled(i, v, i2);
        }

        @Override
        public void onPageScrollStateChanged(int i) {
            if (foreignPageListener != null) foreignPageListener.onPageScrollStateChanged(i);
        }
    };

    public static interface Refreshable {
        public void refresh();
    }
}
