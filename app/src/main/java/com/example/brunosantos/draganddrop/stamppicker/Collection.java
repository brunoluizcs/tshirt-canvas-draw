package com.example.brunosantos.draganddrop.stamppicker;


import com.example.brunosantos.draganddrop.engine.stamp.PictureStamp;

import java.util.List;

public class Collection {

    private List<PictureStamp> mPictureStamps;

    public Collection(List<PictureStamp> mPictureStamps) {
        this.mPictureStamps = mPictureStamps;
    }

    public List<PictureStamp> getStamps() {
        return mPictureStamps;
    }
}
