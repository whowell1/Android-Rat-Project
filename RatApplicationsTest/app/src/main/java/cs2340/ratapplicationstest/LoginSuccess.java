package cs2340.ratapplicationstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;











//

/*
This is the class that handles all of the login


 */

public class LoginSuccess extends AppCompatActivity {
    private Button Logout;
    private UserDatabase userDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        userDB = (UserDatabase) getIntent().getSerializableExtra("userDB");
        System.out.println("Check " + getIntent());

        Logout = (Button) findViewById(R.id.logoutBtn);
        Logout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(LoginSuccess.this, MainActivity.class);
                intent.putExtra("userDB", userDB);
                startActivity(intent);
            }
        });
    }

}
