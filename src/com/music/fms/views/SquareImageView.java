package com.music.fms.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.music.fms.R;

public class SquareImageView extends ImageView {
    private Drawable overlay;

    public SquareImageView(Context paramContext) {
        super(paramContext);
        init();
    }

    public SquareImageView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public SquareImageView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void init() {
        this.overlay = getResources().getDrawable(R.drawable.cover_border);
        Rect localRect = new Rect();
        this.overlay.getPadding(localRect);
        setPadding(localRect.left, localRect.top, localRect.right, localRect.bottom);
    }

    public void onDraw(Canvas canvas) {
        canvas.save();
        canvas.clipRect(new Rect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom()));
        super.onDraw(canvas);
        canvas.restore();
        this.overlay.setBounds(0, getPaddingTop(), getWidth(), getHeight() - (getPaddingBottom()));
        this.overlay.draw(canvas);
    }
}
