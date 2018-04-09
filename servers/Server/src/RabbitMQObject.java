/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JovanaStamenkovic
 */
public class RabbitMQObject {
   public char[][] Matrix;
   public int PlayerNum;
   public RabbitMQObject()
   {
       Matrix=new char[6][7];
   }
   public byte[] ObjectToBytes()
   {
      String temp="";
      for(int i=0;i<6;i++)
      {  for(int j=0;j<7;j++)
          {
              temp+=Matrix[i][j]+" ";
          }
      }
      temp+=PlayerNum;
      byte[] array = temp.getBytes();
      return array;
   }
   public Object BytesToObject(byte[] bytes)
   {
       RabbitMQObject obj=new RabbitMQObject();
       String temp=new String(bytes);
       String[] parts = temp.split(" ");
       for(int i=0;i<6;i++)
       {  for(int j=0;j<7;j++)
           {
             obj.Matrix[i][j]=parts[i*5+j].charAt(0);
            
                
           }
       }
        PlayerNum=new Integer(parts[42]);
        
       return obj;
   }
    
}
