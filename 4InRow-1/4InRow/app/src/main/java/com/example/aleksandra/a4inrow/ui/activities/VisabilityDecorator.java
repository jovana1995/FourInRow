package com.example.aleksandra.a4inrow.ui.activities;

import android.content.Context;
import android.view.View;

/**
 * Created by Aleksandra on 03/04/2018.
 */

class VisabilityDecorator extends AbstractViewDecorator {
    private boolean visability;

    VisabilityDecorator(Context context, View view, boolean visability) {
        super(context, view);
        this.visability = visability;
        decorate();
    }

    @Override
    public void decorate() {
        if (visability) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }
}
