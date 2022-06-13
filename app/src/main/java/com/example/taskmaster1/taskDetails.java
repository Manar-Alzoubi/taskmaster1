package com.example.taskmaster1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class taskDetails extends AppCompatActivity {

    private static final String TAG = "taskDetails";
    String imageKey ;
    ImageView newImage ;

    private final MediaPlayer mp = new MediaPlayer();



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

                        Button readVoice = findViewById(R.id.readBtn);
                        readVoice.setOnClickListener(view -> {
                            Amplify.Predictions.convertTextToSpeech(
                                    newBody.getText().toString(),
                                    result -> playAudio(result.getAudioData()),
                                    error -> Log.e(TAG, "Conversion failed", error)
                            );
                        });


                        Button translateBtn = findViewById(R.id.translateBtn);
                        translateBtn.setOnClickListener(view -> {
                            Amplify.Predictions.translateText(newBody.getText().toString(),
                                    result -> {
                                        runOnUiThread(() -> {
                                            TextView translatedText = findViewById(R.id.translateBody);
                                            translatedText.setText(result.getTranslatedText());
                                        });
                                        Log.i(TAG, result.getTranslatedText());
                                    },
                                    error -> Log.e(TAG, "Translation failed", error)
                            );
                        });
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
                    ImageView image = findViewById(R.id.s3Image);
                    Bitmap bitmap = BitmapFactory.decodeFile(getApplicationContext().getFilesDir()+"/"+ response.getFile().getName());
                    image.setImageBitmap(bitmap);

                },
                error -> Log.e(TAG,  "Download Failure", error)
        );
    }

    private void playAudio(InputStream audioData) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = audioData.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
        }
    }

}
