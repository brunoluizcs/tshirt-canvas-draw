package com.example.brunosantos.draganddrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CanvasView extends SurfaceView implements
        Runnable,
        View.OnTouchListener{

    private static final String TAG = CanvasView.class.getSimpleName();

    private Paint mPaint;
    private Paint mEdgePaint;
    private Paint mBackgroundPaint;
    private SurfaceHolder mHolder;
    private Thread mRenderThread = null;
    private boolean mRunning = false;
    private List<DrawnerObject> mDrawnerObjectList = new ArrayList<>();
    private DrawnerObject mCurrentObject = null;
    private ScaleGestureDetector mScaleDetector;

    private static final int NONE = 0;
    private static final int MOVING = 1;
    private static final int ZOONING = 2;
    @State private static int mState;



    @IntDef({NONE,MOVING,ZOONING})
    @interface State{}


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        final int action = event.getAction();

        switch (action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                actionDown(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                actionMove(event);
                return true;
            case MotionEvent.ACTION_UP:
                //mCurrentObject = null;
                mState = NONE;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                actionPointerDown(event);

                return true;
            case MotionEvent.ACTION_POINTER_UP:
                mState = NONE;
                return true;
        }
        return false;
    }

    private void actionPointerDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (findElement((int) x, (int) y)){
            mState = ZOONING;
        }
    }

    private boolean findElement(int x, int y) {
        Rect rect1 = new Rect(x, y, x + 46, y + 46);
        for (DrawnerObject object : mDrawnerObjectList){
            mCurrentObject = null;
            if (object.getRect().intersect(rect1)){
                mState = MOVING;
                mCurrentObject = object;
                break;
            }
        }
        String objetoValido = mCurrentObject!=null ? "Valido" : "Invalido";
        Log.d(TAG, "onTouch: " + objetoValido);
        return mCurrentObject != null ;
    }

    private void actionDown(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (findElement((int) x, (int) y)){
           mState = MOVING;
        }
    }

    private void actionMove(MotionEvent event) {
        Log.d(TAG, "onTouch: movendo");
        if (mCurrentObject != null && mState == MOVING){
            mCurrentObject.setLeft(event.getX() - mCurrentObject.getWidth()/2);
            mCurrentObject.setTop(event.getY() - mCurrentObject.getHeight()/2);
        }
    }

    public CanvasView(Context context) {
        super(context);
        init();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mScaleDetector = new ScaleGestureDetector(getContext(),
                new ScaleListener());

        mPaint = new Paint();
        mHolder = getHolder();
        setOnTouchListener(this);
        mBackgroundPaint = this.getBackgroundPaint();
        mEdgePaint = this.getEdgePaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mScaleDetector.onTouchEvent(event);
        return true;
    }

    public void resume(){
        mRunning = true;
        mRenderThread = new Thread(this);
        mRenderThread.start();
    }

    @Override
    public void run() {
        while(mRunning){
            if (! mHolder.getSurface().isValid())
                continue;

            Canvas canvas = mHolder.lockCanvas();
            if (canvas != null) {
                synchronized (mHolder){
                    canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),mBackgroundPaint);
                    drawTShirt(canvas);
                    drawObjects(canvas);
                    drawObjectEdge(canvas);
                }
                mHolder.unlockCanvasAndPost(canvas);
            }

            try{
                Thread.sleep(12);//60 fps
            }catch (InterruptedException ex){
            }
        }
    }

    private Paint getEdgePaint(){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        return paint;
    }

    private Paint getBackgroundPaint(){
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(android.R.color.white));
        paint.setAntiAlias(true);
        return paint;
    }

    synchronized private void drawTShirt(Canvas canvas){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.t_shirt_front

        );
        canvas.drawBitmap(bitmap,0,0,mPaint);
    }

    synchronized private void drawObjects(Canvas canvas){
        for (DrawnerObject drawnerObject : mDrawnerObjectList) {
            try{
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),drawnerObject.getBitmap());
                bitmap = Bitmap.createScaledBitmap(bitmap,
                        Math.round(drawnerObject.getWidth()),
                        Math.round(drawnerObject.getHeight()),false);

                Matrix matrix = new Matrix();
                matrix.preRotate(drawnerObject.getRotateDegrees());

                bitmap = Bitmap.createBitmap(bitmap,0,0,
                        Math.round(drawnerObject.getWidth()),
                        Math.round(drawnerObject.getHeight()),
                        matrix,false);

                canvas.drawBitmap(bitmap,drawnerObject.getLeft(),drawnerObject.getTop(),mPaint);
            }catch (IllegalArgumentException e){
                Log.e(TAG, "drawObjects: ", e);
            }
        }
    }

    synchronized private void drawObjectEdge(Canvas canvas){
        if (mCurrentObject != null){
            canvas.drawRoundRect(mCurrentObject.getRectF(),10,10,mEdgePaint);
        }
    }

    synchronized public void addBitmap(@DrawableRes int bitmap, float left, float top, float width, float height){
        mDrawnerObjectList.add(new DrawnerObject(bitmap,left,top,width,height,1.f));
    }

    synchronized public void removeBitmap(){
        if (mCurrentObject != null){
            mDrawnerObjectList.remove(mCurrentObject);
            mCurrentObject = null;
        }
    }

    public void zoonIn(){
        if (mCurrentObject != null){
            float scaleFactor = mCurrentObject.getScale();
            scaleFactor *= 1.1f;
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            mCurrentObject.setScale(scaleFactor);
        }
    }

    public void zoonOut(){
        if (mCurrentObject != null){
            float scaleFactor = mCurrentObject.getScale();
            scaleFactor -= scaleFactor * 0.1f;
            scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
            mCurrentObject.setScale(scaleFactor);
        }
    }

    public void rotateLeft(){
        if (mCurrentObject != null){
            float degrees = mCurrentObject.getRotateDegrees();
            degrees -= 1 * 5.0f;
            mCurrentObject.setRotateDegrees(degrees);
        }
    }

    public void rotateRight(){
        if (mCurrentObject != null){
            float degrees = mCurrentObject.getRotateDegrees();
            degrees += 1 * 5.0f;
            mCurrentObject.setRotateDegrees(degrees);
        }
    }

    synchronized public void flipToFront() {
        if (mCurrentObject != null){
            mCurrentObject.incZIndex();
            sortDrawners(mDrawnerObjectList);
        }
    }

    synchronized public void flipToBack() {
        if (mCurrentObject != null){
            mCurrentObject.decZIndex();
            sortDrawners(mDrawnerObjectList);
        }
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (mCurrentObject != null){
                float scaleFactor = mCurrentObject.getScale();
                scaleFactor *= detector.getScaleFactor();
                scaleFactor = Math.max(0.1f, Math.min(scaleFactor, 5.0f));
                mCurrentObject.setScale(scaleFactor);
            }
            return true;
        }
    }

    synchronized public void sortDrawners(List<DrawnerObject> drawnerObjects){
        for(int i = 0; i <= drawnerObjects.size() -1; i++){
            DrawnerObject current = drawnerObjects.get(i);
            if (i > 0){
                DrawnerObject comparable = drawnerObjects.get(i -1);
                if (current.getzIndex() <= comparable.getzIndex()){
                    drawnerObjects.set(i,comparable);
                    drawnerObjects.set(i - 1,current);
                }
            }
        }
    }

}
