package com.example.taskmaster1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;

import java.io.File;

public class taskDetails extends AppCompatActivity {

    private static final String TAG = "taskDetails";
    String imageKey ;
    ImageView newImage ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        Intent intent = getIntent();


        TextView newTitle = findViewById(R.id.title);
        TextView newBody = findViewById(R.id.textLorem);
        TextView newState = findViewById(R.id.state);
        newImage = findViewById(R.id.s3Image);

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
                        imageKey =  task.getImage();


                        if(imageKey!=null)
                            downloadImg(imageKey);
//
//                        String sourceImg=intent.getStringExtra("sourceImage");
//
//                        // https://www.codegrepper.com/code-examples/whatever/android+picasso+
//                        if(sourceImg!=null){
//                            Log.i(TAG, "downloaded successfuly"+ sourceImg);
//                            Picasso.get().load(sourceImg).into(newImage);
//                            System.out.println("image from details : +++++++++++++++++++++++++++++ "+ newImage);
//                        }
//
//
                    }
                },
                error -> Log.e(TAG, "Query failure", error)
        );


    }
    private void downloadImg(String imageKey) {
        Amplify.Storage.downloadFile(
                imageKey,
                new File( getApplicationContext().getFilesDir() + "/" + imageKey),
                response -> {

                    Log.i(TAG, "Successfully downloaded: " + response.getFile().getName());

                    //https://iqcode.com/code/other/how-to-get-bitmap-from-file-in-android
                    ImageView image = findViewById(R.id.s3Image);
                    System.out.println("image --------------------- "+ findViewById(R.id.s3Image).toString());
                    Bitmap bitmap = BitmapFactory.decodeFile(getApplicationContext().getFilesDir()+"/"+ response.getFile().getName());
//                    System.out.println(" bitmap 00000000000000000000 "+ bitmap);
                    image.setImageBitmap(bitmap);
//                    System.out.println("  downloaded successfuly : "+bitmap);

                },
                error -> Log.e(TAG,  "Download Failure", error)
        );
    }

}
