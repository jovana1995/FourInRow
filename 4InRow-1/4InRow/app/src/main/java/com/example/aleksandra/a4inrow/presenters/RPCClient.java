package com.example.aleksandra.a4inrow.presenters;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

/**
 * Created by Aleksandra on0/01/2018.
 */

class RPCClient {
    //private Connection connection;
    //private Channel channel;
    private String replyQueueName;
    private String requestQueueName;

    RPCClient(String s) throws IOException, TimeoutException {

        /*ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.SERVER_ADDRESS);
        factory.setUsername(Constants.host);
        factory.setPassword(Constants.password);//todo izmena

        connection = factory.newConnection();
        channel = connection.createChannel();*/

        RabbitMQFactory.createInstance();
        replyQueueName = RabbitMQFactory.getChannel().queueDeclare().getQueue();
        requestQueueName = s;
    }

    String call(String message) throws IOException, InterruptedException {


        final String corrId = UUID.randomUUID().toString();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        //String requestQueueName = "rpc_queue";
        RabbitMQFactory.getChannel().basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));

        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

        RabbitMQFactory.getChannel().basicConsume(replyQueueName, true, new DefaultConsumer(RabbitMQFactory.getChannel()) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                if (properties.getCorrelationId().equals(corrId)) {
                    response.offer(new String(body, "UTF-8"));
                }
            }
        });

        return response.take();
    }

    void close() throws IOException {
       // connection.close();
    }
}