package com.example.aleksandra.a4inrow.ui.activities;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Aleksandra on 02/04/2018.
 */

class TextViewDecorator extends AbstractViewDecorator {

    protected int color;
    protected String text;

    TextViewDecorator(Context context, View view, int color, String text) {
        super(context, view);
        this.color = color;
        this.text = text;
        decorate();
    }

    @Override
    public void decorate() {
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
            ((TextView) view).setTextColor(color);
            view.setVisibility(View.VISIBLE);
        }
    }
}
