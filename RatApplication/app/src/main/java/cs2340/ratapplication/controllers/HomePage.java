package cs2340.ratapplication.controllers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private int userID;
    private ListView listView;
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //userDB = (UserDatabase) getIntent().getSerializableExtra("userDB");
        userID = getIntent().getIntExtra("userID", 0);
        //userID = getIntent().getIntExtra("userID");
        textView = (TextView) findViewById(R.id.isAdmin);
        Logout = (Button) findViewById(R.id.logoutBtn);
        Report = (Button) findViewById(R.id.reportBtn);
        listView = (ListView) findViewById(R.id.reportList);



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
        while (dataFile.hasNext() && counter < 51) {
            String fix = dataFile.next();
            counter++;
        }

        counter = 0;
        int entryCounter = 0;
        while (dataFile.hasNext() && entryCounter < 1000) {
            counter = 0;
            data = new String[7];
            while (counter < 52 && dataFile.hasNext()) {
                String val = dataFile.next();
                switch (counter) {
                    case 7:
                        data[0] = val;
                        break;
                    case 8:
                        data[4] = val;
                        break;
                    case 9:
                        data[1] = val;
                        break;
                    case 16:
                        data[2] = val;
                        break;
                    case 23:
                        data[3] = val;
                        break;
                    case 49:
                        data[5] = val;
                        break;
                    case 50:
                        data[6] = val;
                        break;
                    default:
                        break;

                }
                counter++;
            }

            dbHelper.addSighting(userID, new Timestamp(System.currentTimeMillis()), data[0], data[1], data[2], data[3], Integer.parseInt(data[4]), Float.parseFloat(data[5]), Float.parseFloat(data[6]));
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
        }
            Sighting[] sightings = dbHelper.get50sightings();
            List<Long> sightingsNum = new ArrayList<Long>();
            for(int i = 0; i< sightings.length; i++) {
                sightingsNum.add(sightings[i].sightingID);
            }

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    Intent intent = new Intent(HomePage.this, DetailPage.class);
                    intent.putExtra("sightingID", arg2 + 1);
                    startActivity(intent);
                }
            });
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, sightingsNum);
            listView.setAdapter(adapter);
    }

}
