package com.example.brunosantos.draganddrop.colordialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;

import com.example.brunosantos.draganddrop.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ColorDialogActivity extends AppCompatActivity {

    public static final String EXTRA_CIRCULAR_REVEAL_X = "extra-circular-reveal-x";
    public static final String EXTRA_CIRCULAR_REVEAL_Y = "extra-circular-reveal-y";

    private int mRevealX;
    private int mRevealY;

    @BindView(R.id.rv_colors) RecyclerView mColorsRecyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_select);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (savedInstanceState == null &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_X) &&
                intent.hasExtra(EXTRA_CIRCULAR_REVEAL_Y) &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            mColorsRecyclerView.setVisibility(View.INVISIBLE);

            mRevealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0);
            mRevealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0);


            ViewTreeObserver viewTreeObserver = mColorsRecyclerView.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        revealActivity(mRevealX, mRevealY);
                        mColorsRecyclerView.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);
                    }
                });
            }


        } else {
            mColorsRecyclerView.setVisibility(View.VISIBLE);
        }

    }


    public void revealActivity(int x, int y){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            float finalRadius = (float) (Math.max(mColorsRecyclerView.getWidth(), mColorsRecyclerView.getHeight()) * 1.1);

            // create the animator for this view (the start radius is zero)
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(mColorsRecyclerView, x, y, 0, finalRadius);
            circularReveal.setDuration(400);
            circularReveal.setInterpolator(new AccelerateInterpolator());

            // make the view visible and start the animation
            mColorsRecyclerView.setVisibility(View.VISIBLE);
            circularReveal.start();
        } else {
            finish();
        }
    }


    protected void unRevealActivity() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            finish();
        } else {
            float finalRadius = (float) (Math.max(mColorsRecyclerView.getWidth(), mColorsRecyclerView.getHeight()) * 1.1);
            Animator circularReveal = ViewAnimationUtils.createCircularReveal(
                    mColorsRecyclerView, mRevealX, mRevealY, finalRadius, 0);

            circularReveal.setDuration(400);
            circularReveal.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mColorsRecyclerView.setVisibility(View.INVISIBLE);
                    finish();
                }
            });


            circularReveal.start();
        }
    }

    @Override
    protected void onPause() {
        if (isFinishing()){
            unRevealActivity();
        }
        super.onPause();
    }
}
