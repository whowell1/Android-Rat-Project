package cs2340.ratapplication.controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import org.json.JSONObject;

import cs2340.ratapplication.R;
import cs2340.ratapplication.models.DatabaseHelper;

/**
 * Created by thoma on 9/29/2017.
 */

public class SignupPage extends AppCompatActivity {

    //These are the fields that we needed intialized here.
    // username, password, Info is how many attempts we have left
    // Button for Login

    private EditText Name;
    private EditText Password;
    private Button Signup;
    private Button Back;
    private Spinner AdminSpinner;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Name = (EditText)findViewById(R.id.etName);
        Password= (EditText)findViewById(R.id.etPassword);
        Signup = (Button) findViewById(R.id.signupBtn);
        Back = (Button) findViewById(R.id.backBtn);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupPage.this, LoginPage.class);
                startActivity(intent);
            }
        });


        AdminSpinner = (Spinner) findViewById(R.id.adminSpinner);
        String[] adminArr = {"User", "Admin"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, adminArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        AdminSpinner.setAdapter(adapter);

    }


    /*
    Method that validates username and password. Right now, it only checks is "user" is equal to the
    * the password which is pass. The if loop does this check and if it is, there a new screen that
    * appears which is an intent
     */

    private void validate(String username, String userPassword) {
        if ((username.contains("@") && userPassword.length() >= 7)){
            addUserAsync async = new addUserAsync();
            async.execute(username, userPassword, Boolean.toString(AdminSpinner.getSelectedItem().equals("Admin")));

        } else  if(!username.contains("@")) {
            Toast.makeText(this,"Must enter a valid email", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"Password must be longer than 7", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    // connects to the database
    protected class addUserAsync extends AsyncTask<String,Void, Long> {
        private String username;
        protected Long doInBackground(String... strs) {
            username = strs[0];
            System.out.println();
            return DatabaseHelper.addUser(strs[0], strs[1], Boolean.parseBoolean(strs[2]));

        }

        // checks if there is valid entry on table
        protected void onPostExecute(Long result) {
            if(result == -1) {
                Toast.makeText(SignupPage.this,"User already exists", Toast.LENGTH_LONG).show();

            }else {
                System.out.println("Result: " + result);
                Intent intent = new Intent(SignupPage.this, HomePage.class);
                intent.putExtra("userID", result);
                startActivity(intent);
            }
        }
    }
}