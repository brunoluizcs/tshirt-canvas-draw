package com.example.brunosantos.draganddrop.textpicker.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.example.brunosantos.draganddrop.BaseActivity;
import com.example.brunosantos.draganddrop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnEditorAction;


public class TextPickerActivity extends BaseActivity {
    @BindView(R.id.tv_text1) EditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_picker);
        ButterKnife.bind(this);
    }

    @OnEditorAction(R.id.tv_text1)
    protected boolean onTextAction(int actionId){
        if (actionId == EditorInfo.IME_ACTION_DONE){
            deliveryTextTyped(mEditText.getText().toString());
        }
        return false;
    }

    protected void deliveryTextTyped(String text) {
        if (! TextUtils.isEmpty(text.trim())){
            Intent intent = new Intent();
            intent.putExtra(Intent.EXTRA_TEXT,text.trim());
            setResult(RESULT_OK,intent);
        }
        finish();
    }


}
