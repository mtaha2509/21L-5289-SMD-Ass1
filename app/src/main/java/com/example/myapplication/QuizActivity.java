package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private RadioButton option1, option2, option3, option4;
    private Button previousButton, nextButton;

    // 10 questions with corresponding options and correct answer indexes
    private String[] questions = {
            "What is the capital of France?",
            "Which planet is known as the Red Planet?",
            "What is 2 + 2?",
            "What is the largest ocean on Earth?",
            "Who wrote 'Hamlet'?",
            "What is the boiling point of water (°C)?",
            "Which element has the chemical symbol 'O'?",
            "What is the currency of Japan?",
            "How many continents are there?",
            "What is the tallest mountain in the world?"
    };

    private String[][] options = {
            {"London", "Paris", "Berlin", "Rome"},
            {"Venus", "Mars", "Jupiter", "Saturn"},
            {"3", "4", "5", "6"},
            {"Atlantic", "Indian", "Arctic", "Pacific"},
            {"Charles Dickens", "William Shakespeare", "Mark Twain", "Jane Austen"},
            {"50", "75", "100", "120"},
            {"Hydrogen", "Oxygen", "Carbon", "Nitrogen"},
            {"Yen", "Dollar", "Euro", "Pound"},
            {"5", "6", "7", "8"},
            {"K2", "Everest", "Kangchenjunga", "Lhotse"}
    };

    // Correct answer indexes (0-based)
    private int[] correctAnswers = {1, 1, 1, 3, 1, 2, 1, 0, 2, 1};

    private int[] userAnswers;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get username from previous activity
        String userName = getIntent().getStringExtra("USER_NAME");

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.optionsRadioGroup);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);

        // Initialize userAnswers array with -1 indicating unanswered
        userAnswers = new int[questions.length];
        Arrays.fill(userAnswers, -1);

        loadQuestion(currentQuestionIndex);

        nextButton.setOnClickListener(v -> {
            int selectedRadioId = optionsRadioGroup.getCheckedRadioButtonId();
            if (selectedRadioId == -1) {
                Toast.makeText(this, "Please select an answer!", Toast.LENGTH_SHORT).show();
                return;
            }
            int selectedOptionIndex = getSelectedOptionIndex(selectedRadioId);
            userAnswers[currentQuestionIndex] = selectedOptionIndex;

            if (currentQuestionIndex < questions.length - 1) {
                currentQuestionIndex++;
                loadQuestion(currentQuestionIndex);
            } else {
                calculateScore();
                Intent resultIntent = new Intent(QuizActivity.this, ResultActivity.class);
                resultIntent.putExtra("USER_NAME", userName);
                resultIntent.putExtra("SCORE", score);
                resultIntent.putExtra("TOTAL_QUESTIONS", questions.length);
                startActivity(resultIntent);
                finish();
            }
        });

        previousButton.setOnClickListener(v -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
                loadQuestion(currentQuestionIndex);
            }
        });
    }

    private void loadQuestion(int index) {
        questionTextView.setText(questions[index]);
        optionsRadioGroup.clearCheck();

        option1.setText(options[index][0]);
        option2.setText(options[index][1]);
        option3.setText(options[index][2]);
        option4.setText(options[index][3]);

        previousButton.setEnabled(index > 0);

        // Restore previously selected answer if exists
        if (userAnswers[index] != -1) {
            getRadioButtonByIndex(userAnswers[index]).setChecked(true);
        }
    }

    private void calculateScore() {
        score = 0;
        for (int i = 0; i < questions.length; i++) {
            if (userAnswers[i] != -1 && userAnswers[i] == correctAnswers[i]) {
                score++;
            }
        }
    }

    private int getSelectedOptionIndex(int selectedRadioId) {
        if (selectedRadioId == option1.getId()) return 0;
        if (selectedRadioId == option2.getId()) return 1;
        if (selectedRadioId == option3.getId()) return 2;
        if (selectedRadioId == option4.getId()) return 3;
        return -1;
    }

    private RadioButton getRadioButtonByIndex(int index) {
        switch (index) {
            case 0: return option1;
            case 1: return option2;
            case 2: return option3;
            case 3: return option4;
            default: return null;
        }
    }
}
