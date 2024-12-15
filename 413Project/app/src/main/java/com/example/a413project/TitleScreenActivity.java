package com.example.a413project;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TitleScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_title_screen);

        // Set padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set custom font
        setCustomFont();

        // Find the start button and set an OnClickListener to start MainActivity
        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(v -> {
            Intent intent = new Intent(TitleScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the title screen activity
        });
    }

    private void setCustomFont() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "slkscreb.ttf");

        TextView textView = findViewById(R.id.textView);
        textView.setTypeface(typeface);

        Button startButton = findViewById(R.id.start_button);
        startButton.setTypeface(typeface);
    }
}
