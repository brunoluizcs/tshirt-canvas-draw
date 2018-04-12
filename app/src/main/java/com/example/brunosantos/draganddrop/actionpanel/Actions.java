package com.example.brunosantos.draganddrop.actionpanel;


import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import com.example.brunosantos.draganddrop.R;

public enum Actions {


    ZOON_IN (R.string.action_zoom_in,R.string.description_zoom_in,R.drawable.ic_zoom_in),
    ZOON_OUT (R.string.action_zoom_out,R.string.description_zoom_out,R.drawable.ic_zoom_out),
    FLIP_HORIZONTAL(R.string.action_horizontal,R.string.description_flip_horizontal,R.drawable.ic_flip_horizontal),
    ROTATE_LEFT (R.string.action_rotate_left,R.string.description_rotate_left,R.drawable.ic_rotate_left),
    ROTATE_RIGHT (R.string.action_rotate_right,R.string.description_rotate_right,R.drawable.ic_rotate_right),
    FONT(R.string.action_font,R.string.description_font,R.drawable.ic_font_download),
    FLIP_FRONT (R.string.action_flip_front,R.string.description_flip_front,R.drawable.ic_flip_to_front),
    FLIP_BACK (R.string.action_flip_back,R.string.description_flip_back,R.drawable.ic_flip_to_back),
    COLOR (R.string.action_color,R.string.description_color,R.drawable.ic_palette),
    DELETE (R.string.action_delete,R.string.description_delete,R.drawable.ic_delete);



    private @StringRes int action;
    private @StringRes int description;
    private @DrawableRes int icon;

    Actions(int action, int description, int icon) {
        this.action = action;
        this.description = description;
        this.icon = icon;
    }

    public int getAction() {
        return action;
    }

    public int getDescription() {
        return description;
    }

    public int getIcon() {
        return icon;
    }
}
