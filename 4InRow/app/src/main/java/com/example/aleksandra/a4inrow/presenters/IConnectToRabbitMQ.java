package com.example.aleksandra.a4inrow.presenters;

/**
 * Created by Aleksandra on 16/01/2018.
 */

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.*;

/**
 * Base class for objects that connect to a RabbitMQ Broker
 */
@SuppressWarnings({"WeakerAccess", "unused"})
abstract class IConnectToRabbitMQ {
    public String mServer;
    public String mExchange;

    Channel mModel = null;
    private Connection  mConnection;

    boolean Running ;

    String MyExchangeType ;

    /**
     *
     * @param server The server address
     * @param exchange The named exchange
     * @param exchangeType The exchange type name
     */
    IConnectToRabbitMQ(String server, String exchange, String exchangeType)
    {
        mServer = server;
        mExchange = exchange;
        MyExchangeType = exchangeType;
    }

    public void Dispose()
    {
        Running = false;

        try {
            if (mConnection!=null)
                mConnection.close();
            if (mModel != null)
                mModel.abort();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * Connect to the broker and create the exchange
     * @return success
     */
    public boolean connectToRabbitMQ()
    {
        if(mModel!= null && mModel.isOpen() )//already declared
            return true;
        try
        {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(mServer);
            connectionFactory.setPort(5672);
            //connectionFactory.setUsername("yourName");
            //connectionFactory.setPassword("yourPass"); //todo izmena
            mConnection = connectionFactory.newConnection();
            mModel = mConnection.createChannel();
            mModel.exchangeDeclare(mExchange, MyExchangeType, true);

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}

