package com.example.aleksandra.a4inrow.presenters;

/**
 * Created by Aleksandra on 16/01/2018.
 */

import android.os.Handler;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Method;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class MessageConsumer extends  IConnectToRabbitMQ{

    MessageConsumer(String server, String exchange, String exchangeType) {
        super(server, exchange, exchangeType);
    }

    //The Queue name for this consumer
    private String mQueue;
    private QueueingConsumer MySubscription;

    //last message to post back
    private byte[] mLastMessage;

    // An interface to be implemented by an object that is interested in messages(listener)
    interface OnReceiveMessageHandler{
        void onReceiveMessage(byte[] message) throws IOException, TimeoutException;
    }

    //A reference to the listener, we can only have one at a time(for now)
    private OnReceiveMessageHandler mOnReceiveMessageHandler;

    /**
     *
     * Set the callback for received messages
     * @param handler The callback
     */
    void setOnReceiveMessageHandler(OnReceiveMessageHandler handler){
        mOnReceiveMessageHandler = handler;
    }

    private Handler mMessageHandler = new Handler();
    private Handler mConsumeHandler = new Handler();

    // Create runnable for posting back to main thread
    private final Runnable mReturnMessage = new Runnable() {
        public void run() {
            try {
                mOnReceiveMessageHandler.onReceiveMessage(mLastMessage);
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    };

    private final Runnable mConsumeRunner = this::Consume;

    /**
     * Create Exchange and then start consuming. A binding needs to be added before any messages will be delivered
     */
    @Override
    public boolean connectToRabbitMQ()
    {
        if(super.connectToRabbitMQ())
        {

            try {
                RabbitMQFactory.createInstance();
                mQueue = RabbitMQFactory.getChannel().queueDeclare().getQueue();
                MySubscription = new QueueingConsumer(RabbitMQFactory.getChannel());
                RabbitMQFactory.getChannel().basicConsume(mQueue, false, MySubscription);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            if (MyExchangeType.equalsIgnoreCase("fanout"))
                AddBinding("");//fanout has default binding

            Running = true;
            mConsumeHandler.post(mConsumeRunner);

            return true;
        }
        return false;
    }

    /**
     * Add a binding between this consumers Queue and the Exchange with routingKey
     * @param routingKey the binding key eg GOOG
     */
    private void AddBinding(String routingKey)
    {
        try {
            RabbitMQFactory.getChannel().queueBind(mQueue, mExchange, routingKey);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Remove binding between this consumers Queue and the Exchange with routingKey
     * @param routingKey the binding key eg GOOG
     */
    void RemoveBinding(String routingKey)
    {
        try {
            RabbitMQFactory.getChannel().queueUnbind(mQueue, mExchange, routingKey);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void Consume()
    {
        Thread thread = new Thread()
        {

            @Override
            public void run() {
                while(Running){
                    QueueingConsumer.Delivery delivery;
                    try {
                        delivery = MySubscription.nextDelivery(); //todo ovde puca
                        mLastMessage = delivery.getBody();
                        mMessageHandler.post(mReturnMessage);
                        try {
                            RabbitMQFactory.getChannel().basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException ie) {
                        ie.printStackTrace();
                    } catch(ShutdownSignalException e){
                        if(e.isHardError()){
                            Connection con = (Connection) e.getReference();
                            if(!e.isInitiatedByApplication()){
                                Method r = e.getReason();
                            } else {
                                Channel sh = (Channel) e.getReference();
                            }
                        }
                    }
                }
            }
        };
        thread.start();

    }

    void dispose(){
        Running = false;
    }
}
