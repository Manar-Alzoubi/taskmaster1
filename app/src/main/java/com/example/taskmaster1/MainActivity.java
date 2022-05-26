package com.example.taskmaster1;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView username;
    List<Task> tasksList = new ArrayList<>();

        public MainActivity(TextView username, List<Task> taskList) {
        this.username = username;
        this.tasksList = tasksList;
    }
    public MainActivity(){}

    private final View.OnClickListener addButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Intent addTaskActivity = new Intent(getApplicationContext(), addTask.class);

            startActivity(addTaskActivity);
        }
    };

    private final View.OnClickListener allButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {


            Intent allTaskActivity = new Intent(getApplicationContext(), allTasks.class);

            startActivity(allTaskActivity);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.editUserName);

        Button settBtn = findViewById(R.id.button8);
        settBtn.setOnClickListener(view -> {
            navigateToSettings();
        });


//        initialiseData();
        List<Task> taskList2 = AppDatabase.getInstance(getApplicationContext()).taskDao().getAll();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

         CustomRecyclerViewAdapter customRecyclerViewAdapter = new CustomRecyclerViewAdapter(
                 taskList2, position -> {
             Toast.makeText(
                    MainActivity.this,
                    "you clicked :  " + taskList2.get(position).getTitle(), Toast.LENGTH_SHORT).show();

             Intent intent = new Intent(getApplicationContext(), taskDetails.class);
//            intent.putExtra("title",tasksList.get(position).getTitle());
//            intent.putExtra("body",tasksList.get(position).getBody());
//            intent.putExtra("state",tasksList.get(position).getState().toString());
            intent.putExtra("id",taskList2.get(position).getId());
//             System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//             System.out.println(taskList2.get(position).getTitle());
//             System.out.println(taskList2.get(position).getBody());
//             System.out.println(taskList2.get(position).getState());

             startActivity(intent);

         });

        recyclerView.setAdapter(customRecyclerViewAdapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//        Button pray = findViewById(R.id.button4);
//        pray.setOnClickListener(view -> {
//            Intent prayActivity = new Intent(this , taskDetails.class);
//            prayActivity.putExtra("title" , pray.getText().toString());
//            startActivity(prayActivity);
//        });
//
//        Button read = findViewById(R.id.button5);
//        read.setOnClickListener(view -> {
//            Intent readActivity = new Intent(this , taskDetails.class);
//            readActivity.putExtra("title" , read.getText().toString());
//            startActivity(readActivity);
//        });
//
//        Button sleep = findViewById(R.id.button3);
//        sleep.setOnClickListener(view -> {
//            Intent sleepActivity = new Intent(this , taskDetails.class);
//            sleepActivity.putExtra("title" , sleep.getText().toString());
//            startActivity(sleepActivity);
//        });



        Button addTask = findViewById(R.id.button6);
        addTask.setOnClickListener(view -> {
            Intent addTaskActivity = new Intent(this, addTask.class);

            startActivity(addTaskActivity);
        });
        Button allTask = findViewById(R.id.button7);

        allTask.setOnClickListener(view -> {

            Intent allTaskActivity = new Intent(this, allTasks.class);

            startActivity(allTaskActivity);
        });
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
        super.onResume();
        setUserName();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                navigateToSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToSettings() {
        Intent settingsIntent = new Intent(this, settingsActivity.class);
        startActivity(settingsIntent);
    }


    private void initialiseData() {
        tasksList.add(new Task("Task 1", "Do your homeWork", "new"));
        tasksList.add(new Task("Task 2", "Go shopping", "assigned"));
        tasksList.add(new Task("Task 3", "visit friend", "in progress"));
        tasksList.add(new Task("Task 4", "stay with childs", "complete"));
    }


    private void setUserName() {
        // get text out of shared preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // set text on text view User Name

        username.setText(sharedPreferences.getString(settingsActivity.UserName, " ") + "'s Tasks");


    }



}


