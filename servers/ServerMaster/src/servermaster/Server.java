package servermaster;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JovanaStamenkovic
 */
public class Server {
    public static ServerMaster mServer=null;
     public static void main(String[] argv) throws Exception{
             if(mServer==null)
             {
                 mServer=ServerMaster.MakeServer();
             }
             ServerMaster.main(null);
          }
     }

