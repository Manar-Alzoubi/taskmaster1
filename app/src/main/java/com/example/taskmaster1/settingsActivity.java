package com.example.taskmaster1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class settingsActivity extends AppCompatActivity {


    private static final String TAG = settingsActivity.class.getSimpleName();
    public static final String UserName = "username";
    private EditText mUserNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button btnsubmit = findViewById(R.id.btnsubmit);
        mUserNameEditText = findViewById(R.id.username);

        // On Click
        btnsubmit.setOnClickListener(view -> {
            // Method to save the userName
            saveUserName();

            // Check if no view has focus: https://stackoverflow.com/questions/1109022/how-to-close-hide-the-android-soft-keyboard-programmatically
            View newview = this.getCurrentFocus();
            if (newview != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(newview.getWindowToken(), 0);
            }
        });
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
        Toast.makeText(this, "the username is saved ", Toast.LENGTH_SHORT).show();

    }
}
