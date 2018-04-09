package com.example.aleksandra.a4inrow.views;

import com.example.aleksandra.a4inrow.models.StatisticsGlobalModel;
import com.example.aleksandra.a4inrow.models.StatisticsLocalModel;

import java.util.ArrayList;

/**
 * Created by Aleksandra on 23/03/2018.
 */

public interface IStatisticsView extends IBaseView {
     void setGraphLocal(ArrayList<StatisticsLocalModel> sm);
     void setGraphGlobal(ArrayList<StatisticsGlobalModel> sm);
}
