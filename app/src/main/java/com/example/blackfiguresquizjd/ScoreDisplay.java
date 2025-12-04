package com.example.blackfiguresquizjd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScoreDisplay extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);

        TextView scoreText = findViewById(R.id.scoreText);
        TextView Percent;
        Percent = findViewById(R.id.percent);
        Button restartButton = findViewById(R.id.Restart);
        int score = getIntent().getIntExtra("SCORE", 0);
        if (score >= 1) {
            Percent.setText("100%");
        } else {
            Percent.setText("0%");
        }
        scoreText.setText("You got " + score + " Correct");

        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent restartIntent = new Intent(ScoreDisplay.this, MainActivity.class);
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(restartIntent);

            }
        });
    }
}