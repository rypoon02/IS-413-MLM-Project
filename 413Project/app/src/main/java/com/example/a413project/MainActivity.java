package com.example.a413project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.nex3z.fingerpaintview.FingerPaintView;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    // Initializes the widgets
    private FingerPaintView fingerPaintView;
    private TextView digitDisplay, correctGuessesText, incorrectGuessesText;
    private Button detectButton, correctButton, incorrectButton;
    private DigitReader digitReader;
    private GifImageView robotImage;

    // Initializes the game logic variables
    private int correctGuesses = 0;
    private int incorrectGuesses = 0;
    private final int MAX_GUESSES = 10;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigns the widgets
        fingerPaintView = findViewById(R.id.finger_paint_view);
        digitDisplay = findViewById(R.id.target_digit_text);
        detectButton = findViewById(R.id.detect_button);
        correctButton = findViewById(R.id.correct_button);
        incorrectButton = findViewById(R.id.incorrect_button);
        correctGuessesText = findViewById(R.id.correct_guesses_text);  // Correct guesses counter
        incorrectGuessesText = findViewById(R.id.incorrect_guesses_text);  // Initialize incorrect guesses counter
        robotImage = findViewById(R.id.robot_image);

        // Set custom font
        setCustomFont();

        // Instantiating the DigitReader class
        digitReader = new DigitReader(this);

        randomDigit();

        // Logic for the detect button
        detectButton.setOnClickListener(v -> {
            Bitmap drawnBitmap = fingerPaintView.exportToBitmap();

            // If the user tries and press detect before drawing something, a toast will pop up with instructions
            if (drawnBitmap == null) {
                Toast.makeText(MainActivity.this, "You have to draw before you detect!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Predict digit and display
            int detectedDigit = digitReader.detectDigit(drawnBitmap);
            digitDisplay.setText("Detected: " + detectedDigit);

            // Show Correct/Incorrect buttons and hide the detect button
            correctButton.setVisibility(View.VISIBLE);
            incorrectButton.setVisibility(View.VISIBLE);
            detectButton.setVisibility(View.GONE);
        });

        // Logic for correct button
        correctButton.setOnClickListener(v -> {
            correctGuesses++; // Counts the number of times the button is hit
            Toast.makeText(MainActivity.this, "Correct Guess!", Toast.LENGTH_SHORT).show();
            updateCounters();
            isGameOver();
            randomDigit(); // Generates a new digit after each guess
            userResponseButtons();
            fingerPaintView.clear();
        });

        // Logic for the incorrect button
        incorrectButton.setOnClickListener(v -> {
            incorrectGuesses++; // Counts the number of times the button is hit
            Toast.makeText(MainActivity.this, "Incorrect Guess. Try Again!", Toast.LENGTH_SHORT).show();
            updateCounters();
            isGameOver();
            randomDigit();
            userResponseButtons();
            fingerPaintView.clear();
        });

        fingerPaintView.setOnTouchListener((v,event ) -> {
            if(event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
                robotImage.setImageResource(R.drawable.robot_thinking);
            } else if ((event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL)) {
                robotImage.setImageResource(R.drawable.robot_normal);
            }
            return false;
        });
    }

    // Displays the counters while the user is playing
    private void updateCounters() {
        correctGuessesText.setText("Correct:" + correctGuesses);
        incorrectGuessesText.setText("Incorrect:" + incorrectGuesses);
    }

    private void GoToGameOver() {
        // Intent to go to the game over activity
        Intent intent = new Intent(MainActivity.this, GameOverActivity.class);

        // Passing the correct and incorrect guesses to the game over activity using putExtra
        intent.putExtra("correct_guesses", correctGuesses);
        intent.putExtra("incorrect_guesses", incorrectGuesses);

        // Starting the activity
        startActivity(intent);
        finish();
    }

    // Method to check if the game is over
    private void isGameOver() {
        if (correctGuesses >= MAX_GUESSES) {
            GoToGameOver();  // Call navigate with scores when the game ends
        } else if (incorrectGuesses >= MAX_GUESSES) {
            GoToGameOver();  // Call navigate with scores when the game ends
        }
    }

    // Generates a random target digit
    private void randomDigit() {
        int randomDigit = (int) (Math.random() * 10); // Between 0-9
        digitDisplay.setText("Target Digit: " + randomDigit);
    }

    // Hides the Correct/Incorrect buttons and shows the detect button
    private void userResponseButtons() {
        correctButton.setVisibility(View.GONE);
        incorrectButton.setVisibility(View.GONE);
        detectButton.setVisibility(View.VISIBLE);
    }

    // Method to set the custom font for TextViews and Buttons
    private void setCustomFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "slkscreb.ttf");
        digitDisplay.setTypeface(typeface);
        correctGuessesText.setTypeface(typeface);
        incorrectGuessesText.setTypeface(typeface);
        detectButton.setTypeface(typeface);
        correctButton.setTypeface(typeface);
        incorrectButton.setTypeface(typeface);
    }
}
