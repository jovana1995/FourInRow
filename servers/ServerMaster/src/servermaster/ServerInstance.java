package servermaster;


import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Envelope;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author Aleksandra
 */

public class ServerInstance {
//vrednosti za oba queue se preuzimaju zi argv
  private  static String QUEUE_NAME1 = "104";
  private  static String QUEUE_NAME2 = "2";
  private static  String EXCHANGE_NAME = "6";

  public static void main(String[] argv) throws Exception {
    QUEUE_NAME1=argv[0];
    QUEUE_NAME2 =argv[1];
    EXCHANGE_NAME=argv[2];
    System.out.println("Server Instance "+QUEUE_NAME1+" "+QUEUE_NAME2+" "+EXCHANGE_NAME);
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("10.10.125.100");
   // factory.setUsername("yourName");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME1+EXCHANGE_NAME, false, false, false,null);
    channel.queueDeclare(QUEUE_NAME2+EXCHANGE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C-ServerInstance");
   try{
     Thread.sleep(1);}
         catch(InterruptedException e)
         {}
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
          throws IOException {
          
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + message + "'" + "now sending message to both customers");
        
 try{
           Thread.sleep(1);}
         catch(InterruptedException e)
         {}
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT, true);
        //String messageToSend = "Hello, I am your server";
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message
                + "'");    
        
       /* try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Recv.class.getName()).log(Level.SEVERE, null, ex);
        }*/
      }
      
   };
    channel.basicConsume(QUEUE_NAME1+EXCHANGE_NAME, true, consumer);
    channel.basicConsume(QUEUE_NAME2+EXCHANGE_NAME, true, consumer); 
    
    
    //channel.close();
  //  connection.close();
  }

  
}

