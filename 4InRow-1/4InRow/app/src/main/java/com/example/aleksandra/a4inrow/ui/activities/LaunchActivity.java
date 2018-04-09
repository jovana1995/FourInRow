package com.example.aleksandra.a4inrow.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils;

public class LaunchActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID) == -1) {
            startActivity(new Intent(this, GoogleSignInActivity.class));
        } else {
            startActivity(new Intent(this, HomeActivity.class));
        }
    }
}
