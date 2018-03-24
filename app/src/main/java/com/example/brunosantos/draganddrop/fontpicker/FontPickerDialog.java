package com.example.brunosantos.draganddrop.fontpicker;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewAnimationUtils;

import com.example.brunosantos.draganddrop.R;


public class FontPickerDialog extends Dialog implements
        DialogInterface.OnShowListener {
    private static final String TAG = FontPickerDialog.class.getSimpleName();

    private View mView;
    private int mCenterX;
    private int mCenterY;
    private boolean mIsCenterDefined;

    public FontPickerDialog(@NonNull Activity activity, FontSelectedListener fontSelectedListener) {
        super(activity, R.style.AppTheme_TransparentDialog);
        init(fontSelectedListener);
    }

    public FontPickerDialog(@NonNull Activity activity, int centerX, int centerY, FontSelectedListener fontSelectedListener){
        super(activity, R.style.AppTheme_TransparentDialog);
        mIsCenterDefined = true;
        mCenterX = centerX;
        mCenterY = centerY;
        init(fontSelectedListener);
    }

    private void init(FontSelectedListener fontSelectedListener) {
        FontPickerAdapter fontPickerAdapter =
                new FontPickerAdapter(FontRepository.getFonts(getContext()),fontSelectedListener);

        mView = View.inflate(getContext(), R.layout.dialog_font_picker,null);
        setContentView(mView);
        setupUI();


        RecyclerView mRecyclerViewFontPicker = mView.findViewById(R.id.rv_font_picker);
        mRecyclerViewFontPicker.setLayoutManager(new GridLayoutManager(getContext(),3));
        mRecyclerViewFontPicker.setHasFixedSize(true);
        mRecyclerViewFontPicker.setAdapter(fontPickerAdapter);

    }


    private void setupUI() {
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        setOnShowListener(this);
    }

    private void reveal(boolean show, final DialogInterface dialog) {
        int duration = getContext().getResources().getInteger(android.R.integer.config_mediumAnimTime);

        if (! mIsCenterDefined) {
            mCenterX = (mView.getLeft() + mView.getRight()) / 2;
            mCenterY = (mView.getTop() + mView.getBottom()) / 2;
        }

        int finalRadius = Math.max(mView.getWidth(), mView.getHeight());
        if (show) {
            showDialog(mCenterX, mCenterY, finalRadius,duration);
        }else{
            hideDialog(mCenterX,mCenterY, finalRadius,duration);
        }
    }

    private void showDialog(int cx, int cy, int finalRadius,int duration) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(mView, cx, cy, 0, finalRadius);
            mView.setVisibility(View.VISIBLE);
            anim.setDuration(duration);
            anim.start();
        }else{
            mView.setVisibility(View.VISIBLE);
        }
    }

    private void hideDialog(int cx, int cy, int finalRadius,int duration) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Animator anim = ViewAnimationUtils.createCircularReveal(mView, cx, cy, finalRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mView.setVisibility(View.INVISIBLE);
                    FontPickerDialog.super.cancel();
                }
            });

            anim.setDuration(duration);
            anim.start();
        }else{
            mView.setVisibility(View.INVISIBLE);
            FontPickerDialog.super.cancel();
        }
    }



    @Override
    public void onShow(DialogInterface dialogInterface) {
        reveal(true,dialogInterface);

    }

    @Override
    public void cancel() {
        reveal(false,this);
    }
}
