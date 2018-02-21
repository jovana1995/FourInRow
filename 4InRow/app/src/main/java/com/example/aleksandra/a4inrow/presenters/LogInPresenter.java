package com.example.aleksandra.a4inrow.presenters;

import com.example.aleksandra.a4inrow.FourInRowApplication;
import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.utils.Utils;
import com.example.aleksandra.a4inrow.views.ILogInView;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Aleksandra on 20/01/2018.
 */

@SuppressWarnings("ConstantConditions")
public class LogInPresenter extends BasePresenter<ILogInView> {

    public LogInPresenter() {
    }

    public void addUser(final String s) throws IOException, TimeoutException {
        if (Utils.hasNetworkConnection()) {
            if (isViewAttached()) {
                getView().showSpinner(FourInRowApplication.getAppString(R.string.loading));
            }

            new Thread() {
                public void run() {
                    try {
                        RPCClient client = null;
                        String response;
                        try {
                            client = new RPCClient("rpc_queue");
                            response = client.call(s);
                            getView().dismissProgressDialog();
                            getView().logInSuccessful(response);
                        } catch (IOException | TimeoutException | InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            if (client != null) {
                                try {
                                    client.close();
                                } catch (IOException ignored) {
                                }
                            }
                        }

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

