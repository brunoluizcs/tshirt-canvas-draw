package com.example.brunosantos.draganddrop.engine.elements;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;

import com.example.brunosantos.draganddrop.R;
import com.example.brunosantos.draganddrop.engine.PaintFactory;

public class BackgroundElement implements ElementDrawable{



    @Override
    public void draw(Context context, Canvas canvas, int color) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_dot);

        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),
                PaintFactory.getInstance().getBackgroundPaint(color));
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
