package com.example.brunosantos.draganddrop.engine;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.IntDef;
import android.util.Log;
import android.view.MotionEvent;

import com.example.brunosantos.draganddrop.engine.elements.BackgroundElement;
import com.example.brunosantos.draganddrop.engine.elements.DrawnerObject;
import com.example.brunosantos.draganddrop.engine.elements.ElementDrawable;
import com.example.brunosantos.draganddrop.engine.scketch.SketchFactory;
import com.example.brunosantos.draganddrop.stamppicker.Stamp;

import java.util.ArrayList;
import java.util.List;

public class DrawnerEngine {

    private static final String TAG = DrawnerEngine.class.getSimpleName();
    private static DrawnerEngine instance;

    private static final int NONE = 0;
    private static final int MOVING = 1;
    private static final int ZOOM = 2;

    private ElementDrawable mSketch;
    private ElementDrawable mBackground;

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

    public DrawnerEngine() {
        mBackground = new BackgroundElement();
        setSketchType(SketchFactory.TSHIRT);
    }

    private List<DrawnerObject> mDrawnerObjectList = new ArrayList<>();
    private DrawnerObject mCurrentObject = null;

    private boolean findElement(int x, int y) {
        Rect rect1 = new Rect(x, y, x + 46, y + 46);
        for (DrawnerObject object : mDrawnerObjectList){
            mCurrentObject = null;
            if (object.intersect(rect1)){
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
            mCurrentObject.move(event.getX(),event.getY());
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
            mCurrentObject.zoomIn();
        }
    }

    synchronized public void zoomOut(){
        if (mCurrentObject != null){
            mCurrentObject.zoomOut();
        }
    }

    synchronized public void rotateLeft(){
        if (mCurrentObject != null){
            mCurrentObject.rotateLeft();
        }
    }

    synchronized public void rotateRight(){
        if (mCurrentObject != null){
            mCurrentObject.rotateRight();
        }
    }

    synchronized public void flipToFront() {
        if (mCurrentObject != null){
            mCurrentObject.flipToFront();
            sortDrawners(mDrawnerObjectList);
        }
    }

    synchronized public void flipToBack() {
        if (mCurrentObject != null){
            mCurrentObject.flipToBack();
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


    synchronized private void sortDrawners(List<DrawnerObject> drawnerObjects){
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



    synchronized private void drawObjects(Context context, Canvas canvas){

        for (DrawnerObject drawnerObject : mDrawnerObjectList) {
            try{
                drawnerObject.draw(context,canvas,Color.BLACK);
            }catch (IllegalArgumentException e){
                Log.e(TAG, "drawObjects: ", e);
            }
        }
    }


    synchronized private void drawBackground(Context context, Canvas canvas){
        mBackground.draw(context,canvas);
    }

    synchronized public void draw(Context context, Canvas canvas) {
        drawBackground(context, canvas);
        drawSketch(context, canvas);
        drawObjects(context, canvas);
    }

    public void scale(float factor) {
        if (mCurrentObject != null){
            float scaleFactor = mCurrentObject.getScale();
            scaleFactor *= factor;
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            mCurrentObject.setScale(scaleFactor);
        }
    }

    synchronized private void drawSketch(Context context, Canvas canvas){
        mSketch.draw(context,canvas);
    }

    public void setSketchColor(int mSketchColor) {
        mSketch.setColor(mSketchColor);
    }

    public void setSketchType(@SketchFactory.SketchType int sketchType){
        this.mSketch = SketchFactory.getInstance().getSketch(sketchType);
    }


}
