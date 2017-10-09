package cs2340.ratapplication.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cs2340.ratapplication.R;
import cs2340.ratapplication.models.DatabaseHelper;

public class DetailPage extends AppCompatActivity {
    private DatabaseHelper helper = DatabaseHelper.getInstance(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
