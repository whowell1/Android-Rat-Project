package cs2340.ratapplication.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cs2340.ratapplication.R;

public class SearchDatesForChartPage extends AppCompatActivity {
    private Button searchForCharts;
    private Button searchForSighting;
    private EditText startDateCharts;
    private EditText endDateCharts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dates_for_chart);
        searchForSighting = (Button) findViewById(R.id.searchForCharts);
        searchForCharts = (Button) findViewById(R.id.searchForSighting);
        startDateCharts = (EditText) findViewById(R.id.startDateCharts);
        endDateCharts = (EditText) findViewById(R.id.endDateCharts);


        searchForSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchDatesForChartPage.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        searchForCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchDatesForChartPage.this, ChartsPage.class);
                startActivity(intent);
            }
        });
    }

    private void validate(String startDate, String endDate){
        // how is this gonna  be passed in? Date
        if (startDate.matches("[a-zA-Z]+") || endDate.matches("[a-zA-Z]+")){
            Toast.makeText(this, "Please enter a proper date", Toast.LENGTH_LONG).show();
        }


    }




}