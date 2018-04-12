package com.example.brunosantos.draganddrop.stamppicker;


import android.content.Context;
import android.text.TextUtils;

import com.example.brunosantos.draganddrop.engine.stamp.PictureStamp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CollectionRepository {
    private static final String TAG = CollectionRepository.class.getSimpleName();

    public static Collection getCollection(Context context, String collectionName){
        if (TextUtils.isEmpty(collectionName)){
            return null;
        }

        List<PictureStamp> pictureStamps = new ArrayList<>();
        try {
            String[] collection = context.getAssets().list(collectionName);
            for (int i = 0; i <= collection.length -1; i++) {
                String asset = String.format(Locale.getDefault(), "%s/%s",collectionName, collection[i]);
                pictureStamps.add(new PictureStamp(asset));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Collection(pictureStamps);
    }
}
