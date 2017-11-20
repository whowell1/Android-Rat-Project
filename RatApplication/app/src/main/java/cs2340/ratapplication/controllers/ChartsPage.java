package cs2340.ratapplication.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.*;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import cs2340.ratapplication.R;
import cs2340.ratapplication.models.MonthCount;

public class ChartsPage extends AppCompatActivity {
    private BarChart barChart;
    private MonthCount[] count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        barChart =(BarChart) findViewById(R.id.barchart);
        count = (MonthCount[]) getIntent().getSerializableExtra("sightingsCount");

        List<BarEntry> entries = new ArrayList<>();

        for(int i = 0; i < count.length; i++) {
            entries.add(new BarEntry(i, count[i].count));
        }



        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh

//        bchart.setTouchEnabled(false);
//        bchart.setData(data);


    }
}
