package com.example.rzeposlaw.localizeme.view;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class RozhaOneTextView extends AppCompatTextView {

    public RozhaOneTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RozhaOneTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RozhaOneTextView(Context context) {
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