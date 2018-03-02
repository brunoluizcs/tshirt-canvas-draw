package com.example.brunosantos.draganddrop;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.IntDef;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TouchableImageView extends AppCompatImageView  {

    private final ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 1.f;

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 3;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NONE,DRAG,ZOOM})
    @interface ScaleMode{}

    @ScaleMode private int mMode = NONE;

    private float mStartX = 0f;
    private float mStartY = 0f;
    private float mDx     = 0f;
    private float mDy     = 0f;
    private float mPrevDx = 0f;
    private float mPrevDy = 0f;


    private OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mMode = DRAG;
                    mStartX = event.getX() - mPrevDx;
                    mStartY = event.getY() - mPrevDy;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mMode == DRAG){
                        mDx = event.getX() - mStartX;
                        mDy = event.getY() - mStartY;
                    }
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    mMode = ZOOM;
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    mMode = DRAG;
                    break;
                case MotionEvent.ACTION_UP:
                    mMode = NONE;
                    mPrevDx = mDx;
                    mPrevDy = mDy;
                    break;
            }
            mScaleDetector.onTouchEvent(event);

            if (mMode == DRAG || mMode == ZOOM) {
                getParent().requestDisallowInterceptTouchEvent(true);
                applyScaleAndTranslation();
            }
            return true;
        }
    };

    private void applyScaleAndTranslation() {
        setScaleX(mScaleFactor);
        setScaleY(mScaleFactor);
        setTranslationX(mDx);
        setTranslationY(mDy);
    }

    public TouchableImageView(Context context) {
        super(context);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        setOnTouchListener(mOnTouchListener);
        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.scale(mScaleFactor, mScaleFactor);
        canvas.restore();
    }


    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();

            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));

            //invalidate();
            return true;
        }
    }

}
