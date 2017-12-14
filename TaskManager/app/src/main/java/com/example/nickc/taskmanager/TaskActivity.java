package com.example.nickc.taskmanager;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskActivity extends AppCompatActivity {

    private static final String TAG = "TaskActivity";
    // variables
    private ListView todo;
    private ArrayList<HashMap<String, String>> data =
            new ArrayList<HashMap<String, String>>();

    // Firebase
    private FirebaseAuth mAuth;
    private String currentUser;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();


        Toast toast = Toast.makeText(getApplicationContext(), "Succesvol ingelogd", Toast.LENGTH_LONG);
        toast.show();

        todo = findViewById(R.id.TodoListview);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                data.clear();
                for (DataSnapshot users: snapshot.child("user").getChildren()){
                    Log.d("eerste for", users.getKey());
                    Log.d("currentuser", currentUser);
                    if(currentUser == users.getKey()) {
                        Log.d("user in de if", currentUser);
                        for(DataSnapshot user: snapshot.child("list").child(currentUser).getChildren()) {
                            Log.d("user", user.getKey());
                            Log.d("user", user.getValue().toString());
                        }
                    }
                }
                for (DataSnapshot postSnapshot: snapshot.child("list").getChildren()) {
                    Log.d("TodoActivity", postSnapshot.toString());
                    Log.d("key", postSnapshot.getKey());
                    Log.d("value", postSnapshot.getValue().toString());
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("key", postSnapshot.getKey());
                    data.add(map);
                }
                updateData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.", databaseError.toException());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_out, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.SignOut:
                mAuth.signOut();
                updateUI(null);
                return true;
            case R.id.NewItem:
                return true;
        }
        return false;
    }

    private void updateData() {
        int resource = R.layout.list_item;
        String[] from = {"key"};
        int[] to = {R.id.ItemTextView};
        SimpleAdapter adapter = new SimpleAdapter(this, data, resource, from, to);
        todo.setAdapter(adapter);
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
}
