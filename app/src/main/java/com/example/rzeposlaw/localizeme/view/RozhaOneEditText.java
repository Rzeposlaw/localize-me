package com.example.rzeposlaw.localizeme.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class RozhaOneEditText extends AppCompatEditText {

    public RozhaOneEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RozhaOneEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RozhaOneEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/RozhaOne-Regular.ttf");
            setTypeface(tf);
        }
    }

}