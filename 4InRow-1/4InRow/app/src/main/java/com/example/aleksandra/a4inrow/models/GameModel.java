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

    private char getElementAt(int i, int j) {
        return Matrix[i][j];
    }

    private void setElementAt(int i, int j, char a) {
        Matrix[i][j] = a;
    }

    public char[][] getMatrix() {
        return Matrix;
    }

    private int getPlayerNum() {
        return PlayerNum;
    }

    public void setMatrix(char[][] matrix) {
        Matrix = matrix;
    }

    private void setPlayerNum(int playerNum) {
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

    public byte[] GameObjectToBytes() {
        String temp = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                temp += this.getElementAt(i, j) + " ";
            }
        }
        temp += this.getPlayerNum();
        return temp.getBytes();
    }

    public void BytesToGameObject(byte[] bytes) {
        String temp = new String(bytes);
        String[] parts = temp.split(" ");
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                this.setElementAt(i, j, parts[i * 5 + j].charAt(0));
            }
        }
        this.setPlayerNum(Integer.valueOf(parts[42]));
    }
}
