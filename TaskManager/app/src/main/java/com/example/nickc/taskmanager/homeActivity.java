package com.example.nickc.taskmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class homeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signin;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        signin = findViewById(R.id.signInHomeButton);
        create = findViewById(R.id.createHomeButton);

        signin.setOnClickListener(this);
        create.setOnClickListener(this);
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
