package com.example.brunosantos.draganddrop.engine;


import android.content.Context;
import android.graphics.Paint;

import com.example.brunosantos.draganddrop.R;

public class PaintFactory {

    private static final String TAG = PaintFactory.class.getSimpleName();

    private static PaintFactory instance;

    private Paint mPaint;
    private Paint mEdgePaint;
    private Paint mBackgroundPaint;
    private Paint mTShirtPaint;

    public static PaintFactory getInstance() {
        if (instance == null){
            instance = new PaintFactory();
        }
        return instance;
    }

    public Paint getPaint(){
        if (mPaint == null){
            mPaint = new Paint();
        }
        return mPaint;
    }

    public Paint getEdgePaint(Context context){
        if (mEdgePaint == null){
            mEdgePaint = new Paint();
            mEdgePaint.setStyle(Paint.Style.STROKE);
            mEdgePaint.setAntiAlias(true);
            mEdgePaint.setStrokeWidth(4);
            mEdgePaint.setColor(context.getResources().getColor(R.color.colorPrimary));
        }
        return mEdgePaint;
    }

    public Paint getBackgroundPaint(Context context){
        if  (mBackgroundPaint == null){
            mBackgroundPaint = new Paint();
            mBackgroundPaint.setColor(context.getResources().getColor(android.R.color.white));
            mBackgroundPaint.setAntiAlias(true);
        }
        return mBackgroundPaint;
    }

    public Paint getTShirtPaint() {
        if (mTShirtPaint == null){
            mTShirtPaint = new Paint();
        }
        return mTShirtPaint;
    }
}
