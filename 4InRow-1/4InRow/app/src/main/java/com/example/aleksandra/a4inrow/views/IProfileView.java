package com.example.aleksandra.a4inrow.views;

import com.example.aleksandra.a4inrow.models.PlayerModel;

/**
 * Created by AleksandraStanojevic on 7/27/2017
 */

public interface IProfileView extends IBaseView {
    void setUser(PlayerModel data);
    void showUser(PlayerModel data);
}
