package cs2340.ratapplication.controllers;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cs2340.ratapplication.models.DatabaseHelper;
import cs2340.ratapplication.R;

public class LoginPage extends AppCompatActivity {
    //These are the fields that we needed intialized here.
    // username, password, Info is how many attempts we have left
    // Button for Login

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Button Signup;
    private int counter = 3;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //dbHelper.destroyPreviousDB();


        Name = findViewById(R.id.etName);
        Password = findViewById(R.id.etPassword);
        Info = findViewById(R.id.numAttempts);
        Login = findViewById(R.id.loginBtn);

        Info.setText("");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(), Password.getText().toString());
            }
        });

        Signup = findViewById(R.id.signupBtn);


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                startActivity(intent);
            }
        });
    }


    /*
    Method that validates username and password. Right now, it only checks is "user" is equal to the
    * the password which is pass. The if loop does this check and if it is, there a new screen that
    * appears which is an intent
     */

    private void validate(String userName, String userPassword) {

        CheckPasswordAsync cpAsync = new CheckPasswordAsync();
        cpAsync.execute(userName, userPassword);
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

    // CheckPassAysnc connects to the database
    class CheckPasswordAsync extends AsyncTask<String,Void, Boolean> {
        private long userID = 0;
        protected Boolean doInBackground(String... strs) {
            boolean validated = DatabaseHelper.checkPassword(strs[0], strs[1]);
            if(validated) {
                userID = DatabaseHelper.getUserID(strs[0]);
            }
            return validated;
        }

        // when the connection is established succesfully, it should return th
        protected void onPostExecute(Boolean result) {
            if(result) {
                Intent intent = new Intent(LoginPage.this, HomePage.class);
                System.out.println(userID);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }else {
                Toast.makeText(LoginPage.this, "Improper Login", Toast.LENGTH_LONG).show();
                counter--;
                Info.setText("No attempts left: " + String.valueOf(counter));
                if (counter == 0) {
                    Login.setEnabled(false);
                }
            }
        }
    }
}
