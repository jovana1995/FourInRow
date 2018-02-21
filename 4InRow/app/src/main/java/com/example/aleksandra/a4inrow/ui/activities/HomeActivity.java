package com.example.aleksandra.a4inrow.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.aleksandra.a4inrow.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    private void initViews() {
        Button btnPlay = (Button) findViewById(R.id.btn_play);
        Button btnProfile = (Button) findViewById(R.id.btn_profile);
        //Button btnStatistics = (Button) findViewById(R.id.btn_statistics);

        btnPlay.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), GameActivity.class)));

        btnProfile.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), ProfileActivity.class)));
    }
}
