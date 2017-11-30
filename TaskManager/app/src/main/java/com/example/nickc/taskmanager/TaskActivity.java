package com.example.nickc.taskmanager;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class TaskActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button signout;
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mAuth = FirebaseAuth.getInstance();


        Toast toast = Toast.makeText(getApplicationContext(), "Succesvol ingelogd", Toast.LENGTH_LONG);
        toast.show();

        signout = findViewById(R.id.signOutButton);
        signout.setOnClickListener(this);
    }

    private void updateUI(FirebaseUser currentUser) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {

        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), "Succesvol afgemeld", Toast.LENGTH_LONG);
            toast.show();
            Intent signoutIntent = new Intent(this, homeActivity.class);
            startActivity(signoutIntent);
        }
    }

    @Override
    public void onClick(View v) {
        mAuth.signOut();
        updateUI(null);
    }
}
