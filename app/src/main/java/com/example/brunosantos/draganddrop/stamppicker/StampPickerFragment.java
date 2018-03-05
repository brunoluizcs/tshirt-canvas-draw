package com.example.brunosantos.draganddrop.stamppicker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brunosantos.draganddrop.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StampPickerFragment extends Fragment {
    public static final String TAG = StampPickerFragment.class.getSimpleName();

    @BindView(R.id.vp_stamp) ViewPager mViewPager;
    private StampPickerAdapter mAdapter;

    public static StampPickerFragment newInstance() {
        Bundle args = new Bundle();

        StampPickerFragment fragment = new StampPickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stamp_picker,container,false);
        ButterKnife.bind(this,view);
        loadCollections();
        return view;
    }


    private void loadCollections() {
        List<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(999);

        mAdapter = new StampPickerAdapter(integers, getFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }
}
