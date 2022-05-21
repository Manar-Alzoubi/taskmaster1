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

        String newTitle = intent.getStringExtra("title");
        TextView title = findViewById(R.id.title);
        title.setText(newTitle);

        String newBody = intent.getStringExtra("body");
        TextView body = findViewById(R.id.body);
//        body.setText("hi");

        String newState = intent.getStringExtra("state");
        TextView state = findViewById(R.id.state);
        state.setText(newState);

        System.out.println("*********************"+ newTitle);
        System.out.println("*********************"+ newBody);
        System.out.println("*********************"+ newState);






    }

}
