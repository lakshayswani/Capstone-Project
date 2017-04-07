package com.lakshayswani.virtuastock.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.lakshayswani.virtuastock.R;

/**
 * The type Sign up.
 */
public class SignUp extends AppCompatActivity {

    private EditText signup_email;

    private EditText signup_password;

    private EditText signup_name;

    private TextView link_login;

    private Button btn_signup;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    private final static String TAG = "SIGNUP_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_name = (EditText) findViewById(R.id.signup_name);
        link_login = (TextView) findViewById(R.id.link_login);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        mAuth = FirebaseAuth.getInstance();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validate()) {
                    return;
                }
                ProgressDialog progressDialog = new ProgressDialog(SignUp.this,
                        R.style.Theme_AppCompat_DayNight_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating Account...");
                progressDialog.show();

                signUpUser(progressDialog);
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(signup_name.getText().toString()).build();
                    user.updateProfile(profileUpdates);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.t_welcome) + user.getDisplayName(), Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(in);
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(in);
            }
        });
    }

    /**
     * Validate boolean.
     *
     * @return the boolean
     */
    public boolean validate() {
        boolean valid = true;

        String name = signup_password.getText().toString();
        String email = signup_email.getText().toString();
        String password = signup_name.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            signup_name.setError(getResources().getString(R.string.t_valid_name));
            valid = false;
        } else {
            signup_name.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signup_email.setError(getResources().getString(R.string.t_valid_email));
            valid = false;
        } else {
            signup_email.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            signup_password.setError(getResources().getString(R.string.t_valid_password));
            valid = false;
        } else {
            signup_password.setError(null);
        }

        return valid;
    }

    private void signUpUser(final ProgressDialog progressDialog) {
        mAuth.createUserWithEmailAndPassword(signup_email.getText().toString(), signup_password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.t_signup_error),
                            Toast.LENGTH_SHORT).show();
                }
                progressDialog.hide();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
