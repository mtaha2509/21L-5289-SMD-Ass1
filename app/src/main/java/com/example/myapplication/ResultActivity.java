package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String userName = getIntent().getStringExtra("USER_NAME");
        int score = getIntent().getIntExtra("SCORE", 0);
        int totalQuestions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

        TextView resultTextView = findViewById(R.id.resultTextView);
        Button shareButton = findViewById(R.id.shareButton);

        resultTextView.setText(String.format(
                "Congratulations %s!\nYour Score: %d/%d",
                userName, score, totalQuestions
        ));

        shareButton.setOnClickListener(v -> {
            String shareMessage = String.format(
                    "I scored %d/%d in the Quiz App! Can you beat my score?",
                    score, totalQuestions
            );
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "Share Score"));
        });
    }
}
