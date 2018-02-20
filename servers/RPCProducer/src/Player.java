/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JovanaStamenkovic
 */
public class Player {
 
        private  int Id_player; 
        private  String First_name;
        private  String Last_name;
        private  String Date_of_birth;
        private  String Gender ;
        private  String Email ;
        private  String Active ;
        private  Game PlaysGame;

    public Player(int Id_player,String First_name, String Last_name, String Date_of_birth, String Gender, String Email, String Active, Game PlaysGame) {
        this.Id_player=Id_player;
        this.First_name = First_name;
        this.Last_name = Last_name;
        this.Date_of_birth = Date_of_birth;
        this.Gender = Gender;
        this.Email = Email;
        this.Active = Active;
        this.PlaysGame = PlaysGame;
    }

    public Player() {
    }

    public void setId_player(int Id_player) {
        this.Id_player = Id_player;
    }

    public void setFirst_name(String First_name) {
        this.First_name = First_name;
    }

    public void setLast_name(String Last_name) {
        this.Last_name = Last_name;
    }

    public void setDate_of_birth(String Date_of_birth) {
        this.Date_of_birth = Date_of_birth;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setActive(String Active) {
        this.Active = Active;
    }

    public void setPlaysGame(Game PlaysGame) {
        this.PlaysGame = PlaysGame;
    }

    public int getId_player() {
        return Id_player;
    }

    public String getFirst_name() {
        return First_name;
    }

    public String getLast_name() {
        return Last_name;
    }

    public String getDate_of_birth() {
        return Date_of_birth;
    }

    public String getGender() {
        return Gender;
    }

    public String getEmail() {
        return Email;
    }

    public String getActive() {
        return Active;
    }

    public Game getPlaysGame() {
        return PlaysGame;
    }
        
        
}
