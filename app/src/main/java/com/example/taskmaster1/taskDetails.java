package com.example.taskmaster1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class taskDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();


        TextView newTitle = findViewById(R.id.title);
        TextView newBody = findViewById(R.id.textLorem);
        TextView newState = findViewById(R.id.state);


        Long newId = Long.valueOf(intent.getIntExtra("id",0));

        Task task = AppDatabase.getInstance(this).taskDao().getTaskById(newId);
        newTitle.setText(task.getTitle());
        newBody.setText(task.getBody());
        newState.setText(task.getState().toString());

    }

}
