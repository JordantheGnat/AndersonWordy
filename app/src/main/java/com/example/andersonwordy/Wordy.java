package com.example.andersonwordy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Wordy extends AppCompatActivity {
    Button backButton;
    Button submitToFB;
    EditText submission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordy);
        backButton = findViewById(R.id.backButton);
        submitToFB = findViewById(R.id.firebaseButton);
        submission  =findViewById(R.id.submissionText);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        submitToFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Go back to the previous activity
            }
        });
    }



}