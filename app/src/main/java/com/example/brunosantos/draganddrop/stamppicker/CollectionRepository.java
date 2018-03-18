package com.example.brunosantos.draganddrop.stamppicker;


import com.example.brunosantos.draganddrop.engine.stamp.PictureStamp;

import java.util.ArrayList;
import java.util.List;

public class CollectionRepository {
    private static final String TAG = CollectionRepository.class.getSimpleName();

    public static Collection getCollection(int collectionId){

        List<PictureStamp> pictureStamps = new ArrayList<>();
        if (collectionId == 1){
            pictureStamps.add(new PictureStamp("icons8-3d-glasses-filled-50.png"));
            pictureStamps.add(new PictureStamp("icons8-music-filled-50.png"));
            pictureStamps.add(new PictureStamp("icons8-musical-notes-filled-50.png"));
        }else if(collectionId == 999){
            pictureStamps.add(new PictureStamp("icons8-ski-mask-50.png"));
            pictureStamps.add(new PictureStamp("icons8-unity-filled-50.png"));
        }

        return new Collection(pictureStamps);
    }
}
