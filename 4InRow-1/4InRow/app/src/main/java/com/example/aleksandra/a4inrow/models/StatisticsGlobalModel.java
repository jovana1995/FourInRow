package com.example.aleksandra.a4inrow.models;

import java.io.Serializable;

/**
 * Created by Aleksandra on 04/04/2018.
 */

public class StatisticsGlobalModel implements Serializable {

    private String Id_player;
    private String Poens;

    public StatisticsGlobalModel() {
    }

    public String getId_player() {
        return Id_player;
    }

    public void setId_player(String id_player) {
        Id_player = id_player;
    }

    public String getPoens() {
        return Poens;
    }

    public void setPoens(String poens) {
        Poens = poens;
    }
}
