package com.example.aleksandra.a4inrow.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.aleksandra.a4inrow.FourInRowApplication;
import com.example.aleksandra.a4inrow.models.GameModel;
import com.example.aleksandra.a4inrow.models.PlayerModel;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

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

    public static byte[] GameObjectToBytes(GameModel gm) {
        String temp = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                temp += gm.getElementAt(i, j) + " ";
            }
        }
        temp += gm.getPlayerNum();
        return temp.getBytes();
    }

    public static GameModel BytesToGameObject(byte[] bytes) {
        GameModel obj = new GameModel();
        String temp = new String(bytes);
        String[] parts = temp.split(" ");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                obj.setElementAt(i, j, parts[i * 5 + j].charAt(0));
            }
        }
        obj.setPlayerNum(Integer.valueOf(parts[42]));
        return obj;
    }

    public static byte[] PlayerObjectToBytes(PlayerModel pm) {
        String temp = "";
        temp += pm.getFirst_name() + "";
        temp += pm.getLast_name() + "";
        temp += pm.getDate_of_birth() + "";
        temp += pm.getGender() + "";
        temp += pm.getEmail() + "";
        temp += pm.getActive();
        return temp.getBytes();
    }

    public static PlayerModel BytesToPlayerObject(byte[] bytes) {
        PlayerModel pm = new PlayerModel();
        String temp = new String(bytes);
        String[] parts = temp.split(" ");
        pm.setFirst_name(parts[0]);
        pm.setLast_name(parts[1]);
        pm.setDate_of_birth(parts[2]);
        pm.setGender(parts[3]);
        pm.setEmail(parts[4]);
        pm.setActive(parts[5]);
        return pm;
    }

    public static void loadImageWithGlideCircle(Uri url, final ImageView imageView) {
        Glide.with(FourInRowApplication.getAppContext())
                .load(url)
                .fitCenter()
                .bitmapTransform(new CropCircleTransformation(FourInRowApplication.getAppContext()))
                .into(imageView);
    }
}
