package com.example.brunosantos.draganddrop.engine;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.MotionEvent;

import com.example.brunosantos.draganddrop.DrawnerObject;
import com.example.brunosantos.draganddrop.R;
import com.example.brunosantos.draganddrop.stamppicker.Stamp;

import java.util.ArrayList;
import java.util.List;

public class DrawnerEngine {

    private static final String TAG = DrawnerEngine.class.getSimpleName();
    private static DrawnerEngine instance;

    private static final int NONE = 0;
    private static final int MOVING = 1;
    private static final int ZOOM = 2;

    /*Color for background t-shirt*/
    private int mSketchColor = Color.WHITE;
    private String mSketchColorString = "#ffffff";

    @State
    private static int mState;
    private int mLastColor = Color.BLACK;




    @IntDef({NONE,MOVING, ZOOM})
    @interface State{}


    public static DrawnerEngine getInstance() {
        if (instance == null){
            instance = new DrawnerEngine();
        }
        return instance;
    }

    private List<DrawnerObject> mDrawnerObjectList = new ArrayList<>();
    private DrawnerObject mCurrentObject = null;

    private boolean findElement(int x, int y) {
        Rect rect1 = new Rect(x, y, x + 46, y + 46);
        for (DrawnerObject object : mDrawnerObjectList){
            mCurrentObject = null;
            if (object.getRect().intersect(rect1)){
                mState = MOVING;
                mCurrentObject = object;
                break;
            }
        }
        String objetoValido = mCurrentObject!=null ? "Valido" : "Invalido";
        Log.d(TAG, "onTouch: " + objetoValido);
        return mCurrentObject != null ;
    }

    public void actionDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (findElement((int) x, (int) y)){
            mState = MOVING;
        }
    }

    public void actionMove(MotionEvent event) {
        Log.d(TAG, "onTouch: movendo");
        if (mCurrentObject != null && mState == MOVING){
            mCurrentObject.setLeft(event.getX() - mCurrentObject.getWidth()/2);
            mCurrentObject.setTop(event.getY() - mCurrentObject.getHeight()/2);
        }
    }

    public void actionUp(MotionEvent event) {
        mState = NONE;
    }

    public void actionPointerUp(MotionEvent event) {
        mState = NONE;
    }

    public void actionPointerDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (findElement((int) x, (int) y)){
            mState = ZOOM;
        }
    }

    synchronized public void zoomIn(){
        if (mCurrentObject != null){
            float scaleFactor = mCurrentObject.getScale();
            scaleFactor *= 1.1f;
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            mCurrentObject.setScale(scaleFactor);
        }
    }

    synchronized public void zoomOut(){
        if (mCurrentObject != null){
            float scaleFactor = mCurrentObject.getScale();
            scaleFactor -= scaleFactor * 0.1f;
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            mCurrentObject.setScale(scaleFactor);
        }
    }

    synchronized public void rotateLeft(){
        if (mCurrentObject != null){
            float degrees = mCurrentObject.getRotateDegrees();
            degrees -= 1 * 5.0f;
            mCurrentObject.setRotateDegrees(degrees);
        }
    }

    synchronized public void rotateRight(){
        if (mCurrentObject != null){
            float degrees = mCurrentObject.getRotateDegrees();
            degrees += 1 * 5.0f;
            mCurrentObject.setRotateDegrees(degrees);
        }
    }

    synchronized public void flipToFront() {
        if (mCurrentObject != null){
            mCurrentObject.incZIndex();
            sortDrawners(mDrawnerObjectList);
        }
    }

    synchronized public void flipToBack() {
        if (mCurrentObject != null){
            mCurrentObject.decZIndex();
            sortDrawners(mDrawnerObjectList);
        }
    }

    synchronized public void setColor(int color) {
        if (mCurrentObject != null){
            mCurrentObject.setColor(color);
            mLastColor = color;
        }
    }

    public int getLastColor() {
        return mLastColor;
    }

    synchronized public void addStamp(Stamp stamp, float left, float top, float width, float height,float density){
        mDrawnerObjectList.add(new DrawnerObject(stamp,left,top,width,height,1.f, density));
    }

    synchronized public void delete(){
        if (mCurrentObject != null){
            mDrawnerObjectList.remove(mCurrentObject);
            mCurrentObject = null;
        }
    }


    synchronized public void sortDrawners(List<DrawnerObject> drawnerObjects){
        for(int i = 0; i <= drawnerObjects.size() -1; i++){
            DrawnerObject current = drawnerObjects.get(i);
            if (i > 0){
                DrawnerObject comparable = drawnerObjects.get(i -1);
                if (current.getzIndex() <= comparable.getzIndex()){
                    drawnerObjects.set(i,comparable);
                    drawnerObjects.set(i - 1,current);
                }
            }
        }
    }

    synchronized private void drawTShirt(Context context, Canvas canvas,Paint paint){
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.t_shirt_front3);
        canvas.drawBitmap(bitmap,0,0,paint);
    }

    synchronized private void drawObjects(Context context, Canvas canvas, Paint paint){

        for (DrawnerObject drawnerObject : mDrawnerObjectList) {
            try{
                Bitmap bitmap = drawnerObject.getBitmap(context);
                bitmap = Bitmap.createScaledBitmap(bitmap,
                        Math.round(drawnerObject.getWidth()),
                        Math.round(drawnerObject.getHeight()),false);

                Matrix matrix = new Matrix();
                matrix.preRotate(drawnerObject.getRotateDegrees());

                bitmap = Bitmap.createBitmap(bitmap,0,0,
                        Math.round(drawnerObject.getWidth()),
                        Math.round(drawnerObject.getHeight()),
                        matrix,false);

                paint.setColorFilter(new PorterDuffColorFilter(drawnerObject.getColor(), PorterDuff.Mode.SRC_ATOP));
                canvas.drawBitmap(bitmap,drawnerObject.getLeft(),drawnerObject.getTop(),paint);

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
            }catch (IllegalArgumentException e){
                Log.e(TAG, "drawObjects: ", e);
            }
        }
    }


    synchronized private void drawObjectEdge(Canvas canvas, Paint paint) {
        if (mCurrentObject != null){
            canvas.drawRoundRect(mCurrentObject.getRectF(),10,10, paint);
        }
    }

    synchronized public void draw(Context context, Canvas canvas) {
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),
                PaintFactory.getInstance().getBackgroundPaint(context));

        drawTShirt(context, canvas,PaintFactory.getInstance()
                .getTShirtPaint(DrawnerEngine.getInstance().getSketchColor()));
        
        drawObjects(context, canvas, PaintFactory.getInstance().getPaint());
        drawObjectEdge(canvas,PaintFactory.getInstance().getEdgePaint(context));
    }

    public void scale(float factor) {
        if (mCurrentObject != null){
            float scaleFactor = mCurrentObject.getScale();
            scaleFactor *= factor;
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            mCurrentObject.setScale(scaleFactor);
        }
    }

    public int getSketchColor() {
        return mSketchColor;
    }

    public void setSketchColor(int mSketchColor) {
        this.mSketchColor = mSketchColor;
        this.mSketchColorString = Integer.toHexString(mSketchColor);
    }
}
