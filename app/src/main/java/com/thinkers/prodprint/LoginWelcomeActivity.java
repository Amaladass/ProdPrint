package com.thinkers.prodprint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginWelcomeActivity extends AppCompatActivity {

    private Button button;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(LoginWelcomeActivity.this, SignUpAndSignIn.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(in);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_welcome);

        button = (Button) findViewById(R.id.buttonpeep);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });

        SharedPreferences retrieveUserData = getSharedPreferences(SignUpAndSignIn.USER_ACCOUNT_DATA, MODE_PRIVATE);

        String name = retrieveUserData.getString("name", null);
        String email = retrieveUserData.getString("email", null);
        String phone = retrieveUserData.getString("phone", null);

        TextView printName = (TextView) findViewById(R.id.tv_name);
        TextView printEmail = (TextView) findViewById(R.id.tv_email);
        TextView printPhone = (TextView) findViewById(R.id.tv_phone);

        printName.setText(name);
        printEmail.setText(email);
        printPhone.setText(phone);
    }

}
