package com.example.xyzreader.customview;

import android.content.Context;
import android.graphics.Typeface;


/**
 * Helper class to load custom fonts
 *
 * Created by steffy on 29/10/2017.
 */

public class TypeFaceLoader {
    private static final String CUSTOM_FONT = "Rosario-Regular.ttf";
    private final Typeface customFont;
    private static TypeFaceLoader instance;

    private TypeFaceLoader(Context context) {
        customFont = Typeface.createFromAsset(context.getAssets(), CUSTOM_FONT);
    }


    public static TypeFaceLoader getInstance(Context context){
        if (instance == null){
            instance = new TypeFaceLoader(context);
        }
        return instance;
    }

    public Typeface getCustomFont(){
        return customFont;
    }
}
