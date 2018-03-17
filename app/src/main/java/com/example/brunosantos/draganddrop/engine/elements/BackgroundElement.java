package com.example.brunosantos.draganddrop.engine.elements;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;

import com.example.brunosantos.draganddrop.R;
import com.example.brunosantos.draganddrop.engine.PaintFactory;

public class BackgroundElement implements ElementDrawable{
    private int  mColor = Color.parseColor("#f7f7f7");

    @Override
    public int getColor() {
        return mColor;
    }

    @Override
    public void setColor(int color) {
        mColor = color;
    }

    @Override
    public void draw(Context context, Canvas canvas) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dot);

        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),
                PaintFactory.getInstance().getBackgroundPaint(mColor));
        drawDashLine(canvas,bmp);
    }

    private void drawDashLine(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        Shader mShader1 = new BitmapShader(bitmap, Shader.TileMode.REPEAT,Shader.TileMode.REPEAT);
        paint.setShader(mShader1);
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint);
        bitmap.recycle();
    }
}
