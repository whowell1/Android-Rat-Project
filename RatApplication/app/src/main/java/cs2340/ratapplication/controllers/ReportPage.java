package cs2340.ratapplication.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cs2340.ratapplication.R;
import cs2340.ratapplication.models.UserDatabase;

public class ReportPage extends AppCompatActivity {
    private EditText LocationType;
    private EditText ZipCode;
    private EditText Address;
    private EditText City;
    private EditText Borough;
    private Button Submit;
    private Button Cancel;


    private UserDatabase userDB;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        userDB = (UserDatabase) getIntent().getSerializableExtra("userDB");
        username = getIntent().getStringExtra("userID");

        LocationType = (EditText)findViewById(R.id.LocationType);
        Address = (EditText)findViewById(R.id.Address);
        City = (EditText)findViewById(R.id.City);
        Borough = (EditText)findViewById(R.id.Borough);
        ZipCode = (EditText)findViewById(R.id.ZipCode);
        Submit = (Button)findViewById(R.id.SubmitBtn);
        Cancel = (Button)findViewById(R.id.CancelBtn);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(LocationType.getText().toString(),Address.getText().toString(),
                        City.getText().toString(), Borough.getText().toString(),
                        ZipCode.getText().toString());
            }
        });
        Cancel.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view) {
                Intent intent = new Intent(ReportPage.this, HomePage.class);
                intent.putExtra("userDB", userDB);
                intent.putExtra("userID", username);
                startActivity(intent);
            }
        });


    }

    /*
    Validates to google apis if its a real address and if it is, adds to database
    //TODO
    // Did not start on this yet

     */
    private void validate(String LocationType, String Address, String City,
                           String Borough,String ZipCode) {


    }
}