package com.example.brunosantos.draganddrop.engine.stamp;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Typeface;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class TextStamp implements Stamp {

    private String mText;
    private String mAsset = "fonts/font_waltographUI.ttf";
    private Rect mBounds;
    private float mTextSize;

    public TextStamp(String text,float textSize) {
        this.mText = text;
        this.mTextSize = textSize;

        mBounds = new Rect();
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.getTextBounds(mText,0,mText.length(),mBounds);
    }


    @Override
    public void draw(Context context, Canvas canvas, float width, float height,
                     float left, float top, float rotateDegrees, int color,float density) {

        Typeface plain = Typeface.createFromAsset(context.getAssets(), mAsset);
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(mTextSize);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setTypeface(plain);
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        mBounds = new Rect();
        paint.getTextBounds(mText,0,mText.length(),mBounds);

        float baseline = -paint.ascent();
        Bitmap bitmap = Bitmap.createBitmap(mBounds.width(),mBounds.height(), Bitmap.Config.ARGB_8888);
        Canvas textCanvas = new Canvas(bitmap);
        textCanvas.drawText(mText, 0, baseline, paint);
        //canvas.rotate(rotateDegrees);

        bitmap = Bitmap.createScaledBitmap(bitmap,
                Math.round(width),
                Math.round(height),false);

        Matrix matrix = new Matrix();
        matrix.preRotate(rotateDegrees,width/2, height/2);
        matrix.postTranslate(left,top);

        canvas.drawBitmap(bitmap,matrix,paint);
    }

    @Override
    public int getWidth() {
        return mBounds != null ? mBounds.width() : 1;
    }

    @Override
    public int getHeight() {
        return mBounds != null ? mBounds.height() : 1;
    }
}
