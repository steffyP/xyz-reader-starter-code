package com.example.xyzreader.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by steffy on 29/10/2017.
 */

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        this(context, null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface font = TypeFaceLoader.getInstance(context).getCustomFont();
        if(font != null){
            this.setTypeface(font);
        }

    }


}
