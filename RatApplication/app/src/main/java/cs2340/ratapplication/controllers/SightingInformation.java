package cs2340.ratapplication.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileNotFoundException;
import cs2340.ratapplication.R;


import cs2340.ratapplication.models.DatabaseHelper;
import cs2340.ratapplication.models.Sighting;



public class SightingInformation extends AppCompatActivity {
    private Button seeSightingBtn;
    private Button seeSightingGraphBtn;
    private int userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighting_information);
        seeSightingBtn = (Button) findViewById(R.id.seeSightingBtn);
        seeSightingGraphBtn = (Button) findViewById(R.id.seeSightingGraphBtn);



        seeSightingGraphBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(SightingInformation.this, ChartsPage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        seeSightingBtn.setOnClickListener(new View.OnClickListener() {
           public void onClick(View view) {
               Intent intent = new Intent(SightingInformation.this, MapsActivity.class);
               intent.putExtra("userID", userID);
              startActivity(intent);
           }
       });
    }
}
