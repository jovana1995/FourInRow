package com.example.aleksandra.a4inrow.ui.activities;

/**
 * Created by Aleksandra on 22/01/2018.
 */
class Cell {
  boolean empty;
  Board.Turn player;

  Cell() {
    empty = true;
  }

  void setPlayer(Board.Turn player) {
    this.player = player;
    empty = false;
  }
}
