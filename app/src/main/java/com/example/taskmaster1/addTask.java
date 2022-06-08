package com.example.taskmaster1;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.aware.DiscoverySession;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.Task;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import java.util.logging.Handler;


public class addTask extends AppCompatActivity {
    private static final String TAG = "addTask";
    public static final int REQUEST_CODE = 123;

    private EditText mTitle;
    private EditText mDesc;
    private Spinner spinner;
    private Spinner teamSelector;
    String imageKey = null;
    private Button uploadImg;
    private Handler handler;
    File file;
    static int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
         uploadImg = findViewById(R.id.uploadBtn);

        uploadImg.setOnClickListener(view2 -> imageUpload());
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
                                    .image(imageKey)
                                    .build();
//                            System.out.println("image ------------------------------------------    "+imageKey);
//                            System.out.println("**************************************************************");
//                            System.out.println("new task : "+ newTask);

                            // Data store save
                            Amplify.DataStore.save(newTask,
                                    saved -> Log.i(TAG, "Saved Task: " + newTask.getTitle()),
                                    notSaved -> Log.e(TAG, "Could not save Task to DataStore", notSaved)
                            );

                            // API save to backend
                            Amplify.API.mutate(
                                    ModelMutation.create(newTask),
                                    taskSaved -> {
                                        Log.i(TAG, "Saved Task: " + taskSaved.getData());
                                    },
                                    error ->  Log.e(TAG, "Could not save Task to API", error)
                            );
                        }
                    },
                    Failure ->{
                        Log.i(TAG, " no team assigned");
                    }
            );
            Toast.makeText(this, "task added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

    }

    public void imageUpload(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
           }

        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode != Activity.RESULT_OK) {
                // Handle error
                Log.e(TAG, "onActivityResult: Error getting image from device");
                return;
            }

            switch(requestCode) {
                case REQUEST_CODE:
                    // Get photo picker response for single select.
                    Uri currentUri = data.getData();
//                    System.out.println("++++++++++++++++++++++++++++++++++++++  count = "+count);
//                    count++;
//                    EditText imgName ;
                   String imgName= findViewById(R.id.newTaskTitle).toString();
//                    System.out.println(" image imageName  "+ imgName.toString());
                    // Do stuff with the photo/video URI.
                    Log.i(TAG, "the uri is => " + currentUri);

                    try {
                        Bitmap bitmap = getBitmapFromUri(currentUri);
//                        String imageName= imgName+count;
//                        System.out.println("image name ............................................"+ imageName.toString());
//                        File file = new File(getApplicationContext().getFilesDir(), imageName+".jpg");
                        EditText imgTitle = findViewById(R.id.newTaskTitle);
                        System.out.println("img title : *****************   "+ imgTitle);
                        String imageName = imgTitle.getText().toString();
                        System.out.println("img imageName : *****************   "+ imageName);
                        file = new File(getApplicationContext().getFilesDir(), imageName+".jpg");
                        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                        os.close();

                        // upload to s3
                        // uploads the file
                        Amplify.Storage.uploadFile(
                                imageName,
                                file,
                                result -> {
                                    Log.i(TAG, "Successfully uploaded: " + result.getKey()) ;
                                    imageKey = result.getKey();
                                },
                                storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
            }

        }
//    /*
//       https://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
//        */
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }
}



