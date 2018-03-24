package com.example.brunosantos.draganddrop.textpicker;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.example.brunosantos.draganddrop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class TextPickerDialog extends Dialog implements
        DialogInterface.OnShowListener {
    private static final String TAG = TextPickerDialog.class.getSimpleName();

    private View mView;
    private int mCenterX;
    private int mCenterY;
    private boolean mIsCenterDefined;
    private TextSelectedListener mTextSelectedListener;

    @BindView(R.id.tv_text1) EditText mEditText;
    @BindView(R.id.bt_confirm) Button mButtonConfirm;


    public TextPickerDialog(@NonNull Activity activity, TextSelectedListener textSelectedListener) {
        super(activity, R.style.AppTheme_TransparentDialog);
        mTextSelectedListener = textSelectedListener;
        init();
    }

    public TextPickerDialog(@NonNull Activity activity, int centerX, int centerY, TextSelectedListener textSelectedListener){
        super(activity, R.style.AppTheme_TransparentDialog);
        mIsCenterDefined = true;
        mCenterX = centerX;
        mCenterY = centerY;
        mTextSelectedListener = textSelectedListener;
        init();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this,mView);
    }

    private void init() {
        mView = View.inflate(getContext(), R.layout.activity_text_picker,null);
        setContentView(mView);
        mEditText = findViewById(R.id.tv_text1);
        setupUI();
    }

    @OnEditorAction(R.id.tv_text1)
    protected boolean onTextAction(int actionId){
        if (actionId == EditorInfo.IME_ACTION_DONE){
            handleTextTyped();
        }
        return false;
    }

    @OnClick(R.id.bt_confirm)
    public void confirmClick(View view){
        handleTextTyped();
    }

    private void handleTextTyped() {
        if (! TextUtils.isEmpty(mEditText.getText())){
            if (mTextSelectedListener != null){
                mTextSelectedListener.onTextSelected(mEditText.getText().toString());
            }
        }
        cancel();
    }


    public void setHeight(int height){
        if (getWindow() != null){
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, height);
        }
    }

    private void setupUI() {
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.gravity = Gravity.TOP | Gravity.RIGHT;

            attributes.x = mCenterX;
            attributes.y = mCenterY;
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
                    TextPickerDialog.super.cancel();
                }
            });

            anim.setDuration(duration);
            anim.start();
        }else{
            mView.setVisibility(View.INVISIBLE);
            TextPickerDialog.super.cancel();
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
