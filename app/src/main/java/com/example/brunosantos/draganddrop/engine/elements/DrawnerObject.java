package com.example.brunosantos.draganddrop.engine.elements;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;

import com.example.brunosantos.draganddrop.engine.stamp.Stamp;

public class DrawnerObject implements
        ElementDrawable,
        ElementStamp{

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
    private boolean flipHorizontal = false;


    public DrawnerObject(Stamp stamp, float left, float top, float width, float height,
                         float scale, float density, boolean flipHorizontal) {
        this.stamp = stamp;
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.density = density;
        this.flipHorizontal = flipHorizontal;
    }

    public RectF getRectF(){
        int right = ! isFlipHorizontal() ? Math.round(getLeft() + getWidth()) : Math.round(getLeft() - getWidth()) ;
        int bottom = Math.round(top + getHeight());
        RectF rectF = new RectF(Math.round(getLeft()),Math.round(top),right,bottom);
        Matrix m = new Matrix();
        m.setRotate(getRotateDegrees(), rectF.centerX(), rectF.centerY());
        m.mapRect(rectF);
        return rectF;
    }


    public float getLeft() {
        if (isFlipHorizontal()){
            return left + left - (left /2);
        }
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

    protected void setLeft(float left) {
        this.left = left;
    }

    public void setTop(float top) {
        this.top = top;
    }

    protected void setScale(float scale){
        this.scale = scale;
    }

    protected float getScale() {
        return scale;
    }

    public float getRotateDegrees() {
        return rotateDegrees;
    }

    protected void setRotateDegrees(float rotateDegrees) {
        this.rotateDegrees = rotateDegrees;
    }

    public int getzIndex() {
        return zIndex;
    }

    protected void incZIndex(){
        zIndex++;
    }

    protected void decZIndex(){
        zIndex--;
    }


    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public int getColor(){
        return this.color;
    }

    public float getDensity() {
        return density;
    }

    public void setFlipHorizontal(boolean flip){
        this.flipHorizontal = flip;
    }

    public boolean isFlipHorizontal(){
        return flipHorizontal;
    }

    @Override
    public void draw(Context context, Canvas canvas) {
        stamp.draw(context,canvas,getWidth(),getHeight(),getLeft(),getTop(),
                getRotateDegrees(),getColor(),getDensity(),isFlipHorizontal());

    }

    @Override
    public void zoomIn() {
        float scaleFactor = getScale();
        scaleFactor *= 1.1f;
        scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
        setScale(scaleFactor);
    }

    @Override
    public void zoomOut() {
        float scaleFactor = getScale();
        scaleFactor -= scaleFactor * 0.1f;
        scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
        setScale(scaleFactor);
    }

    @Override
    public void rotateLeft() {
        if (! isFlipHorizontal()) {
            float degrees = getRotateDegrees();
            degrees -= 3 * 10.0f;
            setRotateDegrees(degrees);
        }else{
            float degrees = getRotateDegrees();
            degrees += 3 * 10.0f;
            setRotateDegrees(degrees);
        }
    }

    @Override
    public void rotateRight() {
        if ( ! isFlipHorizontal()) {
            float degrees = getRotateDegrees();
            degrees += 3 * 10.0f;
            setRotateDegrees(degrees);
        }else{
            float degrees = getRotateDegrees();
            degrees -= 3 * 10.0f;
            setRotateDegrees(degrees);
        }
    }

    @Override
    public void flipToFront() {
        incZIndex();
    }

    @Override
    public void flipToBack() {
        decZIndex();
    }

    public void flipHorizontal(){
        setFlipHorizontal(! isFlipHorizontal());
    }

    @Override
    public void move(float x, float y) {
        setLeft(x - getWidth()/2);
        setTop(y - getHeight()/2);
    }

    @Override
    public boolean intersect(RectF rect) {
        return getRectF().intersect(rect);
    }

    @Override
    public void scale(float factor) {
        float scaleFactor = getScale();
        scaleFactor *= factor;
        scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
        setScale(scaleFactor);
    }

    @Override
    public void setAsset(String assetPath) {
        stamp.setAsset(assetPath);
    }
}
