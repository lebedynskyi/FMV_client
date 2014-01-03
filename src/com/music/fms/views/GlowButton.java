package com.music.fms.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.music.fms.R;

/**
 * Created with IntelliJ IDEA.
 * User: Vitalii Lebedynskyi
 * Date: 22.05.13
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public class GlowButton extends LinearLayout {
    private TextView textView;
    private View baseView;
    private ImageView leftDrawable;

    private int whiteColor;
    private int blueColor;

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
        this.textView.setText(arr.getString(R.styleable.GlowButton_text));
        int left_drawable =  arr.getResourceId(R.styleable.GlowButton_leftDrawable, 0);
        if (left_drawable != 0){
            leftDrawable.setImageDrawable(getContext().getResources().getDrawable(left_drawable));
        }
        textView.setGravity(arr.getInt(R.styleable.GlowButton_text_gravity, Gravity.CENTER_HORIZONTAL));
    }

    private void initUI() {
        textView = (TextView) baseView.findViewById(R.id.blue_button_text_view);
        leftDrawable = (ImageView) findViewById(R.id.left_image);

        blueColor = getContext().getResources().getColor(R.color.blue_button);
        whiteColor = getContext().getResources().getColor(R.color.white);
    }

    public void setText(String text) {
        this.textView.setText(text);
    }

    public String getText() {
        return this.textView.getText().toString();
    }

    public void setBlueColor(int color) {
        this.blueColor = color;
        textView.setTextColor(blueColor);
    }

    private void unglow() {
        textView.setTextColor(whiteColor);
        textView.setShadowLayer(800, 0, 0, whiteColor);
    }

    private void glow() {
        textView.setTextColor(blueColor);
        textView.setShadowLayer(8, 0, 0, blueColor);
    }

    @Override
    protected void drawableStateChanged(){
        super.drawableStateChanged();
        if (isSelected() || isPressed()){
            glow();
        }else{
            unglow();
        }
    }
}
