/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JovanaStamenkovic
 */
public class Game {
        public  int Id_game;  
        public  String Game_date;

    public Game(String Game_date) {
        this.Game_date = Game_date;
    }

    public Game() {
    }

    public int getId_game() {
        return Id_game;
    }

    public String getGame_date() {
        return Game_date;
    }

    public void setGame_date(String Game_date) {
        this.Game_date = Game_date;
    }
        
       
}
