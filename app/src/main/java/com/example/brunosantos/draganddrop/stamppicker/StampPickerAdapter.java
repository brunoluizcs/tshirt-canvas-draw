package com.example.brunosantos.draganddrop.stamppicker;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


public class StampPickerAdapter extends FragmentStatePagerAdapter {

    private List<String> mDataSet;

    public StampPickerAdapter(@NonNull List<String> dataSet, FragmentManager fm) {
        super(fm);
        this.mDataSet = dataSet;
    }

    @Override
    public Fragment getItem(int position) {
        return StampCollectionFragment.newInstance(mDataSet.get(position)) ;
    }

    @Override
    public int getCount() {
        return mDataSet != null ? mDataSet.size() : 0 ;
    }
}
