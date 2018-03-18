package com.example.brunosantos.draganddrop.stamppicker;


import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.brunosantos.draganddrop.R;
import com.example.brunosantos.draganddrop.engine.stamp.PictureStamp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StampAdapter extends RecyclerView.Adapter<StampAdapter.StampViewHolder>{
    private List<PictureStamp> mDataSet;

    public void swapDataSet(@NonNull  List<PictureStamp> pictureStamps){
        if (mDataSet == null){
            mDataSet = new ArrayList<>();
        }
        mDataSet.clear();
        mDataSet.addAll(pictureStamps);
    }

    @Override
    public StampViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stamp,parent,false);
        return new StampViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StampViewHolder holder, int position) {
        holder.bind(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet != null ? mDataSet.size() : 0;
    }

    class StampViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener{

        @BindView(R.id.iv_stamp) ImageView mStampImageView;

        private final Context mContext;

        public StampViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            ButterKnife.bind(this,itemView);
            itemView.setOnTouchListener(this);
            itemView.setTag("PictureStamp");


        }

        public void bind(PictureStamp pictureStamp){
            mStampImageView.setImageBitmap(pictureStamp.getBitmap(mContext));
        }


        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN){
                return false;
            }
            ClipData.Item item = new ClipData.Item((String) view.getTag());
            ClipData dragData = new ClipData((String) view.getTag(),new String[]{"text/plain"},item);

            View.DragShadowBuilder dragShadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(dragData,  // the data to be dragged
                    dragShadowBuilder,  // the drag shadow builder
                    mDataSet.get(getAdapterPosition()),      // no need to use local data
                    0          // flags (not currently used, set to 0)
            );
            return true;
        }
    }
}
