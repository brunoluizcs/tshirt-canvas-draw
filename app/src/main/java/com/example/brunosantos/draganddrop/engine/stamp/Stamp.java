package com.example.brunosantos.draganddrop.engine.stamp;


import android.content.Context;
import android.graphics.Canvas;

public interface Stamp {
    void draw(Context context, Canvas canvas, float width, float height,
              float left, float top, float rotateDegrees, int color, float density);
    int getWidth();
    int getHeight();

}
