package com.example.aleksandra.a4inrow.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.presenters.LogInPresenter;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils;
import com.example.aleksandra.a4inrow.views.ILogInView;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;

public class GoogleSignInActivity extends BaseActivity<ILogInView, LogInPresenter>
        implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, ILogInView {

    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestProfile()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(this);
    }

    @NonNull
    @Override
    public LogInPresenter createPresenter() {
        return new LogInPresenter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            try {
                handleSignInResult(result);
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                if (getIntent().getExtras() != null && getIntent().getBooleanExtra(Constants.EXTRAS, true)) {
                    signOut();
                }
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showToast("Connection failed!");
    }

    private void handleSignInResult(GoogleSignInResult result) throws IOException, TimeoutException {
        if (result.isSuccess()) {
            // Signed in successfully
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                if(acct.getPhotoUrl() != null) {
                    SharedPreferencesUtils.getInstance().putPrefString(Constants.IMAGE_URL,
                            acct.getPhotoUrl().toString());
                } else {
                    SharedPreferencesUtils.getInstance().putPrefString(Constants.IMAGE_URL,
                            "no photo");
                }
                String s = "";
                s += acct.getGivenName() + " ";
                s += acct.getFamilyName() + " ";
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                s += mdformat.format(calendar.getTime()) + " ";
                s += "M" + " ";
                s += acct.getEmail() + " ";
                s += "A";
                presenter.addUser(s);
            }
        } else {
            // Sign in failed
            showToast("Sign in error");
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                status -> GoogleSignInActivity.this.showToast("Invalid email!!"));
    }

    @Override
    public void logInSuccessful(String s) {
        if(Integer.parseInt(s) == -1)
        {
            signOut();
        }else {
            SharedPreferencesUtils.getInstance().cacheId(Integer.parseInt(s));
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
