package com.example.aleksandra.a4inrow.ui.activities;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Aleksandra on 03/04/2018.
 */

class ImageViewDecorator extends AbstractViewDecorator {
    private int picture;

    ImageViewDecorator(Context context, View view, int picture) {
        super(context, view);
        this.picture = picture;
        decorate();
    }

    @Override
    public void decorate() {
        if (view instanceof ImageView) {
            ((ImageView) view).setImageResource(picture);
        }
    }
}
