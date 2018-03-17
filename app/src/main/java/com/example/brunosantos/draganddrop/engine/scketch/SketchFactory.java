package com.example.brunosantos.draganddrop.engine.scketch;


import android.support.annotation.IntDef;

import com.example.brunosantos.draganddrop.engine.elements.ElementDrawable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SketchFactory {

    public static final int TSHIRT = 0;
    public static final int SWEATSHIRT = 1;
    private TshirtElement mTShirtSketch;
    private SweatshirtElement mSweatShirtSketch;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({TSHIRT, SWEATSHIRT})
    public @interface SketchType{}

    private static SketchFactory instance;

    private SketchFactory(){}

    public static SketchFactory getInstance(){
        if (instance == null){
            instance = new SketchFactory();
        }
        return instance;
    }

    public ElementDrawable getSketch(@SketchType int type){
        switch (type){
            case TSHIRT:
                return getTshirt();
            case SWEATSHIRT:
                return getSweatShirtSketch();
            default:
                throw new RuntimeException("Background sketch not found");
        }
    }

    private ElementDrawable getTshirt(){
        if (mTShirtSketch == null){
            mTShirtSketch = new TshirtElement();
        }
        return mTShirtSketch;
    }

    private ElementDrawable getSweatShirtSketch(){
        if (mSweatShirtSketch == null){
            mSweatShirtSketch = new SweatshirtElement();
        }
        return mSweatShirtSketch;
    }
}
