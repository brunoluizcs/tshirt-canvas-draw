package com.example.brunosantos.draganddrop.engine.elements;


import android.graphics.Rect;

public interface ElementStamp {

    void zoomIn();
    void zoomOut();
    void rotateLeft();
    void rotateRight();
    void flipToFront();
    void flipToBack();
    void move(float x, float y);
    boolean intersect(Rect rect);
}
