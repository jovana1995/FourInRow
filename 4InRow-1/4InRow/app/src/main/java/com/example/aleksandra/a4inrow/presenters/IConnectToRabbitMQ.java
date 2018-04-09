package com.example.aleksandra.a4inrow.presenters;

/**
 * Created by Aleksandra on 16/01/2018.
 */

/**
 * Base class for objects that connect to a RabbitMQ Broker
 */
@SuppressWarnings({"WeakerAccess", "unused"})
abstract class IConnectToRabbitMQ {
    //public String mServer;
    public String mExchange;

    //Channel mModel = null;
    //private Connection  mConnection;

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
        //mServer = server;
        mExchange = exchange;
        MyExchangeType = exchangeType;
    }

    public void Dispose()
    {
        Running = false;

        /* if (mConnection!=null)
             mConnection.close();
         if (mModel != null)
             mModel.abort();*/

    }

    /**
     * Connect to the broker and create the exchange
     * @return success
     */
    public boolean connectToRabbitMQ()
    {
       // if(mModel!= null && mModel.isOpen() )//already declared
         //   return true;
        try
        {
            /*ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(mServer);
            connectionFactory.setPort(5672);
            connectionFactory.setUsername(Constants.host);
            connectionFactory.setPassword(Constants.password); //todo izmena
            mConnection = connectionFactory.newConnection();
            mModel = mConnection.createChannel();*/
            RabbitMQFactory.createInstance();
            RabbitMQFactory.getChannel().exchangeDeclare(mExchange, MyExchangeType, true);

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }
}

