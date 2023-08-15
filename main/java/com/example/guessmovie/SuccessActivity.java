package com.example.guessmovie;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import org.apache.commons.text.WordUtils;
import android.view.View;
import android.widget.Button;



import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    public TextView successMessageTextView;
    public TextView dialogueTextView;
    public ImageView posterImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        successMessageTextView = findViewById(R.id.success_message_text_view);
        dialogueTextView = findViewById(R.id.song_name_text_view);
        posterImageView = findViewById(R.id.poster_image_view);

        Intent intent = getIntent();
        String dialogue = intent.getStringExtra("dialogue");
        int posterImageResourceId = intent.getIntExtra("posterImageResourceId", 0);


        String formatteddialogue = WordUtils.capitalizeFully(dialogue.replace("_", " "));

        String successMessage = "Woohoo! It was";

        successMessageTextView.setText(successMessage);
        dialogueTextView.setText(formatteddialogue);
        posterImageView.setImageResource(posterImageResourceId);

        Button continueButton = findViewById(R.id.continue_button);
        Button closeButton = findViewById(R.id.close_button);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessActivity.this, com.example.guessmovie.MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close the app
                finishAffinity();
            }
        });
    }


}