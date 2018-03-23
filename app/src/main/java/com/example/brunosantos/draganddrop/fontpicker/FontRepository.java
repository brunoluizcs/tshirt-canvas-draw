package com.example.brunosantos.draganddrop.fontpicker;


import android.content.Context;
import android.util.Log;

import java.io.IOException;

public class FontRepository {

    private static final String TAG = FontRepository.class.getSimpleName();

    public static String[] getFonts(Context context)  {
        try {
            return context.getAssets().list("fonts");
        } catch (IOException e) {
            Log.e(TAG, "getFonts: ", e);
        }
        return new String[]{};
    }
}
