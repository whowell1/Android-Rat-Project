package cs2340.ratapplication.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs2340.ratapplication.R;


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
