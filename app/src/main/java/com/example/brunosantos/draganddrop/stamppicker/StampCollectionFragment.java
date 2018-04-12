package com.example.brunosantos.draganddrop.stamppicker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brunosantos.draganddrop.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StampCollectionFragment extends Fragment {
    private static final String EXTRA_COLLECTION_NAME = "extra-collection-name";
    public String mCollectionName;

    @BindView(R.id.rv_collection_stamp) RecyclerView mCollectionStampRecyclerView;

    private StampAdapter mStampAdapter;


    static StampCollectionFragment newInstance(String collectionName) {
        StampCollectionFragment f = new StampCollectionFragment();

        Bundle args = new Bundle();
        args.putString(EXTRA_COLLECTION_NAME, collectionName);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCollectionName = getArguments() != null ? getArguments().getString(EXTRA_COLLECTION_NAME,"") : "";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_stamp_collection_list, container, false);
        ButterKnife.bind(this,v);
        mCollectionStampRecyclerView.setHasFixedSize(true);
        mCollectionStampRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),7));

        if  (mStampAdapter == null){
            mStampAdapter = new StampAdapter();
        }

        Collection collection = CollectionRepository.getCollection(getContext(), mCollectionName);
        mCollectionStampRecyclerView.setAdapter(mStampAdapter);
        mStampAdapter.swapDataSet(collection.getStamps());
        return v;
    }






}
