package cs2340.ratapplication.controllers;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import cs2340.ratapplication.models.*;

import org.w3c.dom.Text;

import cs2340.ratapplication.R;
import cs2340.ratapplication.models.DatabaseHelper;

public class DetailPage extends AppCompatActivity {
    private TextView Details;
    private Button Back;
    private long userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        userID = getIntent().getLongExtra("userID",0);

        Details = (TextView) findViewById(R.id.sightingIDView);
        Back = (Button) findViewById(R.id.backBtn);

        Back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(DetailPage.this, HomePage.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        DisplayDetailAsync ddAsync = new DisplayDetailAsync();
        ddAsync.execute();

    }

    protected class DisplayDetailAsync extends AsyncTask<String,Void, Sighting> {
        protected Sighting doInBackground(String... strs) {
            return DatabaseHelper.getSighting(getIntent().getIntExtra("sightingID", 0));
        }

        protected void onPostExecute(Sighting sight) {
            Details.setText("\nLocationType: " + sight.locationType
                    + "\nAddress: " + sight.address
                    + "\nCity: " + sight.city
                    + "\nBorough: " + sight.borough
                    + "\nZipcode: " + sight.zip
                    + "\nLatitude: " + sight.latitude
                    + "\nLongitude: " + sight.longitude);
        }
    }
}
