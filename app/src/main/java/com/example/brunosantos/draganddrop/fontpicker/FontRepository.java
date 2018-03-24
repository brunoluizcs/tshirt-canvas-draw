package com.example.brunosantos.draganddrop.fontpicker;


import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Locale;

public class FontRepository {

    private static final String TAG = FontRepository.class.getSimpleName();

    public static String[] getFonts(Context context)  {
        try {
             final String FONT_DIR = "fonts";
             String[] fonts = context.getAssets().list(FONT_DIR);
             String[] fontsPath = new String[fonts.length];
             for (int i = 0; i <= fonts.length -1; i++){
                 fontsPath[i] = String.format(Locale.getDefault(),"%s/%s",FONT_DIR,fonts[i]);
             }
             return fontsPath;
        } catch (IOException e) {
            Log.e(TAG, "getFonts: ", e);
        }
        return new String[]{};
    }
}
