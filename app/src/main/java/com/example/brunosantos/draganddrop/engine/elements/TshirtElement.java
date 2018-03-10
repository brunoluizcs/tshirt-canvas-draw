package com.example.brunosantos.draganddrop.engine.elements;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.brunosantos.draganddrop.R;
import com.example.brunosantos.draganddrop.engine.PaintFactory;


public class TshirtElement implements ElementDrawable {

    @Override
    public void draw(Context context, Canvas canvas, int color) {
        int dx = 0;
        int dy = 0;

        Paint paint = PaintFactory.getInstance().getTShirtPaint(color);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.t_shirt_front3);
        canvas.drawBitmap(bitmap, dx,  dy, paint);
    }



}
