package com.example.brunosantos.draganddrop.fontpicker;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brunosantos.draganddrop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FontPickerAdapter extends RecyclerView.Adapter<FontPickerAdapter.FontPickerViewHolder>{
    private static final String TAG = FontPickerDialog.class.getSimpleName();

    private final String[] mFonts;
    private final FontSelectedListener mFontSelectedListener;

    public FontPickerAdapter(String[] fonts, FontSelectedListener fontSelectedListener) {
        this.mFonts = fonts;
        this.mFontSelectedListener = fontSelectedListener;
    }

    @Override
    public FontPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_font,parent,false);
        return new FontPickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FontPickerViewHolder holder, int position) {
        holder.bind(mFonts[position]);
    }

    @Override
    public int getItemCount() {
        return mFonts != null ? mFonts.length : 0;
    }

    public class FontPickerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        Context mContext;

        @BindView(R.id.text1) TextView mTextView1;
        public FontPickerViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mContext = itemView.getContext();
            ButterKnife.bind(this,itemView);
        }

        public void bind(String font){
            Typeface typeface = Typeface.createFromAsset(mContext.getAssets(),font);
            mTextView1.setText("ATELIE");
            mTextView1.setTypeface(typeface);
        }

        @Override
        public void onClick(View view) {
            if (mFontSelectedListener != null){
                String font = mFonts[getAdapterPosition()];
                mFontSelectedListener.onFontSelected(font);
            }
        }
    }
}
