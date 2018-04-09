package com.example.aleksandra.a4inrow.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.aleksandra.a4inrow.R;
import com.example.aleksandra.a4inrow.models.StatisticsGlobalModel;
import com.example.aleksandra.a4inrow.models.StatisticsLocalModel;
import com.example.aleksandra.a4inrow.presenters.StatisticsPresenter;
import com.example.aleksandra.a4inrow.utils.Constants;
import com.example.aleksandra.a4inrow.views.IStatisticsView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Aleksandra on 30/03/2018.
 */

@SuppressWarnings("unchecked")
public class StatisticsActivity extends BaseActivity<IStatisticsView, StatisticsPresenter>
        implements IStatisticsView {

    private static ArrayList<StatisticsLocalModel> statistikaLokal = null;
    private static ArrayList<StatisticsGlobalModel> statistikaGlobal = null;
    private static ArrayList<String> onX;
    private static boolean local = true;
    private BaseActivity activity;

    @NonNull
    @Override
    public StatisticsPresenter createPresenter() {
        return new StatisticsPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        activity = this;
    }

    @Override
    public void setGraphLocal(ArrayList<StatisticsLocalModel> sm) {
        statistikaLokal = sm;
    }

    @Override
    public void setGraphGlobal(ArrayList<StatisticsGlobalModel> sm) {
        statistikaGlobal = sm;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.option_local) {
            try {
                statistikaLokal = null;
                getPresenter().getStatisticsLocal();
                local = true;
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (statistikaLokal != null) {
                PopulateGraph();
            } else {
                dismissProgressDialog();
                showToast(getString(R.string.something_wrong));
            }
            statistikaLokal = null;
        } else if (id == R.id.option_global) {
            try {
                statistikaGlobal = null;
                getPresenter().getStatisticsGlobal();
                local = false;
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (statistikaGlobal != null) {
                PopulateGraph();
            } else {
                dismissProgressDialog();
                showToast(getString(R.string.something_wrong));
            }
            statistikaGlobal = null;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        try {
            statistikaLokal = null;
            getPresenter().getStatisticsLocal();
            local = true;
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (statistikaLokal != null) {
            PopulateGraph();
        } else {
            dismissProgressDialog();
            showToast(getString(R.string.something_wrong));
        }
        statistikaLokal = null;
    }

    public void PopulateGraph() {
        onX = new ArrayList<>();
        ArrayList<Integer> onY = new ArrayList<>();
        if (local) {
            for (int i = 0; i < statistikaLokal.size(); i++) {
                onX.add(statistikaLokal.get(i).getStatistic_date());
                onY.add(Integer.parseInt(statistikaLokal.get(i).getWins()) - Integer.parseInt(statistikaLokal.get(i).getDefeats()));
            }
        } else {
            for (int i = 0; i < statistikaGlobal.size(); i++) {
                onX.add(statistikaGlobal.get(i).getId_player());
                onY.add(Integer.parseInt(statistikaGlobal.get(i).getPoens()));
            }
        }

        BarChart barChart = (BarChart) findViewById(R.id.bargraph);
        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < onX.size(); i++) {
            entries.add(new BarEntry(i, onY.get(i)));
        }

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(onX));

        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        data.setBarWidth(1f);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.invalidate();

        barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                BarEntry be = (BarEntry) entry;
                float x = be.getX();
                if (local) {
                    if (x == 3f) {
                        startActivity(new Intent(mContext, HomeActivity.class));
                    } else {
                        Toast.makeText(getApplicationContext(), be.toString(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    activity.navigateToActivityWithExtras(ProfileActivity.class, Constants.ID, Integer.parseInt(onX.get((int)x)));
                }
            }

            @Override
            public void onNothingSelected() {
            }
        });
    }
}
