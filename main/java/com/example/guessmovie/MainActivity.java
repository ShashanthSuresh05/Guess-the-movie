package com.example.guessmovie;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    public Button playButton;
    public Button guessButton;
    public Button giveUpButton;
    private EditText guessEditText;
    private int incorrectGuesses = 0;

    //{R.raw., R.drawable.}
    private Integer[][] tunes = {
            {R.raw.batman, R.drawable.poster1},
            {R.raw.kgf, R.drawable.poster2},
            {R.raw.star_wars, R.drawable.poster3},
            {R.raw.vikram, R.drawable.poster4},
            {R.raw.brahmastra, R.drawable.poster5},
            {R.raw.john_wick, R.drawable.poster6},
            {R.raw.pushpa, R.drawable.poster7},
            {R.raw.revenge_of_the_sith, R.drawable.poster8},
            {R.raw.raone, R.drawable.poster9},
            {R.raw.batman_v_superman, R.drawable.poster10}
    };

    private int currentTuneIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.playButton);
        guessButton = findViewById(R.id.guessButton);
        guessEditText = findViewById(R.id.guessEditText);
        giveUpButton = findViewById(R.id.giveUpButton);

        // Shuffle the dialogue array
        List<Integer[]> tunesList = Arrays.asList(tunes);
        Collections.shuffle(tunesList);
        tunes = tunesList.toArray(new Integer[0][0]);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playTune();
            }
        });

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGuess();
            }
        });


        giveUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentTuneIndex++;
                playTune();
            }
        });
    }

    private void playTune() {
        if (currentTuneIndex < tunes.length) {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }

            int tuneResourceId = tunes[currentTuneIndex][0];
            mediaPlayer = MediaPlayer.create(this, tuneResourceId);
            mediaPlayer.start();
        }
    }

    private void checkGuess() {
        String userGuess = guessEditText.getText().toString().replace(" ", "_");
        if (!userGuess.isEmpty()) {
            Integer[] currentTuneResources = tunes[currentTuneIndex];
            int currentTuneResourceId = currentTuneResources[0];
            String currentTuneName = getResources().getResourceEntryName(currentTuneResourceId).replace(".mp3", "");
            if (userGuess.equalsIgnoreCase(currentTuneName)) {
                int posterImageResourceId = currentTuneResources[1];
                mediaPlayer.stop();
                currentTuneIndex++;
                showSuccessScreen(currentTuneName, posterImageResourceId);
            } else {
                Toast.makeText(this, "Incorrect, try again.", Toast.LENGTH_LONG).show();
                handleIncorrectGuess();
            }
        } else {
            Toast.makeText(this, "Please enter a guess.", Toast.LENGTH_LONG).show();
        }
    }


    private void showSuccessScreen(String songName, int posterImageResourceId) {
        Intent intent = new Intent(this, SuccessActivity.class);
        intent.putExtra("dialogue", songName);  // Pass the song name as "dialogue" extra
        intent.putExtra("posterImageResourceId", posterImageResourceId);
        startActivity(intent);
    }






    private void handleIncorrectGuess() {

        if (incorrectGuesses < 2) {
            incorrectGuesses++;
        } else {
            showFailureScreen();
        }
    }



    private void showFailureScreen() {

        Toast.makeText(this, "Game over!", Toast.LENGTH_SHORT).show();
    }




}
