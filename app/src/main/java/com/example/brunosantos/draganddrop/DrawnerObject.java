package com.example.brunosantos.draganddrop;

import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;

public class DrawnerObject {
    @DrawableRes private int bitmap;
    private float left;
    private float top;
    private float height;
    private float width;
    private float scale = 1.f;
    private float rotateDegrees = 0f;
    private int zIndex = 0;

    public DrawnerObject(@DrawableRes int bitmap, float left, float top, float width, float height, float scale) {
        this.bitmap = bitmap;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.scale = scale;
    }

    public Rect getRect(){
        int right = Math.round(left + getWidth());
        int bottom = Math.round(top + getHeight());
        return new Rect(Math.round(left),Math.round(top),right,bottom);
    }

    public RectF getRectF(){
        int right = Math.round(left + getWidth());
        int bottom = Math.round(top + getHeight());
        return new RectF(Math.round(left),Math.round(top),right,bottom);
    }

    public int getBitmap() {
        return bitmap;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getHeight() {
        return height * scale;
    }

    public float getWidth() {
        return width * scale;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public float getScale() {
        return scale;
    }

    public float getRotateDegrees() {
        return rotateDegrees;
    }

    public void setRotateDegrees(float rotateDegrees) {
        this.rotateDegrees = rotateDegrees;
    }

    public int getzIndex() {
        return zIndex;
    }

    public void incZIndex(){
        zIndex++;
    }

    public void decZIndex(){
        zIndex--;
    }



}
