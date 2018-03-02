package com.example.brunosantos.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity {
    private static final String IMAGEVIEW_TAG = "icon bitmap";

    @BindView(R.id.fl_container_logo) View mContainerLogoView;
    @BindView(R.id.fl_container_screen) CanvasView mCanvasView;
    @BindView(R.id.iv_logo) ImageView mLogoImageView;

    @Override
    protected void onResume() {
        super.onResume();
        mCanvasView.resume();
    }

    private final String TAG = MainActivity.class.getSimpleName();

    private final View.OnDragListener mDragListen = new View.OnDragListener() {

        @Override
        public boolean onDrag(View v, DragEvent event) {
            final int action = event.getAction();

            switch (action){
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                        if (v instanceof ImageView)
                            ((ImageView) v).setColorFilter(Color.BLUE);

                        v.invalidate();
                        return true;

                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
                    if (v instanceof ImageView)
                        ((ImageView) v).setColorFilter(Color.GREEN);

                    v.invalidate();
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    if (v instanceof ImageView)
                        ((ImageView) v).setColorFilter(Color.GREEN);

                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DROP:
                    ClipData.Item item = event.getClipData().getItemAt(0);

                    /*
                    TouchableImageView imageView = new TouchableImageView(MainActivity.this);
                    imageView.setImageResource(R.drawable.ic_android);
                    imageView.setY(event.getY());
                    imageView.setX(event.getX());
                    imageView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    */

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_android,options);
                    mCanvasView.addBitmap(R.drawable.ic_android,
                            event.getX(),event.getY(),options.outWidth,options.outHeight);

                    mCanvasView.addBitmap(R.drawable.ic_camera_alt,
                            event.getX(),event.getY(),options.outWidth,options.outHeight);

                    //((FrameLayout) v).addView(imageView);


                    float scale = getResources().getDisplayMetrics().density;
                    //imageView.getLayoutParams().height = (int)(46 *scale);
                    //imageView.getLayoutParams().width = (int)(46 * scale);

                    CharSequence dragData = item.getText();

                    if (v instanceof ImageView)
                        ((ImageView) v).clearColorFilter();

                    v.invalidate();
                    return true;

                case DragEvent.ACTION_DRAG_LOCATION:
                    return true;
                case DragEvent.ACTION_DRAG_ENDED:
                    v.invalidate();
                    return true;
                default:
                    Log.e("DragDrop Example","Unknown action type received by OnDragListener.");
                    break;
            }

            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLogoImageView.setTag(IMAGEVIEW_TAG);
        mCanvasView.setOnDragListener(mDragListen);
    }

    @OnClick(R.id.iv_zoom_in)
    public void zoomIn(View view){
        mCanvasView.zoonIn();
    }

    @OnClick(R.id.iv_zoom_out)
    public void zoomOut(View view){
        mCanvasView.zoonOut();
    }

    @OnClick(R.id.iv_rotate_left)
    public void rotateLeft(View view){
        mCanvasView.rotateLeft();
    }

    @OnClick(R.id.iv_rotate_right)
    public void rotateRight(View view){
        mCanvasView.rotateRight();
    }

    @OnClick(R.id.iv_flip_to_front)
    public void flipToFront(View view){
        mCanvasView.flipToFront();
    }

    @OnClick(R.id.iv_flip_to_back)
    public void flipToBack(View view){
        mCanvasView.flipToBack();
    }

    @OnClick(R.id.iv_remove)
    public void removeBitmap(View view){
        mCanvasView.removeBitmap();
    }


    @OnTouch(R.id.iv_logo)
    public boolean startDrag(View view, MotionEvent event){
        if (event.getAction() != MotionEvent.ACTION_DOWN){
            return false;
        }
        ClipData.Item item = new ClipData.Item((String) view.getTag());
        ClipData dragData = new ClipData((String) view.getTag(),new String[]{"text/plain"},item);

        //View.DragShadowBuilder myShadow = new MyDragShadowBuilder(mLogoImageView);
        View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);

        view.startDrag(dragData,  // the data to be dragged
                 dragShadowBuilder,  // the drag shadow builder
                null,      // no need to use local data
                0          // flags (not currently used, set to 0)
        );
        return true;

    }



}
