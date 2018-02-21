package com.example.aleksandra.a4inrow.views;

import com.example.aleksandra.a4inrow.models.GameModel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Aleksandra on 02/12/2017.
 */

public interface IGameView extends IBaseView {
    void showData(int col) throws IOException, TimeoutException;
    void showDataAndWait() throws IOException, TimeoutException;
    void finishSending() throws IOException, TimeoutException;
    void start(int i) throws IOException, TimeoutException;
    void otherPlayerHasGone();
}
