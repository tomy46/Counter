package com.example.counter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Random;

public class HomeActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;

    TextView timerText;
    TextView wordText;
    EditText wordInput;

   String[] words;
    String randomWord;
    boolean running;
    private long startTime;
    long millis;
    long millisbien;
    int seconds;
    int minutes;
    long bestTime = 1;
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            millis = System.currentTimeMillis() - startTime;
            if (millis == 0)
                return;
            millisbien = millis;
            if (millis > 1000)
                millisbien = millis % (int) Math.pow(10, (int) Math.log10(millis));

            seconds = (int) (millis / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            timerText.setText(String.format("%d:%02d:%02d", minutes, seconds, millisbien));
            timerHandler.postDelayed(this, 10);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        //Setters
        firebaseAuth = FirebaseAuth.getInstance();
        Button startButton = findViewById(R.id.button_start);
        Button logoutBUtton = findViewById(R.id.logout_button);
        timerText = findViewById(R.id.timer_text);
        wordText = findViewById(R.id.word_text);
        wordInput = findViewById(R.id.word_input);
        running = false;

        logoutBUtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(running){
                    if(wordInput.getText().toString().equals(randomWord.toString())  ){
                        if (millis == 0)
                            return;
                        if (bestTime > millis || bestTime == 1) {
                            bestTime = millis;
                            Toast.makeText(HomeActivity.this, "Fue tu mejor tiempo",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(HomeActivity.this, "No fue tu mejor tiempo",
                                    Toast.LENGTH_SHORT).show();
                        }

                        timerHandler.removeCallbacks(timerRunnable);
                        startButton.setText(getString(R.string.start_button));
                        running = false;
                    }
                    else
                        Toast.makeText(HomeActivity.this, getString(R.string.wrong_word),
                                Toast.LENGTH_SHORT).show();
                }
                else {
                    //Settings strings
                    words = new String[]{getString(R.string.holand), getString(R.string.truck), getString(R.string.decoration)};

                    randomWord = randomWord();
                    System.out.println(randomWord);
                    wordText.setText(randomWord);
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    startButton.setText(getString(R.string.submit_button));
                    running = true;

                }
            }
        });


    }

    void logout(){
        firebaseAuth.signOut();

        Intent homeIntent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(homeIntent);
    }
    String randomWord(){
        Random rand = new Random(); //instance of random class
        int upperbound = words.length;
        int random = rand.nextInt(upperbound);
        return words[random];
    }

    void startRunner(){

    }
}