package com.example.aleksandra.a4inrow.ui.activities;

import android.content.Context;
import android.view.View;

import java.util.Arrays;

/**
 * Created by Aleksandra on 02/04/2018.
 */

@SuppressWarnings("unused")
abstract class AbstractViewDecorator implements Decorable {
    protected View view;
    protected View[] views;
    protected Context context;

    AbstractViewDecorator(Context context, View view){
        super();
        this.view = view;
        this.context = context;
    }

    public AbstractViewDecorator(Context context, View[] views){
        super();
        this.context = context;
        this.views = Arrays.copyOf(views, views.length);
    }

}
