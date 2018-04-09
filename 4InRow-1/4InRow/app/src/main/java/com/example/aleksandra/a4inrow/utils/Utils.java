package com.example.aleksandra.a4inrow.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.ImageView;

import com.example.aleksandra.a4inrow.FourInRowApplication;
import com.example.aleksandra.a4inrow.models.StatisticsGlobalModel;
import com.example.aleksandra.a4inrow.models.StatisticsLocalModel;

import java.util.ArrayList;

/**
 * Created by Aleksandra on 02/12/2017.
 */

@SuppressWarnings("unused")
public class Utils {

    public static boolean hasNetworkConnection() {
        ConnectivityManager cm = (ConnectivityManager) FourInRowApplication.getAppContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static ArrayList<StatisticsLocalModel> BytesToStatisticsLocalObject(byte[] bytes) {
        ArrayList<StatisticsLocalModel> pm = new ArrayList<>();
        String temp = new String(bytes);
        String[] parts = temp.split("_");
        for (String part : parts) {
            String[] statistic = part.split(" ");
            StatisticsLocalModel sm = new StatisticsLocalModel();
            sm.setId_statistic(statistic[0]);
            sm.setStatistic_date(statistic[1]);
            sm.setWins(statistic[2]);
            sm.setDefeats(statistic[3]);
            pm.add(sm);
        }
        return pm;
    }

    public static ArrayList<StatisticsGlobalModel> BytesToStatisticsGlobalObject(byte[] bytes) {
        ArrayList<StatisticsGlobalModel> pm = new ArrayList<>();
        String temp = new String(bytes);
        String[] parts = temp.split("_");
        for (String part : parts) {
            String[] statistic = part.split(" ");
            StatisticsGlobalModel sm = new StatisticsGlobalModel();
            sm.setId_player(statistic[0]);
            sm.setPoens(statistic[1]);
            pm.add(sm);
        }
        return pm;
    }

    public static void loadImageWithGlideCircle(Uri url, final ImageView imageView) {
        /*Glide.with(FourInRowApplication.getAppContext())
                .load(url)
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(FourInRowApplication.getAppContext()))
                .into(imageView);*/
    }
}
