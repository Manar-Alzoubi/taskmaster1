package com.example.taskmaster1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.Task;


public class addTask extends AppCompatActivity {
    private static final String TAG = "addTask";

    private EditText mTitle;
    private EditText mDesc;
    private Spinner spinner;
    private Spinner teamSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Hard code to create 3 Teams just the first time run
        // To initialize the teams then no need for them
        // if they still not commented, the program will initialize thee 3 teams each time run


//            Team Team1 = Team.builder().name("Team 1").build();
//            Amplify.API.mutate(
//                    ModelMutation.create(Team1),
//                    success -> Log.i(TAG, "Saved Team1 : " + success.getData().toString()),
//                    error -> Log.e(TAG, "Could not save Team1 to API", error)
//            );
//            Team Team2 = Team.builder().name("Team 2").build();
//            Amplify.API.mutate(
//                    ModelMutation.create(Team2),
//                    success -> Log.i(TAG, "Saved Team2: " + success.getData().toString()),
//                    error -> Log.e(TAG, "Could not save Team2 to API", error)
//            );
//            Team Team3 = Team.builder().name("Team 3").build();
//            Amplify.API.mutate(
//                    ModelMutation.create(Team3),
//                    success -> Log.i(TAG, "Saved Team3 : " + success.getData().toString()),
//                    error -> Log.e(TAG, "Could not save Team3 to API", error)
//            );

        final String[] teams_array = new String[]{"Team 1", "Team 2", "Team 3"};

        teamSelector = findViewById(R.id.teamSpinner);
        ArrayAdapter<String> teamSpinnerAdapter = new ArrayAdapter<>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, teams_array

        );
        teamSelector.setAdapter(teamSpinnerAdapter);

        Button addTask = findViewById(R.id.button);
        addTask.setOnClickListener(view -> {

             mTitle = findViewById(R.id.newTaskTitle);
             mDesc = findViewById(R.id.doSomthing);
             spinner = findViewById(R.id.spinner);
             teamSelector= findViewById(R.id.teamSpinner);

            String newTitle = mTitle.getText().toString();
            String newDescription = mDesc.getText().toString();
            String newState = spinner.getSelectedItem().toString();
            String newTeam = teamSelector.getSelectedItem().toString();

//            Task task = new Task(mTitle.getText().toString(), mDesc.getText().toString(), spinner.getSelectedItem().toString());
//            System.out.println("task =================================");
//            System.out.println(task);
//            AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);

            Amplify.API.query(
                    ModelQuery.list(Team.class, Team.NAME.eq(newTeam)),
                    success->{
                        for (Team team :
                                success.getData()) {
                            Task newTask = Task.builder()
                                    .title(newTitle)
                                    .description(newDescription)
                                    .status(newState)
                                    .teamTasksListId(team.getId())
                                    .build();
                            
                            // Data store save
                            Amplify.DataStore.save(newTask,
                                    saved -> Log.i(TAG, "Saved Task: " + newTask.getTitle()),
                                    notSaved -> Log.e(TAG, "Could not save Task to DataStore", notSaved)
                            );

                            // API save to backend
                            Amplify.API.mutate(
                                    ModelMutation.create(newTask),
                                    taskSaved -> {
                                        Log.i(TAG, "Saved Task: " + taskSaved.getData().getTeamTasksListId());
                                    },
                                    error -> {
                                        Log.e(TAG, "Could not save Task to API", error);
                                    }
                            );
                        }
                    },
                    Failure ->{
                        Log.i(TAG, "onCreate: no team assigned");
                    }
            );
            Toast.makeText(this, "task added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }
}



