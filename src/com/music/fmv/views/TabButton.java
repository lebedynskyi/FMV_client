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
//    private TextView textView;
    private ImageView button;
    private ClickCallBack callBack;

    public TabButton(Context context) {
        super(context);
        view = inflate(context, R.layout.tab_button, this);
        initComponentsUI();
    }

    public TabButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        view = inflate(context,R.layout.tab_button, this);
        initComponentsUI();
    }

    public TabButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        view = inflate(context,R.layout.tab_button, this);
        initComponentsUI();
    }

    @Override
    public View getRootView() {
        return view;
    }

    private void initComponentsUI(){
//        textView = (TextView) view.findViewById(R.id.tab_text);
        button = (ImageView) view.findViewById(R.id.tab_btn);
        setOnClickListener(clickListener);
    }

    public void initUI(Drawable imageSelector, String text, ClickCallBack callBack){
        this.callBack = callBack;
//        textView.setText(text);
        button.setBackgroundDrawable(imageSelector);
    }

    @Override
    public void setSelected(boolean selected) {
        button.setSelected(selected);
//        textView.setSelected(selected);
        if (selected){
            view.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.bottom_selected));
        }else {
            view.setBackgroundDrawable(null);
        }
    }

    public interface ClickCallBack{
        public void onClick(View v);
    }

    private OnClickListener clickListener =new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (callBack != null){
                callBack.onClick(TabButton.this);
            }
        }
    };
}
