package com.example.brunosantos.draganddrop.engine.scketch;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.example.brunosantos.draganddrop.R;
import com.example.brunosantos.draganddrop.engine.PaintFactory;
import com.example.brunosantos.draganddrop.engine.elements.ElementDrawable;


class SweatshirtElement extends Sketch {

    @Override
    public void draw(Context context, Canvas canvas) {
        Paint paint = PaintFactory.getInstance().getTShirtPaint(getColor());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_sweatshirt_front);

        int dx = (canvas.getWidth() / 2) - (bitmap.getWidth() /2);
        int dy = (canvas.getHeight() / 2) - (bitmap.getHeight() /2);
        canvas.drawBitmap(bitmap, dx,  dy, paint);
        bitmap.recycle();
    }



}
