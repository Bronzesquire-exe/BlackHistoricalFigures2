package com.example.blackfiguresquizjd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "QuizPrefs";
    private static final String HIGH_SCORE_KEY = "HighScore";

    private int Score = 0;
    private int currentind = 0;
    private int highScore = 0;

    private List<Question> questions;
    private Button falseB;
    private Button trueB;
    private TextView Correct;
    private TextView Wrong;
    private TextView QuestionText;
    private Button hintButton;
    private Button Gamble;

    private Firebase firebase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuestionText = findViewById(R.id.QuestionText);
        Gamble = findViewById(R.id.letsgamble);
        Correct = findViewById(R.id.IfCorrect);
        Wrong = findViewById(R.id.IfWrong);
        falseB = findViewById(R.id.FalseButton);
        trueB = findViewById(R.id.TrueButton);
        hintButton = findViewById(R.id.Hint);

        Correct.setVisibility(View.INVISIBLE);
        Wrong.setVisibility(View.INVISIBLE);


        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        highScore = prefs.getInt(HIGH_SCORE_KEY, 0);


        firebase = new Firebase();
        firebase.signIn(() -> {
            firebase.getScore(cloudScore -> {
                if (cloudScore > highScore) {
                    highScore = cloudScore;
                }
            });
        });

        questions = loadQuestionsFromJSON();

        Gamble.setOnClickListener(v -> gamble());

        hintButton.setOnClickListener(v -> {
            if (currentind < questions.size() && questions.get(currentind).getHint() != null) {
                Toast.makeText(MainActivity.this, questions.get(currentind).getHint(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No hint available", Toast.LENGTH_SHORT).show();
            }
        });

        showQuestion();
    }

    private List<Question> loadQuestionsFromJSON() {
        List<Question> questionList = new ArrayList<>();
        try {
            InputStream is = getAssets().open("sample.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            JSONArray questionsArray = jsonObject.getJSONArray("questions");

            for (int i = 0; i < questionsArray.length(); i++) {
                JSONObject questionObj = questionsArray.getJSONObject(i);
                String text = questionObj.getString("questionText");
                boolean answer = questionObj.getBoolean("rightAnswer");
                String hint = questionObj.optString("hint", null);

                questionList.add(new Question(text, answer, hint));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error loading questions", Toast.LENGTH_LONG).show();
            questionList.add(new Question("Sample question?", true, "Sample hint"));
        }

        return questionList;
    }

    private void gamble() {
        boolean guess = Math.random() < 0.5;
        boolean correctguess = questions.get(currentind).isRightAnswer();
        Correct.setVisibility(View.INVISIBLE);
        Wrong.setVisibility(View.INVISIBLE);

        if (guess == correctguess) {
            Score = Score * 2;
            Correct.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Gamble Win", Toast.LENGTH_SHORT).show();
        } else {
            Score = Score / 2;
            Wrong.setVisibility(View.VISIBLE);
            Toast.makeText(MainActivity.this, "Gamble Loss", Toast.LENGTH_SHORT).show();
        }
        currentind++;
        showQuestion();
    }

    private void showQuestion() {
        if (currentind < questions.size()) {
            QuestionText.setText(questions.get(currentind).getQuestionText());

            trueB.setOnClickListener(V -> checkAnswer(true));
            falseB.setOnClickListener(v -> checkAnswer(false));
        } else {

            if (Score > highScore) {
                highScore = Score;


                SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                prefs.edit().putInt(HIGH_SCORE_KEY, highScore).apply();


                firebase.saveScore(highScore);
            }


            Intent intent = new Intent(MainActivity.this, ScoreDisplay.class);
            intent.putExtra("SCORE", Score);
            intent.putExtra("TOTAL_QUESTIONS", questions.size());
            intent.putExtra("HIGH_SCORE", highScore);
            startActivity(intent);
        }
    }

    private void checkAnswer(boolean userAnswer) {
        Correct.setVisibility(View.INVISIBLE);
        Wrong.setVisibility(View.INVISIBLE);

        boolean correctAnswer = questions.get(currentind).isRightAnswer();
        if (userAnswer == correctAnswer) {
            Correct.setVisibility(View.VISIBLE);
            Score++;
        } else {
            Wrong.setVisibility(View.VISIBLE);
            Score = 0;
        }
        currentind++;
        showQuestion();
    }
}