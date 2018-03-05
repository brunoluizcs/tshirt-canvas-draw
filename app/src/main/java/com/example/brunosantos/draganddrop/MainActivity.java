package com.example.brunosantos.draganddrop;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.brunosantos.draganddrop.actionpanel.ActionsFragment;
import com.example.brunosantos.draganddrop.engine.DrawnerEngine;
import com.example.brunosantos.draganddrop.stamppicker.Stamp;
import com.example.brunosantos.draganddrop.stamppicker.StampPickerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_container_stamp_picker) View mContainerLogoView;
    @BindView(R.id.fl_container_screen) CanvasView mCanvasView;

    @Override
    protected void onResume() {
        super.onResume();
        mCanvasView.resume();
    }

    @Override
    protected void onPause(){
        mCanvasView.pause();
        super.onPause();
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
                    Stamp stamp = null;
                    if (event.getLocalState() instanceof Stamp){
                        stamp = (Stamp) event.getLocalState();
                    }

                    float density = getResources().getDisplayMetrics().density;
                    if (stamp != null){
                        DrawnerEngine.getInstance().addStamp(stamp,
                                event.getX(),event.getY(),
                                stamp.getmWidth(),
                                stamp.getmHeight(),
                                density);
                    }

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

        mCanvasView.setOnDragListener(mDragListen);

        loadStampPickerFragment();
        loadActionsFragment();

    }

    private void loadStampPickerFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(StampPickerFragment.TAG);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_container_stamp_picker, StampPickerFragment.newInstance(), ActionsFragment.TAG)
                    .commit();
        }
    }

    private void loadActionsFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(ActionsFragment.TAG);
        if (fragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fl_actions, ActionsFragment.newInstance(), ActionsFragment.TAG)
                    .commit();
        }
    }




}