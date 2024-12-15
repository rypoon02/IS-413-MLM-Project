package com.example.a413project;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOverActivity extends AppCompatActivity {

    // These are our variables for displaying the user's final score and for displaying the play again and exit buttons
    private TextView finalScoreText, gameOverText;
    private Button playAgainButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        // Making sure that the TextViews in the XML correspond to our buttons
        finalScoreText = findViewById(R.id.final_score_text);
        gameOverText = findViewById(R.id.game_over_message);
        playAgainButton = findViewById(R.id.play_again_button);
        exitButton = findViewById(R.id.exit_button);

        // Using intents to pass the data in the MainActivity to this activity
        // When the GameOverActivity runs, it'll display whatever the user's score was in the MainActivity as their final score
        int correctGuesses = getIntent().getIntExtra("correct_guesses", 0);
        int incorrectGuesses = getIntent().getIntExtra("incorrect_guesses", 0);

        // TextView for the final score displaying
        finalScoreText.setText("Final Score:\nCorrect: " + correctGuesses + "\nIncorrect: " + incorrectGuesses);

        // Set custom font
        setCustomFont();

        // Using a listener so that when the play again button is pressed it returns to the MainActivity again
        playAgainButton.setOnClickListener(v -> {
            Intent intent = new Intent(GameOverActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // This closes the game over activity
        });

        // Using a listener so that the app closes when the user presses the button
        exitButton.setOnClickListener(v -> {
            finish(); // This closes the game over activity
            System.exit(0); // This forces the app to close
        });
    }

    // Method to set the custom font for TextViews and Buttons
    private void setCustomFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "slkscreb.ttf");
        finalScoreText.setTypeface(typeface);
        gameOverText.setTypeface(typeface);
        playAgainButton.setTypeface(typeface);
        exitButton.setTypeface(typeface);
    }
}
