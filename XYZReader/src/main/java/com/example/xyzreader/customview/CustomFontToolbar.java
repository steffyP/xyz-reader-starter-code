package com.example.xyzreader.customview;


import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by steffy on 29/10/2017.
 */

public class CustomFontToolbar extends Toolbar {
    private TextView mTitleTextViewReference;
    private Typeface mCustomTypeFont;

    public CustomFontToolbar(Context context) {
        super(context);
        setCustomFontToTitle(context);
    }


    public CustomFontToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setCustomFontToTitle(context);

    }

    public CustomFontToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomFontToTitle(context);
    }

    private void setCustomFontToTitle(Context context) {
        this.mTitleTextViewReference = getTextViewWithReflection("mTitleTextView");
        this.mCustomTypeFont = TypeFaceLoader.getInstance(context).getCustomFont();
        if (mTitleTextViewReference != null && mCustomTypeFont != null) {
            mTitleTextViewReference.setTypeface(mCustomTypeFont);
        }
    }


    private TextView getTextViewWithReflection(String viewName) {
        TextView titleTextView = null;

        try {
            Field f = this.getClass().getSuperclass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(this);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return titleTextView;
    }


}
