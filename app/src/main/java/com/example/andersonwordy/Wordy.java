package com.example.andersonwordy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        // use likepusher.setValue("Butts");
        submitToFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference pusher = databaseReference.child("jordleWords").push();
                String temp = submission.getText().toString();
                if(temp.length()!=5){
                    Toast.makeText(getApplicationContext(),"Not 5 letters",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(),"Added to Firebase",Toast.LENGTH_LONG).show();
                pusher.setValue(temp);//this auto limits to five letters, based on the xml
                //but if you put a 5 letter word into fb itself, it wouldn't be only 5
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