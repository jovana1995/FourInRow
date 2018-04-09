package com.example.aleksandra.a4inrow.presenters;

import com.example.aleksandra.a4inrow.FourInRowApplication;
import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils;
import com.example.aleksandra.a4inrow.utils.Utils;
import com.example.aleksandra.a4inrow.views.IGameView;
import com.rabbitmq.client.AMQP;
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
                getView().setReceived();
                String s = new String(message, "UTF-8");
                String[] parts = s.split(" ");
                GamePresenter.this.getView().dismissProgressDialog();
                mConsumer.dispose();
               // mConsumer.RemoveBinding("");


                if (!parts[0].equalsIgnoreCase("gone") && Integer.parseInt(parts[1]) == SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) {
                    GamePresenter.this.getView().showDataAndWait();
                } else {
                    if (parts[0].equalsIgnoreCase("gone")) {
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
                        RabbitMQFactory.createInstance();
                        RabbitMQFactory.getChannel().queueDeclare(String.valueOf(SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) + exchange
                                , false, false, false, null);
                        RabbitMQFactory.getChannel().basicPublish("", String.valueOf(SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) + exchange
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
                        RabbitMQFactory.createInstance();
                        RabbitMQFactory.getChannel().queueDeclare("hello"
                                , false, false, false, null);
                        RabbitMQFactory.getChannel().basicPublish("", "hello",
                                null, (String.valueOf(SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) + "g").getBytes("UTF-8"));

                        RabbitMQFactory.getChannel().queueDeclare(String.valueOf(SharedPreferencesUtils.getInstance()
                                .getPrefInt(Constants.MY_ID)) + "g", false, false, false, null);

                        Consumer consumer = new DefaultConsumer(RabbitMQFactory.getChannel()) {
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
                                /*
                                try {
                                    receiveData(1);
                                } catch (TimeoutException e) {
                                    e.printStackTrace();
                                }*/
                            }
                        };
                        RabbitMQFactory.getChannel().basicConsume(String.valueOf(SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)) + "g"
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
