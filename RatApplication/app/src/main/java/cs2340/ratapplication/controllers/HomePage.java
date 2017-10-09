package cs2340.ratapplication.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

//import cs2340.ratapplication.models.UserDatabase;
import cs2340.ratapplication.models.DatabaseHelper;
import cs2340.ratapplication.R;



//

/*
This is the class that handles all of the login


 */

public class HomePage extends AppCompatActivity {
    private Button Logout;
    private Button Report;
    //private UserDatabase userDB;
    private TextView textView;
    private String username;
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //userDB = (UserDatabase) getIntent().getSerializableExtra("userDB");
        username = getIntent().getStringExtra("userID");
        textView = (TextView) findViewById(R.id.isAdmin);
        Logout = (Button) findViewById(R.id.logoutBtn);
        Report = (Button) findViewById(R.id.reportBtn);




        Logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, LoginPage.class);
                //intent.putExtra("userDB", userDB);
                startActivity(intent);
            }
        });


        Report.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, ReportPage.class);
                //intent.putExtra("userDB", userDB);
                intent.putExtra("userID", username);
                startActivity(intent);
            }
        });

        if(dbHelper.isAdmin(username)) {
            textView.setText("Admin Account");
        }


        try {
            loadData();
        }catch(Throwable t){}

    }

    protected void loadData() throws FileNotFoundException{
        Scanner dataFile = new Scanner(new File("/Users/data.csv")); //change this to look at ratdata.csv
        dataFile.useDelimiter(",");
        int counter = 0;
        String[] data = new String[10];
        while(dataFile.hasNext()) {
            counter = 0;
            data = new String[10];
            while (counter < data.length && dataFile.hasNext()) {
                data[counter] = dataFile.next();
                counter++;
            }
            //dbHelper.addSighting();
        }

    }

}
