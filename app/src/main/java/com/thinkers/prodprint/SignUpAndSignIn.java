package com.thinkers.prodprint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class SignUpAndSignIn extends AppCompatActivity {

    private ViewFlipper viewFlipper;
    private float lastX;
    private LinearLayout linearLayoutName;
    private Button buttonSignUp, buttonLogIn;
    private Button btnSignIn, btnSignUp;
    private EditText inputName, inputEmail, inputPassword, inputPhone;
    public static final String USER_ACCOUNT_DATA = "User Account Data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_and_sign_in);
        viewFlipper = (ViewFlipper) findViewById(R.id.view_flipper);
        linearLayoutName = (LinearLayout) findViewById(R.id.lt_name);
        buttonSignUp = (Button) findViewById(R.id.bt_signup);
        buttonLogIn = (Button) findViewById(R.id.bt_login);
        inputName = (EditText) findViewById(R.id.et_name);
        inputEmail = (EditText) findViewById(R.id.et_email);
        inputPassword = (EditText) findViewById(R.id.et_password);
        inputPhone = (EditText) findViewById(R.id.et_phone);
        btnSignIn = (Button) findViewById(R.id.bt_sign_in);
        btnSignUp = (Button) findViewById(R.id.bt_create);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapScreenRightToLeft();
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swapScreenLeftToRight();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (logInValidate()) {
                    if(checkLogin(email, password)){
                        Intent intent = new Intent(SignUpAndSignIn.this, LoginWelcomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(),
                                getResources().getString(R.string.loginfail), Toast.LENGTH_LONG)
                                .show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            getResources().getString(R.string.invalidemail), Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    public void signUp() {
        if (!signUpValidate()) {
            onSignUpFailed();
            return;
        }

        btnSignUp.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignUpAndSignIn.this,R.style.AppTheme_PopupOverlay);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String phone = inputPhone.getText().toString();

        SharedPreferences.Editor user = getSharedPreferences(USER_ACCOUNT_DATA, MODE_PRIVATE).edit();
        user.putString("name", name);
        user.putString("email", email);
        user.putString("password", password);
        user.putString("phone", phone);
        user.commit();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        onSignUpSuccess();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignUpSuccess() {
        btnSignUp.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(SignUpAndSignIn.this, LoginWelcomeActivity.class);
        startActivity(intent);
    }

    public void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Sign Up failed", Toast.LENGTH_LONG).show();

        btnSignUp.setEnabled(true);
    }

    public boolean logInValidate() {
        boolean valid = true;

        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError(getResources().getString(R.string.invalidemail));
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            inputPassword.setError("Invalid password!");
            valid = false;
        } else {
            inputPassword.setError(null);
        }

        return valid;
    }

    public boolean signUpValidate() {
        boolean valid = true;

        String name = inputName.getText().toString();
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        String phone = inputPhone.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            inputName.setError("at least 3 characters");
            valid = false;
        } else {
            inputName.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            inputEmail.setError(getResources().getString(R.string.invalidemail));
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            inputEmail.setError("at least 4 alphanumeric characters");
            valid = false;
        } else {
            inputEmail.setError(null);
        }

        if (phone.isEmpty() || phone.length() < 10 || !Patterns.PHONE.matcher(phone).matches()) {
            inputPhone.setError("Invalid phone number!");
            valid = false;
        } else {
            inputPhone.setError(null);
        }

        return valid;
    }


    private boolean checkLogin(final String email, final String password){
        SharedPreferences retrieveUserData = getSharedPreferences(USER_ACCOUNT_DATA, MODE_PRIVATE);

        String userEmail = retrieveUserData.getString("email", null);
        String userPassword = retrieveUserData.getString("password", null);

        if (email.equals(userEmail) && password.equals(userPassword)){
            return true;
        } else {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = touchEvent.getX();
                break;
            case MotionEvent.ACTION_UP:
                float currentX = touchEvent.getX();

                if (lastX < currentX) {
                    swapScreenLeftToRight();
                }

                if (lastX > currentX) {
                    swapScreenRightToLeft();
                }
                break;
        }
        return false;
    }

    public void swapScreenLeftToRight() {
        if (viewFlipper.getDisplayedChild() == 0)
            return;

        viewFlipper.setInAnimation(this, R.anim.slide_in_from_left);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_to_right);

        viewFlipper.showNext();
        linearLayoutName.setVisibility(View.INVISIBLE);
        buttonSignUp.setBackgroundColor(Color.WHITE);
        buttonLogIn.setBackgroundColor(Color.parseColor("#FF3343A2"));
    }

    public void swapScreenRightToLeft() {
        if (viewFlipper.getDisplayedChild() == 1)
            return;

        viewFlipper.setInAnimation(this, R.anim.slide_in_from_right);
        viewFlipper.setOutAnimation(this, R.anim.slide_out_to_left);

        viewFlipper.showPrevious();
        linearLayoutName.setVisibility(View.VISIBLE);
        buttonSignUp.setBackgroundColor(Color.parseColor("#FF3343A2"));
        buttonLogIn.setBackgroundColor(Color.WHITE);
        inputName.requestFocus();
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
        if (id == R.id.diclaimer) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
