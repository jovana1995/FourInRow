package com.example.aleksandra.a4inrow.presenters;

import com.example.aleksandra.a4inrow.FourInRowApplication;
import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils;
import com.example.aleksandra.a4inrow.utils.Utils;
import com.example.aleksandra.a4inrow.views.IGameView;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Aleksandra on 02/12/2017.
 */

@SuppressWarnings("ConstantConditions")
public class GamePresenter extends BasePresenter<IGameView> {

    private MessageConsumer mConsumer;
    private static String exchange = "";

    public GamePresenter() {
    }

    public void receiveData() throws IOException, TimeoutException {
        if (Utils.hasNetworkConnection()) {
            if (isViewAttached()) {
                getView().showSpinner(FourInRowApplication.getAppString(R.string.other_player_playing));
            }

            //Create the consumer
            mConsumer = new MessageConsumer(Constants.SERVER_ADDRESS,
                    exchange,
                    "fanout");

            //Connect to broker
            new Thread() {
                public void run() {
                    mConsumer.connectToRabbitMQ();
                }
            }.start();


            //register for messages
            mConsumer.setOnReceiveMessageHandler(message -> {

                String s = new String(message, "UTF-8");
                String[] parts = s.split(" ");
                GamePresenter.this.getView().dismissProgressDialog();
                mConsumer.dispose();
                mConsumer.RemoveBinding("");

                if (!parts[0].equalsIgnoreCase("winner") && !parts[0].equalsIgnoreCase("gone") && Integer.parseInt(parts[1]) == SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) {
                    GamePresenter.this.getView().showDataAndWait();
                } else {
                    if (parts[0].equalsIgnoreCase("winner")) {

                    } else if (parts[0].equalsIgnoreCase("gone")) {
                        getView().otherPlayerHasGone();
                    } else {
                        GamePresenter.this.getView().showData(Integer.parseInt(parts[0]));
                    }
                }
            });
        } else if (

                isViewAttached())

        {
            getView().showToast(FourInRowApplication.getAppString(R.string.error_offline));
        }

    }


    public void sendData(final String message) throws IOException, TimeoutException {
        if (Utils.hasNetworkConnection()) {

            new Thread() {
                public void run() {
                    try {
                        ConnectionFactory e = new ConnectionFactory();
                        e.setHost(Constants.SERVER_ADDRESS);
                        e.setPort(5672);
                        Connection connection = e.newConnection();
                        Channel channel = connection.createChannel();
                        channel.queueDeclare(String.valueOf(SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) + exchange
                                , false, false, false, null);
                        channel.basicPublish("", String.valueOf(SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) + exchange
                                , null, message.getBytes("UTF-8"));
                    } catch (Exception var7) {
                        var7.printStackTrace();
                    }
                }
            }.start();
        } else if (isViewAttached()) {
            getView().showToast(FourInRowApplication.getAppString(R.string.error_offline));
        }
    }

    public void startGame() throws IOException, TimeoutException {
        if (Utils.hasNetworkConnection()) {

            new Thread() {
                public void run() {
                    try {
                        ConnectionFactory factory = new ConnectionFactory();
                        factory.setHost(Constants.SERVER_ADDRESS);
                        final Connection connection = factory.newConnection();
                        final Channel channel = connection.createChannel();

                        channel.queueDeclare("hello"
                                , false, false, false, null);
                        channel.basicPublish("", "hello",
                                null, (String.valueOf(SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) + "g").getBytes("UTF-8"));

                        channel.queueDeclare(String.valueOf(SharedPreferencesUtils.getInstance()
                                .getPrefInt(Constants.MY_ID)) + "g", false, false, false, null);

                        Consumer consumer = new DefaultConsumer(channel) {
                            @Override

                            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                                    throws IOException {
                                String response = new String(body, "UTF-8");
                                String[] parts = response.split(" ");
                                exchange = parts[1];
                                try {
                                    getView().start(Integer.parseInt(parts[0]));
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    channel.close();
                                } catch (TimeoutException ex) {
                                    ex.printStackTrace();
                                }
                                connection.close();
                            }
                        };
                        channel.basicConsume(String.valueOf(SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) + "g"
                                , true, consumer);

                    } catch (Exception var7) {
                        var7.printStackTrace();
                    }
                }
            }.start();
        } else if (isViewAttached()) {
            getView().showToast(FourInRowApplication.getAppString(R.string.error_offline));
        }
    }
}
