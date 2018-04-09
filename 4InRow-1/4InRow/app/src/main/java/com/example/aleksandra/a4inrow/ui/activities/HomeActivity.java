package com.example.aleksandra.a4inrow.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.presenters.HomePresenter;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.utils.SharedPreferencesUtils;
import com.example.aleksandra.a4inrow.views.IHomeView;

public class HomeActivity extends BaseActivity<IHomeView, HomePresenter> {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    private void initViews() {
        Button btnPlay = (Button) findViewById(R.id.btn_play);
        Button btnProfile = (Button) findViewById(R.id.btn_profile);
        Button btnStatistics = (Button) findViewById(R.id.btn_statistics);

        btnPlay.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), GameActivity.class)));

        btnProfile.setOnClickListener(v -> this.navigateToActivityWithExtras(ProfileActivity.class, Constants.ID,
                SharedPreferencesUtils.getInstance().getPrefInt(Constants.MY_ID)));

        btnStatistics.setOnClickListener(v -> startActivity(new Intent(getBaseContext(), StatisticsActivity.class)));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* try {
            RabbitMQFactory.closeAll();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }*/
    }

    @NonNull
    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }
}
