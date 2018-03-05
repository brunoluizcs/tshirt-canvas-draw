package com.example.brunosantos.draganddrop.stamppicker;


import java.util.List;

public class Collection {

    private List<Stamp> mStamps;

    public Collection(List<Stamp> mStamps) {
        this.mStamps = mStamps;
    }

    public List<Stamp> getStamps() {
        return mStamps;
    }
}
