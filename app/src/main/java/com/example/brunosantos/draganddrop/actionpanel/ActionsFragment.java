package com.example.brunosantos.draganddrop.actionpanel;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brunosantos.draganddrop.R;
import com.example.brunosantos.draganddrop.engine.DrawnerEngine;
import com.example.brunosantos.draganddrop.fontpicker.FontPickerDialog;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ActionsFragment extends Fragment {
    public static final String TAG = ActionsFragment.class.getSimpleName();

    public static ActionsFragment newInstance() {
        Bundle args = new Bundle();

        ActionsFragment fragment = new ActionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rv_action_panel) RecyclerView mActionPanelRecycleView;
    private ActionsAdapter mActionsAdapter;
    private final ActionsAdapter.ActionClickListener mActionClickListener = new ActionsAdapter.ActionClickListener(){
        @Override
        public void onItemClicked(View view, Actions actions) {
            switch (actions){
                case FONT:
                    int cx = (int) (view.getX() + (view.getWidth()/2));
                    int cy = (int) (view.getY())+ view.getHeight();

                    FontPickerDialog fontPickerDialog = new FontPickerDialog(getActivity(),cx,cy);
                    fontPickerDialog.show();
                    break;
                case ZOON_IN:
                    DrawnerEngine.getInstance().zoomIn();
                    break;
                case ZOON_OUT:
                    DrawnerEngine.getInstance().zoomOut();
                    break;
                case ROTATE_LEFT:
                    DrawnerEngine.getInstance().rotateLeft();
                    break;
                case ROTATE_RIGHT:
                    DrawnerEngine.getInstance().rotateRight();
                    break;
                case FLIP_FRONT:
                    DrawnerEngine.getInstance().flipToFront();
                    break;
                case FLIP_BACK:
                    DrawnerEngine.getInstance().flipToBack();
                    break;
                case COLOR:
                    setStampColor();
                    break;
                case DELETE:
                    DrawnerEngine.getInstance().delete();
                    break;
            }
        }
    };

    private void setStampColor() {
        ColorPickerDialogBuilder
                .with(getContext())
                .setTitle(R.string.choose_color)
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(6)
                .showAlphaSlider(false)
                .setOnColorSelectedListener(null)
                .setPositiveButton(android.R.string.ok, new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        DrawnerEngine.getInstance().setColor(selectedColor);
                    }
                })
                .setNegativeButton(android.R.string.cancel,null)
                .build()
                .show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action_panel,container,false);
        ButterKnife.bind(this,view);
        setupRecyclerView();
        return view;
    }

    private void setupRecyclerView(){
        mActionPanelRecycleView.setHasFixedSize(true);
        mActionPanelRecycleView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,false));

        if (mActionsAdapter == null){
            mActionsAdapter = new ActionsAdapter(null);
        }
        mActionPanelRecycleView.setAdapter(new ActionsAdapter(mActionClickListener));
    }


}
