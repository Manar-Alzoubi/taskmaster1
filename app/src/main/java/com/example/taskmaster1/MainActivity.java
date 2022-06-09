package com.example.taskmaster1;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.options.AuthSignOutOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.Team;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView username;
    private TextView teamName;

    List<Task> tasksListDB = new ArrayList<>();
    private Handler handler;
    private Handler handler1;
    String newTeamName;

    private InterstitialAd mInterstitialAd;
    private RewardedAd mRewardedAd;
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

//        configureAmplify();


        username = findViewById(R.id.editUserName);

         teamName = findViewById(R.id.editTeam);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
         newTeamName =  sharedPreferences.getString(settingsActivity.TeamName,"");

        getTasksAssignedToTeams(newTeamName);

        Button settBtn = findViewById(R.id.button8);
        settBtn.setOnClickListener(view -> {
            navigateToSettings();
        });

        handler = new Handler(Looper.getMainLooper(), msg -> {
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            CustomRecyclerViewAdapter customRecyclerViewAdapter = new CustomRecyclerViewAdapter(
                    tasksListDB, position -> {
                Toast.makeText(
                        MainActivity.this,
                        "you clicked :  " + tasksListDB.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), taskDetails.class);
                intent.putExtra("id", tasksListDB.get(position).getId());
                System.out.println("tasks list DB : "+ tasksListDB);
                startActivity(intent);
            }) {
                @Override
                public void onTaskItemClicked(int position) {

                }
            };

            recyclerView.setAdapter(customRecyclerViewAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            return true;

        });
        handler1= new Handler(Looper.getMainLooper(),msg ->{
            String newUser= msg.getData().getString("newUserName");
            username.setText(newUser);
            return true;
        });

////        Button pray = findViewById(R.id.button4);
////        pray.setOnClickListener(view -> {
////            Intent prayActivity = new Intent(this , taskDetails.class);
////            prayActivity.putExtra("title" , pray.getText().toString());
////            startActivity(prayActivity);
////        });
////
////        Button read = findViewById(R.id.button5);
////        read.setOnClickListener(view -> {
////            Intent readActivity = new Intent(this , taskDetails.class);
////            readActivity.putExtra("title" , read.getText().toString());
////            startActivity(readActivity);
////        });
////
////        Button sleep = findViewById(R.id.button3);
////        sleep.setOnClickListener(view -> {
////            Intent sleepActivity = new Intent(this , taskDetails.class);
////            sleepActivity.putExtra("title" , sleep.getText().toString());
////            startActivity(sleepActivity);
////        });




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

        Button InterstitialBtn = findViewById(R.id.interstitialBtn);

        InterstitialBtn.setOnClickListener(view -> {
            loadInterstitialAd();
            if (mInterstitialAd != null) {
                mInterstitialAd.show(MainActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }

                });

        Button RewardedBtn = findViewById(R.id.rewardedBtn);

        RewardedBtn.setOnClickListener(view -> {
            loadRewardedAd();
            if (mRewardedAd != null) {
                Activity activityContext = MainActivity.this;
                mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                    @Override
                    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                        // Handle the reward.
                        Log.d(TAG, "The user earned the reward.");
                        int rewardAmount = rewardItem.getAmount();
                        String rewardType = rewardItem.getType();
                        Toast.makeText(activityContext, "the amount => " + rewardAmount, Toast.LENGTH_SHORT).show();
                        Toast.makeText(activityContext, "the type => " + rewardType, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Log.d(TAG, "The rewarded ad wasn't ready yet.");
            }
        });


        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
            case R.id.action_Log_Out:
                logOut();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                return true;
                    default:
                return super.onOptionsItemSelected(item);

    }
    }

    private void navigateToSettings() {
        Intent settingsIntent = new Intent(this, settingsActivity.class);
        startActivity(settingsIntent);
    }

//    private void initialiseData() {
//        tasksList.add(new Task("Task 1", "Do your homeWork", "new"));
//        tasksList.add(new Task("Task 2", "Go shopping", "assigned"));
//        tasksList.add(new Task("Task 3", "visit friend", "in progress"));
//        tasksList.add(new Task("Task 4", "stay with childs", "complete"));
//    }

private void setUserName() {
        // get text out of shared preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        // set text on text view User Name    editTeam
//        username.setText(sharedPreferences.getString(settingsActivity.UserName, " ") + "'s Tasks" );
//          username.setText(Amplify.Auth.getCurrentUser().getUsername());
          teamName.setText("Tasks For : "+ sharedPreferences.getString(settingsActivity.TeamName," "));
          Amplify.Auth.fetchUserAttributes(
                  attributes ->{
                      Log.i(TAG,"user attributes-->  "+attributes.get(2).getValue());
                      Bundle bundle =new Bundle();
                      bundle.putString("newUserName",attributes.get(2).getValue());
                      Message message = new Message();
                      message.setData(bundle);
                      handler1.sendMessage(message);
                  },
                  error -> Log.e(TAG, "can't find username",error)
          );
        }



    private void getTasksAssignedToTeams(String newTeamName) {
        Amplify.API.query(
                ModelQuery.list(Team.class),
                teams -> {
                    for (Team team : teams.getData()) {
                        if (team.getName().equals(newTeamName)) {
                            Amplify.API.query(
                                    ModelQuery.list(Task.class, Task.TEAM_TASKS_LIST_ID.eq(team.getId())),
                                    success -> {
                                        tasksListDB = new ArrayList<>();
                                        if (success.hasData()) {
                                            for (Task task : success.getData()) {
                                                tasksListDB.add(task);
                                            }
                                        }
                                        Bundle bundle = new Bundle();
                                        bundle.putString("Team", success.toString());

                                        Message message = new Message();
                                        message.setData(bundle);

                                        handler.sendMessage(message);

                                    },
                                    error -> Log.e(TAG, error.toString(), error)
                            );
                        }
                    }


                },
                error -> Log.e(TAG, error.toString(), error)
        );
    }
    private void logOut() {
        Amplify.Auth.signOut(
                AuthSignOutOptions.builder().globalSignOut(true).build(),
                () -> Log.i("AuthQuickstart", "Signed out globally"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
    }

    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");

                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d(TAG, "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d(TAG, "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d(TAG, "The ad was shown.");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }
    private void loadRewardedAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");

                        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad was shown.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.d(TAG, "Ad failed to show.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad was dismissed.");
                                mRewardedAd = null;
                            }
                        });
                    }
                });
    }
}

