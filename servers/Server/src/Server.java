/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

public class Server {

  private static final String RPC_QUEUE_NAME = "rpc_queue";

  public static void main(String[] argv) {
      
      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("192.168.1.6");

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

            System.out.println(" [.] zovu me " + message);
            response += "sandricaaa";
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