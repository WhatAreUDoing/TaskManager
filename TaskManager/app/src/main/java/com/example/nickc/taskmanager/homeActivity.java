package com.example.nickc.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button signin;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();

        updateUI(mAuth.getCurrentUser());

        signin = findViewById(R.id.signInHomeButton);
        create = findViewById(R.id.createHomeButton);

        signin.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.signInHomeButton:
                Intent loginIntent = new Intent(this, loginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.createHomeButton:
                Intent createIntent = new Intent(this, createActivity.class);
                startActivity(createIntent);
                break;
        }
    }
}
