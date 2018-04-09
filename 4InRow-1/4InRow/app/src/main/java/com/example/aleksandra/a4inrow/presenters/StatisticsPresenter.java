package com.example.aleksandra.a4inrow.presenters;

import com.example.aleksandra.a4inrow.FourInRowApplication;
import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.models.StatisticsGlobalModel;
import com.example.aleksandra.a4inrow.models.StatisticsLocalModel;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils;
import com.example.aleksandra.a4inrow.utils.Utils;
import com.example.aleksandra.a4inrow.views.IStatisticsView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by Aleksandra on 23/03/2018.
 */

@SuppressWarnings("ConstantConditions")
public class StatisticsPresenter extends BasePresenter<IStatisticsView> {

    public void getStatisticsLocal() throws IOException, TimeoutException {
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
                            client = new RPCClient("rpc_statistic_local");
                            response = client.call(String.valueOf(SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)));
                            getView().dismissProgressDialog();
                            final ArrayList<StatisticsLocalModel> sm = Utils.BytesToStatisticsLocalObject(response.getBytes());
                            getView().setGraphLocal(sm);
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

    public void getStatisticsGlobal() throws IOException, TimeoutException {
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
                            client = new RPCClient("rpc_statistic_global");
                            response = client.call("global");
                            getView().dismissProgressDialog();
                            final ArrayList<StatisticsGlobalModel> sm = Utils.BytesToStatisticsGlobalObject(response.getBytes());
                            getView().setGraphGlobal(sm);
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
