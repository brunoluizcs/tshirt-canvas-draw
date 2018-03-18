package com.example.brunosantos.draganddrop.engine.stamp;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.brunosantos.draganddrop.engine.PaintFactory;

import java.io.IOException;
import java.io.InputStream;

public class PictureStamp implements Stamp {
    private final String TAG = PictureStamp.class.getSimpleName();
    private enum StampType {ASSET,RESOURCE,REPOSITORY}

    private String mFilePath;
    private String mAssetName;
    private @DrawableRes int mDrawableId;
    private StampType mStampType;

    private int mWidth;
    private int mHeight;

    public PictureStamp(Uri filePath) {
        this.mStampType  = StampType.REPOSITORY;
        this.mFilePath = filePath.getPath();
    }

    public PictureStamp(String assetName) {
        this.mStampType = StampType.ASSET;
        this.mAssetName = assetName;
    }

    public PictureStamp(@DrawableRes int drawableId){
        this.mStampType = StampType.RESOURCE;
        this.mDrawableId = drawableId;
    }

    @Nullable
    public Bitmap getBitmap(Context context){
        switch (mStampType){
            case ASSET:
                return getBitmapFromAsset(context);
            case RESOURCE:
                return getBitmapFromResource(context);
            case REPOSITORY:
                return getBitmapFromRepository();
            default:
                return null;
        }
    }

    private Bitmap getBitmapFromRepository(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        Bitmap bitmap = BitmapFactory.decodeFile(mFilePath,options);
        mWidth = options.outWidth;
        mHeight = options.outHeight;
        return bitmap;

    }

    private Bitmap getBitmapFromResource(Context context){
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),mDrawableId,options);
        mWidth = options.outWidth;
        mHeight = options.outHeight;
        return bitmap;
    }

    private Bitmap getBitmapFromAsset(Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        AssetManager assetManager = context.getAssets();

        InputStream istr;
        Bitmap bitmap = null;
        try {
            istr = assetManager.open(mAssetName);
            bitmap = BitmapFactory.decodeStream(istr,null,options);
            mWidth = options.outWidth;
            mHeight = options.outHeight;
        } catch (IOException e) {
            Log.e(TAG, "getBitmapFromAsset: ", e);
        }
        return bitmap;
    }

    @Override
    public int getWidth() {
        return mWidth;
    }


    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public void draw(Context context, Canvas canvas, float width, float height,
                     float left, float top, float rotateDegrees, int color, float density) {

        Bitmap bitmap = this.getBitmap(context);
        if (bitmap == null){
            return;
        }

        bitmap = Bitmap.createScaledBitmap(bitmap,
                Math.round(width),
                Math.round(height),false);

        Matrix matrix = new Matrix();
        matrix.preRotate(rotateDegrees,width/2,height/2);
        matrix.postTranslate(left,top);

        Paint paint = PaintFactory.getInstance().getPaint();
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
        canvas.drawBitmap(bitmap,matrix,paint);

    }





}
