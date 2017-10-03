package cs2340.ratapplication.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cs2340.ratapplication.models.UserDatabase;
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
    private UserDatabase userDB;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Name = (EditText)findViewById(R.id.etName);
        Password= (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.numAttempts);
        Login = (Button) findViewById(R.id.loginBtn);

        if(getIntent().getSerializableExtra("userDB") != null) {
            userDB = (UserDatabase) getIntent().getSerializableExtra("userDB");
        } else {
            userDB = new UserDatabase();
        }

        Info.setText("");

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

        Signup = (Button) findViewById(R.id.signupBtn);


        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, SignupPage.class);
                intent.putExtra("userDB", userDB);
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

        if (userDB.checkPassword(userName, userPassword)){
            Intent intent = new Intent(LoginPage.this, HomePage.class);
            intent.putExtra("userDB", userDB);
            intent.putExtra("userID", userName);
            startActivity(intent);
        } else {
            Toast.makeText(this,"Improper Login", Toast.LENGTH_LONG).show();
            counter --;
            Info.setText("No attempts left: " + String.valueOf(counter));
            if (counter == 0) {
                Login.setEnabled(false);
            }
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
}
