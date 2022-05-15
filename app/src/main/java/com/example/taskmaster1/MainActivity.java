package com.example.taskmaster1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.editUserName);

        Button settBtn = findViewById(R.id.button8);
        settBtn.setOnClickListener(view -> {
            navigateToSettings();
        });

        Button addTask = findViewById(R.id.button6);

        addTask.setOnClickListener(view -> {
            Intent addTaskActivity = new Intent(this , addTask.class);
            startActivity(addTaskActivity);
        });

        Button allTask = findViewById(R.id.button7);

        allTask.setOnClickListener(view -> {
            Intent allTaskActivity = new Intent(this , allTasks.class);
            startActivity(allTaskActivity);
        });


        Button pray = findViewById(R.id.button4);
        pray.setOnClickListener(view -> {
            Intent prayActivity = new Intent(this , taskDetails.class);
            prayActivity.putExtra("title" , pray.getText().toString());
            startActivity(prayActivity);
        });

        Button read = findViewById(R.id.button5);
        read.setOnClickListener(view -> {
            Intent readActivity = new Intent(this , taskDetails.class);
            readActivity.putExtra("title" , read.getText().toString());
            startActivity(readActivity);
        });

        Button sleep = findViewById(R.id.button3);
        sleep.setOnClickListener(view -> {
            Intent sleepActivity = new Intent(this , taskDetails.class);
            sleepActivity.putExtra("title" , sleep.getText().toString());
            startActivity(sleepActivity);
        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_settings:
//                navigateToSettings();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    private void navigateToSettings() {
        Intent settingsIntent = new Intent(this, settingsActivity.class);
        startActivity(settingsIntent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart: called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart: called");
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "onResume: called - The App is VISIBLE");

        setUserName();
        super.onResume();

    }

    @Override
    protected void onPause() {
        Log.i(TAG, "onPause: called");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "onStop: called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG, "onDestroy: called");
        super.onDestroy();
    }


    private void setUserName() {
        // get text out of shared preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // set text on text view User Name
        username.setText(sharedPreferences.getString(settingsActivity.UserName, " ") +"'s Tasks");

    }

}