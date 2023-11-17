package com.example.andersonwordy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    Button goToInputButton;
    Button subButton;
    String currWord;
    Button resetButton;
    Button clearButton;
    String letter = "-";
    GridLayout mainGrid;
    int row = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resetButton = findViewById(R.id.resetButton);
        goToInputButton = findViewById(R.id.inputButton);
        mainGrid = findViewById(R.id.wordyGrid);
        subButton = findViewById(R.id.submitButton);
        clearButton = findViewById(R.id.clearButton);
        currWord = "Hello";
        setBlank();
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;mainGrid.getChildCount()>i;i++){
                    EditText temp = (EditText) mainGrid.getChildAt(i);
                    temp.setBackgroundColor(Color.WHITE); //also makes the lines underneath white but idk why
                    setBlank();
                }
                setBlank();
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0;mainGrid.getChildCount()>i;i++){
                    EditText temp = (EditText) mainGrid.getChildAt(i);
                    temp.setBackgroundColor(Color.WHITE); //also makes the lines underneath white but idk why
                    setBlank();
                }
            }
        });
        subButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    for(int i = 0; i<mainGrid.getColumnCount();i++){
                        char currWordChar = currWord.charAt(i);
                        EditText currET = (EditText) mainGrid.getChildAt(row+i);
                        currET.setFocusable(false);
                        EditText nextET = (EditText) mainGrid.getChildAt(row+i+5);
                        nextET.setFocusable(true);
                        char checkGuessLttr= currET.getText().toString().charAt(0);
                        if(currWordChar==checkGuessLttr){
                            currET.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
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
            row=row+5;
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
    public void setBlank(){
        for(int i = 0;mainGrid.getChildCount()>i;i++){
            EditText temp = (EditText) mainGrid.getChildAt(i);
            if(i>=0&&i<=4){
                temp.setFocusable(true);
            }else{
                temp.setFocusable(false);
            }
        }
    }
}