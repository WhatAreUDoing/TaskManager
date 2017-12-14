package com.example.nickc.taskmanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private String userMail;
    private String userPassword;
    private Button signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        signin = findViewById(R.id.signInButton);
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);

        userMail = email.getText().toString();
        userPassword = password.getText().toString();

        signin.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent i = new Intent(this, TaskActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        userMail = email.getText().toString();
        userPassword = password.getText().toString();
                mAuth.signInWithEmailAndPassword(userMail, userPassword)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast toast = Toast.makeText(getApplicationContext(), "Fout bij inloggen, probeer opnieuw", Toast.LENGTH_LONG);
                                    toast.show();
                                    updateUI(null);
                                }
                            }
                        });
        }

    }
