package cs2340.ratapplication.controllers;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import cs2340.ratapplication.R;
import cs2340.ratapplication.models.DatabaseHelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.widget.DatePicker;

import cs2340.ratapplication.R;

public class SearchDatesForChartPage extends AppCompatActivity {
    private Button searchForCharts;
    private Button searchForSighting;
    private EditText startDateCharts;
    private EditText endDateCharts;

    Button datepickerdialogbutton;
    TextView selecteddate;
    Button datepickerdialogbutton2;
    TextView selecteddate2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dates_for_chart);
        searchForSighting = (Button) findViewById(R.id.searchForSighting);
        searchForCharts = (Button) findViewById(R.id.searchForCharts);
//        startDateCharts = (EditText) findViewById(R.id.startDateCharts);
//        endDateCharts = (EditText) findViewById(R.id.endDateCharts);


        datepickerdialogbutton = (Button)findViewById(R.id.startDate);
        selecteddate = (TextView)findViewById(R.id.displayStartDate);
        datepickerdialogbutton2 = (Button)findViewById(R.id.endDate);
        selecteddate2 = (TextView)findViewById(R.id.displayEndDate);

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

        datepickerdialogbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogfragment = new DatePickerStartDate();
                dialogfragment.show(getFragmentManager(), "Date Picker Dialog");
            }
        });

        datepickerdialogbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogfragment2 = new DatePickerEndDate();
                dialogfragment2.show(getFragmentManager(), "Date Picker Dialog");

            }
        });
    }

    public static class DatePickerStartDate extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK,this,year,month,day);
            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            TextView textview = (TextView)getActivity().findViewById(R.id.displayStartDate);
            textview.setText((month+1) + "/" + day + "/"  + year);
        }
    }

    public static class DatePickerEndDate extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState){
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datepickerdialog = new DatePickerDialog(getActivity(),
                    AlertDialog.THEME_DEVICE_DEFAULT_DARK,this,year,month,day);

            return datepickerdialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day){
            TextView textview2 = (TextView)getActivity().findViewById(R.id.displayEndDate);
            textview2.setText((month+1) + "/" + day + "/"  + year);
        }
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