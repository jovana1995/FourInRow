package com.example.aleksandra.a4inrow.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.aleksandra.a4inrow.presenters.BasePresenter;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.views.IBaseView;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.io.Serializable;

/**
 * Created by Aleksandra on 02/12/2017.
 */

public abstract class BaseActivity <V extends IBaseView, P extends BasePresenter<V>>
        extends MvpActivity<V, P> implements IBaseView {

    protected Context mContext;
    private ProgressDialog progressDialog;
    protected LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mContext = this;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        localBroadcastManager = LocalBroadcastManager.getInstance(mContext);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgressDialog();
        getPresenter().onDestroy();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSpinner(String message) {
        progressDialog.setMessage(message);
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(dialog -> onBackPressed());
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void navigateToActivityWithExtras(Class<?> activity, String extra, int o) {
        Intent i = new Intent(this, activity);
        i.putExtra(Constants.EXTRAS, extra);
        i.putExtra(Constants.OBJECT, o);
        startActivity(i);
    }

    public void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 1);
    }

    public void closeKeyboard(View v) {                                                                  //view should be a button or radiobutton or checkbox or layout
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}

