package cs2340.ratapplication.controllers;
import android.os.AsyncTask;
import android.widget.Toast;

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

    private static String start;
    private static String end;

    private Button datepickerdialogbutton;
    private TextView selecteddate;
    private Button datepickerdialogbutton2;
    private TextView selecteddate2;

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
                validateForMap(start, end);
            }
        });
        searchForCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForChart(start, end);
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

    //  class that listens for when startDate is clicked
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
        // this is the inner class for startDate, handles and gives calendar options

        public void onDateSet(DatePicker view, int year, int month, int day){
            TextView textview = (TextView)getActivity().findViewById(R.id.displayStartDate);
            textview.setText((month+1) + "/" + day + "/"  + year);
            String y = year + "";
            String m = month + "";
            String d = day + "";
            if(m.length() < 2) {
                m = "0" + m;
            }
            if(d.length() < 2) {
                d = "0" + d;
            }
            start = y + "-" + m + "-" + d;
        }
    }
    //  class that listens for when endDate is clicked

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

  // this is the inner class for EndDate, handles and gives calendar options
        public void onDateSet(DatePicker view, int year, int month, int day){
            TextView textview2 = (TextView)getActivity().findViewById(R.id.displayEndDate);
            textview2.setText((month+1) + "/" + day + "/"  + year);

            String y = year + "";
            String m = month + "";
            String d = day + "";
            if(m.length() < 2) {
                m = "0" + m;
            }
            if(d.length() < 2) {
                d = "0" + d;
            }
            end = y + "-" + m + "-" + d;
        }
    }


//
    private void validateForMap(String start, String end){
        // how is this gonna  be passed in? Date
        if (start.length() != 10 || end.length() != 10){
            Toast.makeText(this, "Please enter a proper date", Toast.LENGTH_LONG).show();
        } else {
            mapAsync mpAsync = new mapAsync();
            mpAsync.execute(start, end);
        }
    }

    //
    private class mapAsync extends AsyncTask<String,Void, Boolean> {
        private long userID = 0;
        protected Boolean doInBackground(String... strs) {
            try{
                Intent intent = new Intent(SearchDatesForChartPage.this, MapsActivity.class);
                intent.putExtra("sightings", DatabaseHelper.getSightingsInRange(strs[0],  strs[1]));
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

    private void validateForChart(String start, String end){
        // how is this gonna  be passed in? Date
        if (start.length() != 10 || end.length() != 10){
            Toast.makeText(this, "Please enter a proper date", Toast.LENGTH_LONG).show();
        } else {
            chartAsync cAsync = new chartAsync();
            cAsync.execute(start, end);
        }
    }
    private class chartAsync extends AsyncTask<String,Void, Boolean> {
        private long userID = 0;
        protected Boolean doInBackground(String... strs) {
            try{
                System.out.println(strs[0] + " " + strs[1]);
                Intent intent = new Intent(SearchDatesForChartPage.this, ChartsPage.class);
                intent.putExtra("sightingsCount", DatabaseHelper.getSightingsCountByMonth(strs[0],  strs[1]));
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