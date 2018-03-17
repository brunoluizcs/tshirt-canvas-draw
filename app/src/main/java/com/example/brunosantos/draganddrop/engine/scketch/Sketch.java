package com.example.brunosantos.draganddrop.engine.scketch;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;

import com.example.brunosantos.draganddrop.engine.elements.ElementDrawable;

abstract class Sketch implements ElementDrawable{
    private int mColor = Color.WHITE;

    @Override
    public int getColor() {
        return mColor;
    }

    @Override
    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public abstract void draw(Context context, Canvas canvas);
}
