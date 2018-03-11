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
        Paint paint = PaintFactory.getInstance().getTShirtPaint(color);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_tshirt_front);

        int dx = (canvas.getWidth() / 2) - (bitmap.getWidth() /2);
        int dy = (canvas.getHeight() / 2) - (bitmap.getHeight() /2);
        canvas.drawBitmap(bitmap, dx,  dy, paint);
        bitmap.recycle();
    }



}
