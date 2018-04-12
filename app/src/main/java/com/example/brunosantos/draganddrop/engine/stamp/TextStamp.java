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
import android.support.annotation.NonNull;
import android.text.TextUtils;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class TextStamp implements Stamp {

    private String mText;
    private String mAsset;
    private Rect mBounds;
    private float mTextSize;

    public TextStamp(@NonNull String text, float textSize) {
        this.mText = text.toUpperCase();
        this.mTextSize = textSize;

        mBounds = new Rect();
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.getTextBounds(mText,0,mText.length(),mBounds);
    }


    @Override
    public void draw(Context context, Canvas canvas, float width, float height,
                     float left, float top, float rotateDegrees, int color,float density,
                     boolean flipHorizontal) {

        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(mTextSize);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);

        if (! TextUtils.isEmpty(mAsset)) {
            paint.setTypeface(Typeface.createFromAsset(context.getAssets(), mAsset));
        }
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        mBounds = new Rect();
        paint.getTextBounds(mText,0,mText.length(),mBounds);

        float marginDp = (int) 16f * density;
        int bitmapWidth = mBounds.width() + (int) marginDp;
        int bitmapHeight = mBounds.height() + (int) marginDp;
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth,bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas textCanvas = new Canvas(bitmap);

        float x = bitmapWidth / 2f - mBounds.width() / 2f - mBounds.left;
        float y = bitmapHeight / 2f + mBounds.height() / 2f - mBounds.bottom;
        textCanvas.drawText(mText,x,y, paint);

        bitmap = Bitmap.createScaledBitmap(bitmap,
                Math.round(width),
                Math.round(height),false);

        Matrix matrix = new Matrix();
        if (flipHorizontal){
            matrix.preScale(-1,1);
        }

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

    @Override
    public void setAsset(String assetPath) {
        this.mAsset =  assetPath;
    }
}
