package com.example.blackfiguresquizjd;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private int Score = 0;
    private int currentind = 0;
    private boolean CorrectA;
    private boolean WrongA;
    


    private Question[] questions;
    private Button falseB;
    private Button trueB;

    private TextView Correct;

    private TextView Wrong;

    private TextView QuestionText;
    private Button hintButton;
    private String[] hints;
    private Button Gamble;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionText = findViewById(R.id.QuestionText);
        Gamble = findViewById(R.id.letsgamble);
        Correct = (TextView) findViewById(R.id.IfCorrect);
        Wrong = (TextView) findViewById(R.id.IfWrong);
        falseB = (Button) findViewById(R.id.FalseButton);
        trueB = (Button) findViewById(R.id.TrueButton);
        hintButton = findViewById(R.id.Hint);
        Correct.setVisibility(View.INVISIBLE);
        Wrong.setVisibility(View.INVISIBLE);


Gamble.setOnClickListener(new View.OnClickListener(){
    public void onClick(View v){
        gamble();
    }
});
hintButton.setOnClickListener(new View.OnClickListener(){
    public void onClick(View v){
     if (currentind < hints.length) {
         Toast.makeText(MainActivity.this, hints[currentind], Toast.LENGTH_SHORT).show();
     }
    }
});

         questions = new Question[]{
                new Question(getString(R.string.q1), true),
                new Question(getString(R.string.q2), true),
                new Question(getString(R.string.q3), false),
                new Question(getString(R.string.q4), false),
                new Question(getString(R.string.q5), true)

        };


        hints = new String[]

                {
                        getString(R.string.h1),
                        getString(R.string.h2),
                        getString(R.string.h3),
                        getString(R.string.h4),
                        getString(R.string.h5)
                };
        showQuestion();
    }

    private void gamble(){
        boolean guess = Math.random() < 0.5;
        boolean correctguess = questions[currentind].isRightAnswer();
        Correct.setVisibility(View.INVISIBLE);
        Wrong.setVisibility(View.VISIBLE);

        if(guess == correctguess){
            Score = Score*2;
            Correct.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Gamble Win", Toast.LENGTH_SHORT).show();
        }else{
            Score = Score/2;

            Wrong.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this,"Gamble Loss",Toast.LENGTH_SHORT).show();
        }
        currentind++;
        showQuestion();
    }





    private void showQuestion() {
        if (currentind < questions.length) {
            QuestionText.setText(questions[currentind].getQuestionText());
        } else {
            Intent intent = new Intent(MainActivity.this, ScoreDisplay.class);
            intent.putExtra("SCORE", Score);
            startActivity(intent);
        }

        trueB.setOnClickListener(new OnClickListener() {
            public void onClick(View V) {
                checkAnswer(true);
            }

        });
        falseB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(false);
            }
        });
    }

    private void checkAnswer(boolean userAnswer) {
        Correct.setVisibility(View.INVISIBLE);
        Wrong.setVisibility(View.INVISIBLE);

        boolean correctAnswer = questions[currentind].isRightAnswer();
        if (userAnswer == correctAnswer) {
            Correct.setVisibility(View.VISIBLE);
            Score++;

        } else {
            Wrong.setVisibility(View.VISIBLE);

        }
        currentind++;
        showQuestion();
    }

}


