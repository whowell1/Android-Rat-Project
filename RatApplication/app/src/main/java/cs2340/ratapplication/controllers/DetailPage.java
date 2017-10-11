package cs2340.ratapplication.controllers;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs2340.ratapplication.models.*;

import org.w3c.dom.Text;

import cs2340.ratapplication.R;
import cs2340.ratapplication.models.DatabaseHelper;

public class DetailPage extends AppCompatActivity {
    private DatabaseHelper helper = DatabaseHelper.getInstance(this);


    private TextView Details;
    private DatabaseHelper dbHelper = DatabaseHelper.getInstance(this);
    private Button Back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Details = (TextView) findViewById(R.id.sightingIDView);
        Back = (Button) findViewById(R.id.backBtn);

        Sighting sight = dbHelper.getSighting(getIntent().getIntExtra("sightingID", 0));
        Details.setText("\nLocationType: " + sight.locationType
                + "\nAddress: " + sight.address
                + "\nCity: " + sight.city
                + "\nBorough: " + sight.borough
                + "\nZipcode: " + sight.zip
                + "\nLatitude: " + sight.latitude
                + "\nLongitude: " + sight.longitude);
        System.out.println(sight);

        Back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(DetailPage.this, HomePage.class);
                startActivity(intent);
            }
        });


    }
}
