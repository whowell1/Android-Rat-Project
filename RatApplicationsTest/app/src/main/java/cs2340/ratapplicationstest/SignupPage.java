package cs2340.ratapplicationstest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private UserDatabase userDB;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Name = (EditText)findViewById(R.id.etName);
        Password= (EditText)findViewById(R.id.etPassword);
        Signup = (Button) findViewById(R.id.signupBtn);

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validate(Name.getText().toString(),Password.getText().toString());
            }
        });

        System.out.println("Check: " + getIntent());
        userDB = (UserDatabase) getIntent().getSerializableExtra("userDB");
        System.out.println("Check: " + getIntent().getSerializableExtra("userDB"));

    }


    /*
    Method that validates username and password. Right now, it only checks is "user" is equal to the
    * the password which is pass. The if loop does this check and if it is, there a new screen that
    * appears which is an intent
     */

    private void validate(String userName, String userPassword) {
        if ((userName.contains("@") && userPassword.length() >= 7)){
            userDB.addUser(userName,userPassword);
            Intent intent = new Intent(SignupPage.this, LoginSuccess.class);
            intent.putExtra("userDB", userDB);
            startActivity(intent);
        } else  if(!userName.contains("@")) {
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
}
