package com.example.taskmaster1;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class addTask extends AppCompatActivity {

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


            Task task = new Task(mTitle.getText().toString(), mDesc.getText().toString(), spinner.getSelectedItem().toString());
//            System.out.println("task =================================");
//            System.out.println(task);

            AppDatabase.getInstance(getApplicationContext()).taskDao().insertTask(task);
            Toast.makeText(this, "task added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));


        });

    }
}
