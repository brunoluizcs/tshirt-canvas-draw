package com.example.brunosantos.draganddrop.stamppicker;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brunosantos.draganddrop.R;

import java.io.IOException;
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
        List<String> collections = new ArrayList<>();
        try {
            String[] assets = getContext().getAssets().list("");
            for(int i = 0; i <= assets.length -1; i++){
                if (assets[i].startsWith("collection")){
                    collections.add(assets[i]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mAdapter = new StampPickerAdapter(collections, getFragmentManager());
        mViewPager.setAdapter(mAdapter);
    }
}
