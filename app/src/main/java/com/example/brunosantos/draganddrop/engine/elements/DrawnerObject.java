package com.example.brunosantos.draganddrop.engine.elements;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;

import com.example.brunosantos.draganddrop.engine.PaintFactory;
import com.example.brunosantos.draganddrop.stamppicker.Stamp;

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

    private Bitmap getBitmap(Context context) {
        return stamp.getBitmap(context);
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    private float getHeight() {
        return height * scale * density;
    }

    private float getWidth() {
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

    private float getRotateDegrees() {
        return rotateDegrees;
    }

    public void setRotateDegrees(float rotateDegrees) {
        this.rotateDegrees = rotateDegrees;
    }

    public int getzIndex() {
        return zIndex;
    }

    private void incZIndex(){
        zIndex++;
    }

    private void decZIndex(){
        zIndex--;
    }

    public void setColor(int color) {
        this.color = color;
        this.hexaColorString = "0x" + Integer.toHexString(color);
    }

    private int getColor(){
        return this.color;
    }

    public float getDensity() {
        return density;
    }

    @Override
    public void draw(Context context, Canvas canvas, int color) {
        Bitmap bitmap = this.getBitmap(context);
        bitmap = Bitmap.createScaledBitmap(bitmap,
                Math.round(getWidth()),
                Math.round(getHeight()),false);

        Matrix matrix = new Matrix();
        matrix.preRotate(getRotateDegrees());

        bitmap = Bitmap.createBitmap(bitmap,0,0,
                Math.round(getWidth()),
                Math.round(getHeight()),
                matrix,false);

        Paint paint = PaintFactory.getInstance().getPaint();
        paint.setColorFilter(new PorterDuffColorFilter(getColor(), PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap,getLeft(),getTop(),paint);
        canvas.drawRoundRect(getRectF(),10,10, PaintFactory.getInstance().getEdgePaint());
                /*
                Typeface plain = Typeface.createFromAsset(context.getAssets(), "font_waltographUI.ttf");
                Paint p = new Paint();
                p.setStyle(Paint.Style.FILL_AND_STROKE);
                p.setColor(context.getResources().getColor(R.color.colorAccent));
                p.setTypeface(plain);
                p.setTextSize(drawnerObject.getDensity() * 14);
                Rect bounds = new Rect();
                p.getTextBounds("",0,"Sample text".length(),bounds);
                canvas.drawText("Sample text",drawnerObject.getLeft(),drawnerObject.getTop(),p);
                */
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
        float degrees = getRotateDegrees();
        degrees -= 1 * 5.0f;
        setRotateDegrees(degrees);
    }

    @Override
    public void rotateRight() {
        float degrees = getRotateDegrees();
        degrees += 1 * 5.0f;
        setRotateDegrees(degrees);
    }

    @Override
    public void flipToFront() {
        incZIndex();
    }

    @Override
    public void flipToBack() {
        decZIndex();
    }

    @Override
    public void move(float x, float y) {
        setLeft(x - getWidth()/2);
        setTop(y - getHeight()/2);
    }

    @Override
    public boolean intersect(Rect rect) {
        return getRect().intersect(rect);
    }
}
