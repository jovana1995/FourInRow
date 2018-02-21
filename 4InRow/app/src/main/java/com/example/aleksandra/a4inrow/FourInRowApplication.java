package com.example.aleksandra.a4inrow;

import android.content.res.Resources;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Aleksandra on 02/12/2017.
 */

public class FourInRowApplication  extends MultiDexApplication {

    private static FourInRowApplication applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = this;
    }

    public static FourInRowApplication getAppContext() {
        return applicationContext;
    }

    public static Resources getProjectResources() {
        return applicationContext.getResources();
    }

    public static String getAppString(int stringId) {
        return applicationContext.getResources().getString(stringId);
    }
}
