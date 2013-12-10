package com.music.fmv.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.music.fmv.R;

/**
 * Created with IntelliJ IDEA.
 * User: Lebedynskyi
 * Date: 2/4/13
 * Time: 5:24 PM
 */

public class TabButton extends RelativeLayout {
    private View view;
    private ImageView image;

    public TabButton(Context context) {
        super(context);
        initComponentsUI(context);
    }

    public TabButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initComponentsUI(context);
    }

    public TabButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initComponentsUI(context);
    }

    @Override
    public View getRootView() {
        return view;
    }

    private void initComponentsUI(Context context) {
        view = inflate(context, R.layout.tab_button, this);
        image = (ImageView) view.findViewById(R.id.tab_btn);
    }

    public void initUI(Drawable imageSelector) {
        image.setImageDrawable(imageSelector);
    }

    public interface ClickCallBack {
        public void onClick(View v);
    }
}
