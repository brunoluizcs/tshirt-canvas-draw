package com.example.brunosantos.draganddrop.actionpanel;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.brunosantos.draganddrop.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.ActionsViewHolder>{

    public interface ActionClickListener{
        void onItemClicked(Actions actions);
    }

    private ActionClickListener mActionListener;

    public ActionsAdapter(@Nullable  ActionClickListener actionListener) {
        this.mActionListener = actionListener;
    }

    @Override
    public ActionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action,parent,false);
        return new ActionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActionsViewHolder holder, int position) {
        holder.bind(Actions.values()[position]);
    }

    

    @Override
    public int getItemCount() {
        return Actions.values().length;
    }

    class ActionsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Context mContext;

        @BindView(R.id.iv_action) ImageView mActionImageView;

        ActionsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this,itemView);
            mContext = itemView.getContext();
        }

        void bind(Actions actions){
            String description = mContext.getString(actions.getDescription());
            Drawable icon = mContext.getResources().getDrawable(actions.getIcon());

            mActionImageView.setContentDescription(description);
            mActionImageView.setImageDrawable(icon);
        }

        @Override
        public void onClick(View view) {
            Actions action = Actions.values()[getAdapterPosition()];
            if (mActionListener != null){
                mActionListener.onItemClicked(action);
            }
        }
    }
}
