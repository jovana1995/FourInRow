package com.example.aleksandra.a4inrow.models;

import java.io.Serializable;

/**
 * Created by Aleksandra on 30/03/2018.
 */

@SuppressWarnings("unused")
public class StatisticsLocalModel implements Serializable {

    private String Id_statistic;
    private String Statistic_date;
    private String Wins;
    private String Defeats;

    public StatisticsLocalModel() {
    }

    public String getId_statistic() {
        return Id_statistic;
    }

    public void setId_statistic(String id_statistic) {
        Id_statistic = id_statistic;
    }

    public String getStatistic_date() {
        return Statistic_date;
    }

    public void setStatistic_date(String statistic_date) {
        Statistic_date = statistic_date;
    }

    public String getWins() {
        return Wins;
    }

    public void setWins(String wins) {
        Wins = wins;
    }

    public String getDefeats() {
        return Defeats;
    }

    public void setDefeats(String defeats) {
        Defeats = defeats;
    }
}
