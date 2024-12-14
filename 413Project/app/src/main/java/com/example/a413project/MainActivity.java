package com.example.a413project;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.nex3z.fingerpaintview.FingerPaintView;

public class MainActivity extends AppCompatActivity {

    //Initializes the widgets
    private FingerPaintView fingerPaintView;
    private DigitReader digitReader;
    private TextView digitDisplay;
    Button clearButton;
    Button detectButton;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Assigns the widgets
        fingerPaintView = findViewById(R.id.fingerpaint_view);
        digitDisplay = findViewById(R.id.display);
        clearButton = findViewById(R.id.clear_button);
        detectButton = findViewById(R.id.detect_button);

        //Instantiates the DigitReader class
        digitReader = new DigitReader(this);

        //Clears the board
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fingerPaintView.clear();
            }
        });

        //Detects the number
        detectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detectDigit();
            }
        });
    }

    //Exports bitmap and displays the number in a textview
    private void detectDigit() {
        Bitmap drawnBitmap = fingerPaintView.exportToBitmap();
        int detectedDigit = digitReader.detectDigit(drawnBitmap);
        digitDisplay.setText(String.valueOf(detectedDigit));
    }
}
