package cs2340.ratapplication.controllers;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import cs2340.ratapplication.R;
import cs2340.ratapplication.models.DatabaseHelper;

public class DetailPage extends AppCompatActivity {
    private DatabaseHelper helper = DatabaseHelper.getInstance(this);
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textView =(TextView) findViewById(R.id.textView);
        int numClicked = getIntent().getIntExtra("sightingID", 0);
        textView.setText("" + numClicked);
    }


}
