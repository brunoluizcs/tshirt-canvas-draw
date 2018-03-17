package com.example.brunosantos.draganddrop.engine.elements;


import android.content.Context;
import android.graphics.Canvas;


public interface ElementDrawable {
     int getColor();
     void setColor(int color);
     void draw(Context context, Canvas canvas);
}
