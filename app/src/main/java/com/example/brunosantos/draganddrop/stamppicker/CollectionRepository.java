package com.example.brunosantos.draganddrop.stamppicker;


import java.util.ArrayList;
import java.util.List;

public class CollectionRepository {
    private static final String TAG = CollectionRepository.class.getSimpleName();

    public static Collection getCollection(int collectionId){

        List<Stamp> stamps = new ArrayList<>();
        if (collectionId == 1){
            stamps.add(new Stamp("icons8-3d-glasses-filled-50.png"));
            stamps.add(new Stamp("icons8-music-filled-50.png"));
            stamps.add(new Stamp("icons8-musical-notes-filled-50.png"));
        }else if(collectionId == 999){
            stamps.add(new Stamp("icons8-ski-mask-50.png"));
            stamps.add(new Stamp("icons8-unity-filled-50.png"));
        }

        return new Collection(stamps);
    }
}
