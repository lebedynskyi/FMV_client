package com.music.fmv.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.music.fmv.R;

/**
 * Created with IntelliJ IDEA.
 * User: lebed
 * Date: 22.05.13
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public class GlowButton extends RelativeLayout{
    private TextView textView;
    private View baseView;
    private int whiteColor;
    private int textColor;
    private int blackColor;
    private boolean selectable = true;

    public GlowButton(Context context) {
        super(context);
        baseView = inflate(context, R.layout.glow_button, this);
        initUI();
    }

    public GlowButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        baseView = inflate(context, R.layout.glow_button, this);
        initUI();
        initAttr(attrs);
    }

    public GlowButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        baseView = inflate(context, R.layout.glow_button, this);
        initUI();
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.GlowButton);
        this.selectable = arr.getBoolean(R.styleable.GlowButton_selectable, true);
        this.textView.setText(arr.getString(R.styleable.GlowButton_text));
    }

    private void initUI() {
        textView = (TextView) baseView.findViewById(R.id.blue_button_text_view);
        textColor = getContext().getResources().getColor(R.color.blue_button);
        whiteColor = getContext().getResources().getColor(R.color.white);
        blackColor = getContext().getResources().getColor(R.color.black);
    }

    public void setText(String text){
        this.textView.setText(text);
    }

    public String getText(){
        return this.textView.getText().toString();
    }

    public void setTextColor(int color){
        this.textColor = color;
        textView.setTextColor(textColor);
    }

    public void setSelected(boolean selected){
        super.setSelected(selected);
        if (selected){
            glow();
        }else{
            unglow();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!selectable){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    setSelected(true);
                    return true;
                case MotionEvent.ACTION_UP:
                    setSelected(false);
            }
        }
        return super.onTouchEvent(event);
    }

    public void setSelectable(boolean value){
        this.selectable = value;
    }

    private void unglow() {
        textView.setTextColor(whiteColor);
        textView.setShadowLayer(800, 0, 0, whiteColor);
    }

    private void glow() {
        textView.setTextColor(textColor);
        textView.setShadowLayer(8, 0, 0, textColor);
    }
}
