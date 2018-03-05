package com.example.brunosantos.draganddrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.brunosantos.draganddrop.stamppicker.Stamp;

public class DrawnerObject {
    private Stamp stamp;
    private float left;
    private float top;
    private float height;
    private float width;
    private float scale = 1.f;
    private float rotateDegrees = 0f;
    private int zIndex = 0;
    private float density = 0f;
    private int color;
    private String hexaColorString;


    public DrawnerObject(Stamp stamp, float left, float top, float width, float height, float scale,float density) {
        this.stamp = stamp;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.density = density;
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

    public Bitmap getBitmap(Context context) {
        return stamp.getBitmap(context);
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public float getHeight() {
        return height * scale * density;
    }

    public float getWidth() {
        return width * scale * density;
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

    public void setColor(int color) {
        this.color = color;
        this.hexaColorString = "0x" + Integer.toHexString(color);
    }

    public int getColor(){
        return this.color;
    }

    public float getDensity() {
        return density;
    }
}
