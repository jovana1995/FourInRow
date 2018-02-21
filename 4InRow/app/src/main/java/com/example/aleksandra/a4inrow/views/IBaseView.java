package com.example.aleksandra.a4inrow.views;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Aleksandra on 02/12/2017.
 */

public interface IBaseView extends MvpView {
    void showToast(String message);
    void showSpinner(String message);
    void dismissProgressDialog();
}
