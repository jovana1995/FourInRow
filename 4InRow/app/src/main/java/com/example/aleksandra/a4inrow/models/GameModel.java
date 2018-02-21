package com.example.aleksandra.a4inrow.models;

import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils;

import java.io.Serializable;

/**
 * Created by Aleksandra on 17/01/2018.
 */

@SuppressWarnings("unused")
public class GameModel implements Serializable {

    private char[][] Matrix;
    private int PlayerNum;

    public GameModel() {
        Matrix = new char[6][7];
    }

    public char getElementAt(int i, int j) {
        return Matrix[i][j];
    }

    public void setElementAt(int i, int j, char a) {
        Matrix[i][j] = a;
    }

    public char[][] getMatrix() {
        return Matrix;
    }

    public int getPlayerNum() {
        return PlayerNum;
    }

    public void setMatrix(char[][] matrix) {
        Matrix = matrix;
    }

    public void setPlayerNum(int playerNum) {
        PlayerNum = playerNum;
    }

    @Override
    public String toString() {
        String s = "";
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 7; c++) {
                s += Matrix[c][r] + " ";
            }
        }
        s += SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID);
        return s;
    }
}
