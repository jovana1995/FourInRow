package com.example.aleksandra.a4inrow.presenters;

import com.example.aleksandra.a4inrow.FourInRowApplication;
import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.models.PlayerModel;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils;
import com.example.aleksandra.a4inrow.utils.Utils;
import com.example.aleksandra.a4inrow.views.IProfileView;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Aleksandra on 20/01/2018.
 */

@SuppressWarnings("ConstantConditions")
public class ProfilePresenter extends BasePresenter<IProfileView> {

    public ProfilePresenter() {
    }

    public void getUser(int userId) throws IOException, TimeoutException {
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
                            client = new RPCClient("rpc_profile");
                            response = client.call(String.valueOf(userId));
                            getView().dismissProgressDialog();
                            final PlayerModel pm = new PlayerModel();
                            pm.BytesToPlayerObject(response.getBytes());
                            getView().setUser(pm);
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


    public void updateUser(final PlayerModel pm) throws IOException, TimeoutException {
        if (Utils.hasNetworkConnection()) {
            //if (isViewAttached()) {
                //getView().showSpinner(FourInRowApplication.getAppString(R.string.loading));
            //}

            new Thread() {
                public void run() {
                    try {
                        RPCClient client = null;
                        //String response;
                        try {
                            client = new RPCClient("rpc_profile_update");
                            String send = "";
                            send += SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID) + " ";
                            send += pm.getFirst_name() + " ";
                            send += pm.getLast_name() + " ";
                            send += pm.getDate_of_birth() + " ";
                            send += pm.getGender() + " ";
                            send += pm.getEmail() + " ";
                            send += pm.getActive();
                            //response = client.call(send);
                            client.call(send);
                            //getView().dismissProgressDialog();
                            //getView().showToast(response);
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
