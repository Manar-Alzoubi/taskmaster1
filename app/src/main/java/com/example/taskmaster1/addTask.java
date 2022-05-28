package com.example.taskmaster1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;


public class addTask extends AppCompatActivity {

    private static final String TAG = "addTask";
    private EditText mTitle;
    private EditText mDesc;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Button addTask = findViewById(R.id.button);
        addTask.setOnClickListener(v -> {
            mTitle = findViewById(R.id.newTaskTitle);
            mDesc = findViewById(R.id.doSomthing);
            spinner = findViewById(R.id.spinner);

            String newTitle = mTitle.getText().toString();
            String newDescriptipn = mDesc.getText().toString();
            String newState = spinner.getSelectedItem().toString();


//            Task task = new Task(mTitle.getText().toString(), mDesc.getText().toString(), spinner.getSelectedItem().toString());
//            System.out.println("task =================================");
//            System.out.println(task);
//            AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);


            Task newTask = Task.builder()
                    .title(newTitle)
                    .description(newDescriptipn)
                    .status(newState)
                    .build();

            // Data store save
            Amplify.DataStore.save(newTask,
                    success -> Log.i(TAG, "Saved Task: " + success.item().getTitle()),
                    error -> Log.e(TAG, "Could not save Task to DataStore", error)
            );

            // API save to backend
            Amplify.API.mutate(
                    ModelMutation.create(newTask),
                    success -> Log.i(TAG, "Saved Task: " + success.getData().getTitle()),
                    error -> Log.e(TAG, "Could not save Task to API", error)
            );
            // Datastore and API sync
            Amplify.DataStore.observe(Task.class,
                    started -> {
                        Log.i(TAG, "Observation began.");
                    },
                    change -> Log.i(TAG, change.item().toString()),
                    failure -> Log.e(TAG, "Observation failed.", failure),
                    () -> Log.i(TAG, "Observation complete.")

            );

            Toast.makeText(this, "task added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));


        });

    }
}
