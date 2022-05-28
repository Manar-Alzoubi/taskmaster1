package com.example.taskmaster1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;

public class taskDetails extends AppCompatActivity {

    private static final String TAG = "taskDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();

        TextView newTitle = findViewById(R.id.title);
        TextView newBody = findViewById(R.id.textLorem);
        TextView newState = findViewById(R.id.state);

//        Long newId = Long.valueOf(intent.getIntExtra("id",0));

//        Task task = AppDatabase.getInstance(this).taskDao().getTaskById(newId);
//        newTitle.setText(task.getTitle());
//        newBody.setText(task.getDescription());
//        newState.setText(task.getStatus().toString());

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.Task.class, com.amplifyframework.datastore.generated.model.Task.ID.eq(intent.getStringExtra("id"))),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Task task : response.getData()) {
                        Log.i(TAG, "Task title from details  : " + task.getTitle());
                        newTitle.setText(task.getTitle());
                        newBody.setText(task.getDescription());
                        newState.setText(task.getStatus().toString());
                    }
                },
                error -> Log.e(TAG, "Query failure", error)
        );

    }

}
