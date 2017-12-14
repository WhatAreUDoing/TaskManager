package com.example.nickc.taskmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class createActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "create";
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private EditText password2;
    private String userMail;
    private String userPassword;
    private String userPassword2;
    private Button create;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        mAuth = FirebaseAuth.getInstance();

        create = findViewById(R.id.createUserButton);
        email = findViewById(R.id.emailCreateEditTextView);
        password = findViewById(R.id.passwordEditText);
        password2 = findViewById(R.id.password2EditText);

        userMail = email.getText().toString();
        userPassword = password.getText().toString();
        userPassword2 = password2.getText().toString();

        create.setOnClickListener(this);
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
        Log.d(TAG, "onClick: gaat erin");
        userMail = email.getText().toString();
        userPassword = password.getText().toString();
        userPassword2 = password2.getText().toString();
        if(userPassword.equals(userPassword2)) {
            Log.d(TAG, userMail +  " onClick: controlleerd passwoord " + userPassword);
            mAuth.createUserWithEmailAndPassword(userMail, userPassword)
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
}
