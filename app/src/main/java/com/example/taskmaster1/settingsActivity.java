package com.example.taskmaster1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.List;

public class settingsActivity extends AppCompatActivity {

    private static final String TAG = settingsActivity.class.getSimpleName();
    public static final String UserName = "username";
    private EditText mUserNameEditText;
    public static final String TeamName = "teamName";
    Spinner teamSelector;
    private List<String> teamsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnsubmit = findViewById(R.id.btnsubmit);
        mUserNameEditText = findViewById(R.id.username);
        teamSelector = findViewById(R.id.teamSpinner);

//        Handler handler = new Handler(Looper.getMainLooper() , msg -> {
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, teamsList);
//            adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
//
//            teamSelector.setAdapter(adapter);

        // On Click
        btnsubmit.setOnClickListener(view -> {
            // Method to save the userName
            saveUserName();
        });
//            return true ;
//        });

        // Enable the Button
        mUserNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (!btnsubmit.isEnabled()) {
                    btnsubmit.setEnabled(true);
                }

                if (editable.toString().length() == 0){
                    btnsubmit.setEnabled(false);
                }
            }
        });


    }

    // Method to save the userName
    private void saveUserName() {
        // get the text from the edit text
        String username = mUserNameEditText.getText().toString();

        // create shared preference object and set up an editor
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor preferenceEditor = sharedPreferences.edit();

        // save the text to shared preferences
        preferenceEditor.putString(UserName, username);
        preferenceEditor.apply();

        String teamName = teamSelector.getSelectedItem().toString();
        preferenceEditor.putString(TeamName,teamName);
//        Log.i(TAG, "==== Team name settings   ====" + teamName);
        preferenceEditor.apply();

        Toast.makeText(this, "username Saved", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

}
