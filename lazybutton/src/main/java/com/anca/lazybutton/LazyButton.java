package com.anca.lazybutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by LazyMonster on 11/05/2016.
 */
public class LazyButton extends Button {

    private StateListDrawable mDrawable;
    private CharSequence mText;
    private float mCornerRadius;

    public LazyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public LazyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LazyButton(Context context) {
        super(context);
        init(context, null);
    }

    public void setNormalText(CharSequence text) {
        mText = text;
    }

    public CharSequence getNormalText() {
        return mText;
    }

    private void init(Context c, AttributeSet attrs) {
        mDrawable = new StateListDrawable();
        if (attrs != null) {
            initAttrs(c, attrs);
        }
        mText = getText().toString();
        setBackgroundCompat(mDrawable);
    }

    private void initAttrs(Context c, AttributeSet attrs) {
        TypedArray attr = getTypedArray(c, attrs, R.styleable.FlatButton);
        if (attr == null) {
            return;
        }

        try {
            float defValue = getResources().getDimension((R.dimen.corner_radius));
            mCornerRadius = attr.getDimension(R.styleable.FlatButton_pb_cornerRadius, defValue);

            mDrawable.addState(new int[]{android.R.attr.state_pressed}, createPressedDrawable(attr));
            mDrawable.addState(new int[]{android.R.attr.state_focused}, createPressedDrawable(attr));
            mDrawable.addState(new int[]{android.R.attr.state_selected}, createPressedDrawable(attr));
            mDrawable.addState(new int[]{}, createNormalDrawable(attr));
        } finally {
            attr.recycle();
        }
    }

    protected TypedArray getTypedArray(Context c, AttributeSet attrs, int[] attr) {
        return c.obtainStyledAttributes(attrs, attr);
    }

    private Drawable createPressedDrawable(TypedArray attr) {
        GradientDrawable drawablePressed = (GradientDrawable)
                getResources().getDrawable(R.drawable.rect_pressed).mutate();
        drawablePressed.setCornerRadius(mCornerRadius);

        int blueDark = getResources().getColor(R.color.blue_pressed);
        int colorPressed = attr.getColor(R.styleable.FlatButton_pb_colorPressed, blueDark);
        drawablePressed.setColor(colorPressed);

        return drawablePressed;
    }

    private Drawable createNormalDrawable(TypedArray attr) {
        LayerDrawable drawableNormal = (LayerDrawable)
                getResources().getDrawable(R.drawable.rect_normal).mutate();

        GradientDrawable drawableTop = (GradientDrawable)
                drawableNormal.getDrawable(0).mutate();
        drawableTop.setCornerRadius(mCornerRadius);

        int blueDark = getResources().getColor(R.color.blue_pressed);
        int colorPressed = attr.getColor(R.styleable.FlatButton_pb_colorPressed, blueDark);
        drawableTop.setColor(colorPressed);

        GradientDrawable drawableBottom =
                (GradientDrawable) drawableNormal.getDrawable(1).mutate();
        drawableBottom.setCornerRadius(mCornerRadius);

        int blueNormal = getResources().getColor(R.color.blue_normal);
        int colorNormal = attr.getColor(R.styleable.FlatButton_pb_colorNormal, blueNormal);
        drawableBottom.setColor(colorNormal);

        return drawableNormal;
    }

    private void setBackgroundCompat(Drawable drawable) {
        int pL = getPaddingLeft();
        int pT = getPaddingTop();
        int pR = getPaddingRight();
        int pB = getPaddingBottom();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
        setPadding(pL, pT, pR, pB);
    }
}
