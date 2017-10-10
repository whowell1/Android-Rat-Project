package cs2340.ratapplication.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

//import cs2340.ratapplication.models.UserDatabase;
import cs2340.ratapplication.models.DatabaseHelper;
import cs2340.ratapplication.R;
import cs2340.ratapplication.models.Sighting;

import java.net.URL;
import java.net.URLClassLoader;

//

/*
This is the class that handles all of the login


 */

public class HomePage extends AppCompatActivity {
    private Button Logout;
    private Button Report;
    //private UserDatabase userDB;
    private TextView textView;
    private TextView reports;
    private int userID;
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //userDB = (UserDatabase) getIntent().getSerializableExtra("userDB");
        userID = getIntent().getIntExtra("userID", 0);
        //userID = getIntent().getIntExtra("userID");
        textView = (TextView) findViewById(R.id.isAdmin);
        reports = (TextView) findViewById(R.id.reports);
        Logout = (Button) findViewById(R.id.logoutBtn);
        Report = (Button) findViewById(R.id.reportBtn);




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

        if(dbHelper.isAdmin(userID)) {
            textView.setText("Admin Account");
        }

        displayData();

    }

    protected void loadData() throws FileNotFoundException, URISyntaxException {
        Scanner dataFile = new Scanner(getResources().openRawResource(
                getResources().getIdentifier("ratdata", "raw", getPackageName()))
                );
        dataFile.useDelimiter(",");
        int counter = 0;
        String[] data = new String[7];

        //removes labels
        while(dataFile.hasNext() && counter < 51) {
            String fix = dataFile.next();
            counter++;
        }

        counter = 0;
        int entryCounter = 0;
        while(dataFile.hasNext() && entryCounter < 1000) {
            counter = 0;
            data = new String[7];
            while (counter < 52 && dataFile.hasNext()) {
                String val = dataFile.next();
                switch(counter) {
                    case 7 : data[0] = val;
                        break;
                    case 8 : data[4] = val;
                        break;
                    case 9 : data[1] = val;
                        break;
                    case 16 : data[2] = val;
                        break;
                    case 23 : data[3] = val;
                        break;
                    case 49 : data[5] = val;
                        break;
                    case 50: data[6] = val;
                        break;
                    default : break;

                }
                counter++;
            }
            System.out.println("Data[]: ");
            for(int i = 0; i < data.length; i++) {
                System.out.println("data[" + i + "] : " + data[i]);
            }

            dbHelper.addSighting(userID, new Timestamp(System.currentTimeMillis()), data[0],data[1],data[2],data[3],Integer.parseInt(data[4]),Float.parseFloat(data[5]),Float.parseFloat(data[6]));
            reports.append(data[4] + "\n");
            entryCounter++;
        }

    }
    protected void displayData() {
        if(dbHelper.sightingsEmpty()) {
            try {
                loadData();
            }catch(Throwable t){
                System.out.println("Error: " + t.getMessage());
            }
        }else {
            Sighting[] sightings = dbHelper.get50sightings();
            System.out.println("zip: " + sightings[0].zip);
            for(int i = 0; i < sightings.length;i++) {
                reports.append(sightings[i].zip + "\n");
            }
        }
    }

}
