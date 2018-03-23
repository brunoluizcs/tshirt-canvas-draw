package com.example.brunosantos.draganddrop;

import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.brunosantos.draganddrop.actionpanel.ActionsFragment;
import com.example.brunosantos.draganddrop.engine.DrawnerEngine;
import com.example.brunosantos.draganddrop.engine.stamp.PictureStamp;
import com.example.brunosantos.draganddrop.engine.stamp.TextStamp;
import com.example.brunosantos.draganddrop.stamppicker.StampPickerFragment;
import com.example.brunosantos.draganddrop.textpicker.view.TextPickerActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.brunosantos.draganddrop.engine.scketch.SketchFactory.SWEATSHIRT;
import static com.example.brunosantos.draganddrop.engine.scketch.SketchFactory.TSHIRT;


public class MainActivity extends BaseActivity {

    private static final int TEXT_REQUEST = 100;
    @BindView(R.id.fl_container_stamp_picker) View mContainerLogoView;
    @BindView(R.id.fl_container_screen) CanvasView mCanvasView;
    @BindView(R.id.toolbar_main) Toolbar mToolbar;



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
                    PictureStamp pictureStamp = null;
                    if (event.getLocalState() instanceof PictureStamp){
                        pictureStamp = (PictureStamp) event.getLocalState();
                    }

                    float density = getResources().getDisplayMetrics().density;
                    if (pictureStamp != null){
                        DrawnerEngine.getInstance().addStamp(pictureStamp,
                                event.getX(),event.getY(),
                                pictureStamp.getWidth(),
                                pictureStamp.getHeight(),
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
        setSupportActionBar(mToolbar);

        if  (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);

        mCanvasView.setOnDragListener(mDragListen);
        loadStampPickerFragment();
        loadActionsFragment();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.drawner,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id  = item.getItemId();

        switch (id){
            case R.id.action_color_white:
                DrawnerEngine.getInstance()
                        .setSketchColor(getResources().getColor(R.color.colorWhite));
                break;
            case R.id.action_color_black:
                DrawnerEngine.getInstance()
                        .setSketchColor(getResources().getColor(R.color.colorBlack));
                break;
            case R.id.action_color_blue:
                DrawnerEngine.getInstance()
                        .setSketchColor(getResources().getColor(R.color.colorBlue));
                break;
            case R.id.action_color_red:
                DrawnerEngine.getInstance()
                        .setSketchColor(getResources().getColor(R.color.colorRed));
                break;
            case R.id.action_tshirt:
                DrawnerEngine.getInstance().setSketchType(TSHIRT);
                break;
            case R.id.action_sweatshirt:
                DrawnerEngine.getInstance().setSketchType(SWEATSHIRT);
                break;
            case R.id.action_text_field:
                startActivityForResult(new Intent(this, TextPickerActivity.class),TEXT_REQUEST);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadStampPickerFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container_stamp_picker, StampPickerFragment.newInstance(), ActionsFragment.TAG)
                .commit();
    }

    private void loadActionsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_actions, ActionsFragment.newInstance(), ActionsFragment.TAG)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == TEXT_REQUEST) {
                if (data.getExtras() != null && data.hasExtra(Intent.EXTRA_TEXT)){
                    createTextStamp(data.getStringExtra(Intent.EXTRA_TEXT));
                }
            }
        }
    }

    public void createTextStamp(String text){
        float density = getResources().getDisplayMetrics().density;
        TextStamp textStamp = new TextStamp(text,10 * density);
        DrawnerEngine.getInstance().addStamp(textStamp,
                100,100,
                textStamp.getWidth(),
                textStamp.getHeight(),
                density);

    }
}
