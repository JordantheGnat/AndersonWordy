package com.example.andersonwordy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button goToInputButton;
    Button subButton;
    String currWord;
    Button resetButton;
    Button clearButton;
    String letter = "-";
    GridLayout mainGrid;
    FirebaseDatabase database;
    DatabaseReference myRef;
    TextView winner;
    int row = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        winner = findViewById(R.id.winTV);
        setContentView(R.layout.activity_main);
        resetButton = findViewById(R.id.resetButton);
        goToInputButton = findViewById(R.id.inputButton);
        mainGrid = findViewById(R.id.wordyGrid);
        subButton = findViewById(R.id.submitButton);
        clearButton = findViewById(R.id.clearButton);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("jordleWords");
        randomWord();
        //setBlank();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setBlank();
                for(int i = 0;mainGrid.getChildCount()>i;i++){
                    EditText temp = (EditText) mainGrid.getChildAt(i);
                    temp.setBackgroundColor(Color.WHITE); //also makes the lines underneath white but idk why
                    temp.setFocusable(true);//w h y doesn't this work
                    temp.setText("");
                }
                row=0;

                winner = findViewById(R.id.winTV);
                winner.setBackgroundColor(Color.WHITE);
                winner.setText("");
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setBlank();
                for(int i = 0;mainGrid.getChildCount()>i;i++){
                    EditText temp = (EditText) mainGrid.getChildAt(i);
                    temp.setBackgroundColor(Color.WHITE); //also makes the lines underneath white but idk why
                    temp.setText("");
                    temp.setFocusable(true);
                    winner = findViewById(R.id.winTV);
                    winner.setBackgroundColor(Color.WHITE);
                    winner.setText("");
                }
            }
        });
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int greenCount = 0;
                if(currWord.length() == 5) {
                    currWord = currWord.toUpperCase();
                    HashMap<Character, Integer> rowLetters = new HashMap<>();
                    HashMap<Character,Integer>  goalWordLetters =  new HashMap<>();
                    for(int i = 0; i< currWord.length();i++){
                        char tempChar = currWord.charAt(i);
                        char guessLetter = ((EditText) mainGrid.getChildAt(row+i)).getText().toString().charAt(0);
                        if(goalWordLetters.containsKey(tempChar)){
                            goalWordLetters.put(tempChar,goalWordLetters.get(tempChar)+1);
                        }else{
                            goalWordLetters.put(tempChar,1);
                        }
                        if(rowLetters.containsKey(guessLetter)){
                            rowLetters.put(guessLetter,rowLetters.get(guessLetter)+1);
                        }else{
                            rowLetters.put(guessLetter,1);
                        }
                    }
                    int sum = 0;
                    for (int value : rowLetters.values()) {
                        sum += value;
                    }
                    if(sum!=5){
                        Toast.makeText(getApplicationContext(),"Need to imput 5 letters please", Toast.LENGTH_LONG).show();
                        return;
                    }

                    for(int i = 0; i<mainGrid.getColumnCount();i++){
                        char currWordChar = currWord.charAt(i);
                        EditText currET = (EditText) mainGrid.getChildAt(row+i);
                        currET.setFocusable(false);
                        //EditText nextET = (EditText) mainGrid.getChildAt(row+i+5); check setBlank() for explanation
                        //nextET.setFocusable(true);
                        char checkGuessLttr= currET.getText().toString().charAt(0);
                        if(currWordChar==checkGuessLttr){
                            currET.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
                            greenCount++;
                            Integer val = goalWordLetters.get(checkGuessLttr);
                            goalWordLetters.put(checkGuessLttr,val-1);
                        }else if(goalWordLetters.get(checkGuessLttr)==null||goalWordLetters.get(checkGuessLttr)==0){
                            currET.setBackgroundColor(Color.GRAY);
                        }
                        else if(goalWordLetters.get(checkGuessLttr)>=1){
                            currET.setBackgroundColor(Color.YELLOW);//if letters are before green, they're yellow even though they shouldnt be

                        }
                       EditText temp = (EditText) mainGrid.getChildAt(row+i);
                       String tempStr = temp.toString();


                       }
                }else{
                    Toast.makeText(getApplicationContext(),"Uh oh word not 5 :(",Toast.LENGTH_SHORT).show();
                }
                if(greenCount==5){
                    finishGame(true);
                }
            row=row+5;
                if(row==30){
                    finishGame(false);
                }
            }});

        goToInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ListActivity
                Intent intent = new Intent(MainActivity.this, Wordy.class);
                startActivity(intent);
            }
        });
    }
 /*   public void setBlank(){
        for(int i = 0;mainGrid.getChildCount()>i;i++){
            EditText temp = (EditText) mainGrid.getChildAt(i);
            temp.setFocusable(true);
            if(i>=row+0&&i<=row+4){

            }else{                       supposed to make things unfocused, breaks everything. idk
                temp.setFocusable(false);
            }
            temp.setText("");
        }
    } */
    public void randomWord(){
        ArrayList<String> allWords = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot temp : snapshot.getChildren()){
                    allWords.add(temp.getValue(String.class));
                }
                Random randy =  new Random();
                int randomNum = randy.nextInt(allWords.size());
                currWord = allWords.get(randomNum);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void finishGame(boolean  won){
        for(int i = 0;mainGrid.getChildCount()>i;i++){
            EditText temp = (EditText) mainGrid.getChildAt(i);
            //temp.setFocusable(false);
    }
        if(won == true){
            winner = findViewById(R.id.winTV);
           winner.setText("YOU WIN!!!");
           winner.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
        }else{
            winner = findViewById(R.id.winTV);
            winner.setText("YOU LOSE!!!");
            winner.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }
}