package com.example.brunosantos.draganddrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.brunosantos.draganddrop.engine.DrawnerEngine;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends SurfaceView implements
        Runnable,
        View.OnTouchListener{

    private static final String TAG = CanvasView.class.getSimpleName();


    private SurfaceHolder mHolder;
    private Thread mRenderThread = null;
    private boolean mRunning = false;
    private ScaleGestureDetector mScaleDetector;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        final int action = event.getAction();

        switch (action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                DrawnerEngine.getInstance().actionDown(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                DrawnerEngine.getInstance().actionMove(event);
                return true;
            case MotionEvent.ACTION_UP:
                DrawnerEngine.getInstance().actionUp(event);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                DrawnerEngine.getInstance().actionPointerDown(event);
                return true;
            case MotionEvent.ACTION_POINTER_UP:
                DrawnerEngine.getInstance().actionPointerUp(event);
                return true;
        }
        return false;
    }


    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mScaleDetector = new ScaleGestureDetector(getContext(),
                new ScaleListener());
        mHolder = getHolder();
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        return true;
    }

    public void pause(){
        mRunning = false;
        mRenderThread.interrupt();
    }

    public void resume(){
        mRunning = true;
        if (mRenderThread == null) {
            mRenderThread = new Thread(this);
        }
        mRenderThread.start();
    }

    @Override
    public void run() {
        while(mRunning){
            if (! mHolder.getSurface().isValid())
                continue;

            Canvas canvas = mHolder.lockCanvas();
            if (canvas != null) {
                synchronized (mHolder){
                    DrawnerEngine.getInstance().draw(getContext(),canvas);
                }
                mHolder.unlockCanvasAndPost(canvas);
            }

            try{
                //Thread.sleep(12);//60 fps
                Thread.sleep(45);//22 fps
            }catch (InterruptedException ex){
            }
        }
    }


    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            DrawnerEngine.getInstance().scale(detector.getScaleFactor());
            return true;
        }
    }



}
