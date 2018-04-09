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
        public  int    Id_player;  
        public  String First_name;
        public  String Last_name ;
        public  String Date_of_birth;
        public  String Gender;
        public  String Email;
        public  String Active;
        public int Id_game;
        public Player(int i, String fn,String ln,String d,String g, String e, String a, int ig)
        {
              Id_player=i;  
              First_name=fn;
              Last_name=ln ;
              Date_of_birth=d;
              Gender=g;
              Email=e;
              Active=a;
              Id_game=ig;
        }
        
}
