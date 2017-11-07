package cs2340.ratapplication.controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;

import cs2340.ratapplication.models.DatabaseHelper;
import cs2340.ratapplication.R;
import cs2340.ratapplication.models.Sighting;


//

/*
This is the class that handles all of the login


 */

public class HomePage extends AppCompatActivity {
    private Button Logout;
    private Button Report;
    private TextView textView;
    private long userID;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        userID = getIntent().getLongExtra("userID", 0);
        textView = (TextView) findViewById(R.id.isAdmin);
        Logout = (Button) findViewById(R.id.logoutBtn);
        Report = (Button) findViewById(R.id.reportBtn);
        listView = (ListView) findViewById(R.id.reportList);
        System.out.println(userID);


        Logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, LoginPage.class);
                startActivity(intent);
            }
        });


        Report.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, ReportPage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        DisplayDataAsync ddAsync = new DisplayDataAsync();
        ddAsync.execute();

    }
    protected void displayData() {
            Sighting[] sightings = DatabaseHelper.get50sightings();
            List<String> sightingsNum = new ArrayList<String>();
            for(int i = 0; i< sightings.length; i++) {
                System.out.println(sightings[i].sightingID);
                sightingsNum.add("Sighting #" + sightings[i].sightingID);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent intent = new Intent(HomePage.this, DetailPage.class);
                    intent.putExtra("sightingID", Integer.parseInt(((TextView) arg1).getText().toString().substring(10)));
                    startActivity(intent);
                }
            });
            final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sightingsNum);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    listView.setAdapter(adapter);
                }
            });
    }
    protected class DisplayDataAsync extends AsyncTask<String,Void, Boolean> {
        private long userID = 0;
        protected Boolean doInBackground(String... strs) {
            try{
                displayData();
                return true;
            } catch (Throwable t) {
                System.out.println(t);
                return false;
            }
        }

        protected void onPostExecute(Boolean result) {
            Toast.makeText(HomePage.this, "Finished Loading", Toast.LENGTH_LONG).show();
        }
    }
}
