package com.example.brunosantos.draganddrop.engine;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;

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

    public Paint getEdgePaint(){
        if (mEdgePaint == null){
            mEdgePaint = new Paint();
            mEdgePaint.setStyle(Paint.Style.STROKE);
            mEdgePaint.setAntiAlias(true);
            mEdgePaint.setStrokeWidth(4);
            mEdgePaint.setColor(Color.BLUE);
        }
        return mEdgePaint;
    }

    public Paint getBackgroundPaint(int color){
        if  (mBackgroundPaint == null){
            mBackgroundPaint = new Paint();
            mBackgroundPaint.setColor(color);
            mBackgroundPaint.setAntiAlias(true);
        }else{
            if (mBackgroundPaint.getColor() != color){
                mBackgroundPaint.setColor(color);
            }
        }
        return mBackgroundPaint;
    }

    public Paint getTShirtPaint(int color) {
        if (mTShirtPaint == null) {
            mTShirtPaint = new Paint();
            mTShirtPaint.setAntiAlias(true);
            /*
            mTShirtPaint.setShadowLayer(5f,10f,10f,
                    Color.parseColor("#f7f7f7"));
                    */
        }

        if (mTShirtPaint.getColor() != color) {
            mTShirtPaint.setColor(color);
            mTShirtPaint.setColorFilter(new PorterDuffColorFilter(color,
                    PorterDuff.Mode.MULTIPLY));

        }
        return mTShirtPaint;
    }
}
