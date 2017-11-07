package cs2340.ratapplication.controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import cs2340.ratapplication.R;
import cs2340.ratapplication.models.DatabaseHelper;

public class SearchDatesForChartPage extends AppCompatActivity {
    private Button searchForCharts;
    private Button searchForSighting;
    private EditText startDateCharts;
    private EditText endDateCharts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dates_for_chart);
        searchForSighting = (Button) findViewById(R.id.searchForSighting);
        searchForCharts = (Button) findViewById(R.id.searchForCharts);
        startDateCharts = (EditText) findViewById(R.id.startDateCharts);
        endDateCharts = (EditText) findViewById(R.id.endDateCharts);


        searchForSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MapAsync mpAsync = new MapAsync();
                mpAsync.execute();
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

    protected class MapAsync extends AsyncTask<String,Void, Boolean> {
        private long userID = 0;
        protected Boolean doInBackground(String... strs) {
            try{
                Intent intent = new Intent(SearchDatesForChartPage.this, MapsActivity.class);
                intent.putExtra("sightings", DatabaseHelper.get50sightings());
                startActivity(intent);
                return true;
            } catch (Throwable t) {
                System.out.println(t);
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {

        }
    }


}