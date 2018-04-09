package com.example.aleksandra.a4inrow.presenters;

import android.annotation.SuppressLint;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Aleksandra on 23/03/2018.
 */

@SuppressWarnings("unused")
public class RabbitMQFactory {
    private static ConnectionFactory connectionFactory = null;
    private static Connection connection = null;
    private static Channel channel = null;

    @SuppressLint("AuthLeak")
    static ConnectionFactory createInstance() {
        if (connectionFactory == null) {
            try {

                connectionFactory = new ConnectionFactory();
                connectionFactory.setUri("amqp://zinotxdo:GhFhdMu3OxPEf2hY55BEySYj0Z8Zqs52@skunk.rmq.cloudamqp.com/zinotxdo");
                //connectionFactory.setHost("10.10.248.154");
                //connectionFactory.setPassword("yourPass");
                //connectionFactory.setUsername("yourName");
                connection = connectionFactory.newConnection();
            } catch (URISyntaxException | KeyManagementException | NoSuchAlgorithmException | TimeoutException | IOException e1) {
                e1.printStackTrace();
            }
        }
        if(channel == null || !channel.isOpen()){
            try {
                channel = connection.createChannel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return connectionFactory;
    }

    static Channel getChannel() {
        return channel;
    }

    public static void closeAll() throws IOException, TimeoutException, ShutdownSignalException {
        if(connectionFactory != null) {
            if(channel.isOpen()) {
                channel.close();
            }
        }
    }
}
