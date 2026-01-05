package com.example.blackfiguresquizjd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScoreDisplay extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_display);

        TextView scoreText = findViewById(R.id.scoreText);
        TextView Percent = findViewById(R.id.percent);
        Button restartButton = findViewById(R.id.Restart);

        int score = getIntent().getIntExtra("SCORE", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 5);
        int highScore = getIntent().getIntExtra("HIGH_SCORE", 0);


        double percentage = (totalQuestions > 0) ? (score * 100.0 / totalQuestions) : 0;
        Percent.setText(String.format("%.0f%%", percentage));

        scoreText.setText("You got " + score + " out of " + totalQuestions + " Correct");


        restartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent restartIntent = new Intent(ScoreDisplay.this, MainActivity.class);
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(restartIntent);
                finish();
            }
        });
    }
}