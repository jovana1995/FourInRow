package rpcproducer;

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
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RPCProducer {

  private static final String RPC_QUEUE_NAME = "rpc_queue";
  private static HTTPRequests req=new HTTPRequests();
  public static void main(String[] argv) {
      
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("10.10.125.100");
 //   factory.setUsername("yourName");
   // factory.setPassword("yourPass");
    Connection connection = null;
    try {
      connection      = factory.newConnection();
      final Channel channel = connection.createChannel();

      channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);

      channel.basicQos(1);

      System.out.println(" [x] Awaiting RPC requests");

      Consumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
          AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                  .Builder()
                  .correlationId(properties.getCorrelationId())
                  .build();

          String response = "";

          try {
            String message = new String(body,"UTF-8");
            System.out.println(" Creating user... "+message );
            String par[]=message.split(" ");
            
            //Ovde se kreira novi igrac u bazi
            //zatim se uzimena njegov ID i vraca se odgovor
            //podaci se izvlace iz poruke
            JSONObject obj=new JSONObject();
         
            obj.put("First_name", par[0]);
            obj.put("Last_name",par[1]); 
            obj.put("Date_of_birth", par[2]);
            obj.put("Gender", par[3]); 
            obj.put("Email",par[4]);
            obj.put("Active",par[5]);
           
           
           
            
           
            req.Add(obj,"http://localhost:55735/api/Player");
            
            JSONArray arr=req.getAllData("http://localhost:55735/api/Player");
            int max=0;
            int b=0;
            for(int i=0;i<arr.size();i++)
            {
                JSONObject obj1=(JSONObject)arr.get(i);
                String s=obj1.get("Id_player").toString();
                b=Integer.parseInt(s);
                if(b>max){max=b;}
            }
           System.out.println(max);
            response+=max;
          //  response=89+" "+5;
            
          }
          catch (RuntimeException e){
            System.out.println(" [.] " + e.toString());
          }
          finally {
            channel.basicPublish( "", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));
            channel.basicAck(envelope.getDeliveryTag(), false);
            // RabbitMq consumer worker thread notifies the RPC server owner thread 
            synchronized(this) {
            	this.notify();
            }
          }
        }
      };

      channel.basicConsume(RPC_QUEUE_NAME, false, consumer);
      // Wait and be prepared to consume the message from RPC client.
      while (true) {
      	synchronized(consumer) {
      		try {
      			consumer.wait();
      	    } catch (InterruptedException e) {
      	    	e.printStackTrace();	    	
      	    }
      	}
      }
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
    finally {
      if (connection != null)
        try {
          connection.close();
        } catch (IOException _ignore) {}
    }
  }
}
