/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servermaster;

/**
 *
 * @author Aleksandra
 */
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Envelope;
import java.io.BufferedReader;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class ServerMaster extends Server {
 
  private final static String QUEUE_NAME = "hello";
  private static int num=0,num2=0;
  private static String queue1;
  private static String queue2;
  private static HTTPRequests req=new HTTPRequests();
  public static void main(String[] argv) throws Exception{
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("10.10.125.100");
//
    
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'"); 
        
       
        if(num==1)
        {
         
            queue2=message;
            JSONObject obj=new JSONObject();
            obj.put("Game_date","nn");
            //DODAJE NOVU IGRU
            req.Add(obj, "http://localhost:55735/api/Game");
            //NALAZI SVE IGRE
            JSONArray arr=req.getAllData("http://localhost:55735/api/Game");
   
            
           //NALAZI SVE ID-IJE, NALAZI NAJVECI..TO JE MAX ID
            int max=0;
            int b=0;
            for(int i=0;i<arr.size();i++)
            {
                JSONObject obj1=(JSONObject)arr.get(i);
                String s=obj1.get("Id_game").toString();
                b=Integer.parseInt(s);
                if(b>max){max=b;}
            }
            //treba max da vrac;a
            //ovde treba max usmesto 5
          
           
            
            
            JSONObject mess=new JSONObject();
            mess.put("Id_game",max);
            mess.put("Id_date","nn");
            System.out.println(mess.toString());
            
            String q1=queue1.substring(0, queue1.length()-1);
            
             JSONObject  obj1=req.GetData("http://localhost:55735/api/Player/"+q1);
            obj1.put("PlaysGame", mess);
            req.Update(obj1.toString(),"http://localhost:55735/api/Player/"+q1);
           
            String q2=queue2.substring(0, queue2.length()-1);
            obj1=req.GetData("http://localhost:55735/api/Player/"+q2);       
            obj1.put("PlaysGame", mess);
            req.Update(obj1.toString(),"http://localhost:55735/api/Player/"+q2);
            String message1=q1+" "+max;
            
            //kreira novi game
            //updatuje Igrace sa id-jevima u queue1 i queue2 tako sto im dodaje novi  id game
            //vraca id game
            //pokrece novi server za tu igru
            //prosledju mu id game id player-a
            
         //   message=queue1+" "+queue2;
        try{
           Thread.sleep(3);}
         catch(InterruptedException e)
         {}
            channel.queueDeclare(queue1, false, false, false, null);
            channel.basicPublish("", queue1,null, message1.getBytes("UTF-8"));
            
            channel.queueDeclare(queue2, false, false, false, null);
            channel.basicPublish("", queue2,null, message1.getBytes("UTF-8"));
            num=0;
            System.out.println(message1);
          
                       
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome +
        File.separator + "bin" +
        File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = ServerInstance.class.getCanonicalName();
        
      
       
           ProcessBuilder builder = new ProcessBuilder(
                javaBin, "-cp", classpath, className,q1,q2,max+"");

        Process process = builder.start();
        System.out.println("main");
     /*   try{
        process.waitFor(5, TimeUnit.SECONDS);
        }
        catch(InterruptedException w)
        {}*/
        /*
      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line;
 
      System.out.printf("Output of running %s is:", 
          Arrays.toString(argv));
 
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
        System.out.println(process.exitValue());
  */
        } 
        else {queue1=message; num=1;}
       
      }
    };
    
    channel.basicConsume(QUEUE_NAME, true, consumer); 
    
    
    
     
    } 
  public void Send() throws Exception
  {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("localhost");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    String message = "Hello World!";
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
    System.out.println(" [x] Sent '" + message + "'");

    channel.close();
    connection.close();
 }
public static ServerMaster MakeServer()
{
    return new ServerMaster();
}
}
  
