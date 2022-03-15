package com.example.samanyusatheeshflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ImageView flashcardCancel = findViewById(R.id.flashcard_cancel_button);
        ImageView flashcardSave = findViewById(R.id.flashcard_save_button);
        EditText flashcardEditQuestion = findViewById(R.id.flashcard_edit_question);
        EditText flashcardEditAnswer = findViewById(R.id.flashcard_edit_answer);

        String question = getIntent().getStringExtra("question");
        String answer = getIntent().getStringExtra("answer");
        flashcardEditQuestion.setText(question);
        flashcardEditAnswer.setText(answer);


        flashcardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        flashcardSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String saveQuestion = flashcardEditQuestion.getText().toString();
                String saveAnswer = flashcardEditAnswer.getText().toString();
                if (saveQuestion.matches("") || saveAnswer.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Must enter both question and answer!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent data = new Intent();
                data.putExtra("QUESTION_KEY", saveQuestion);
                data.putExtra("ANSWER_KEY", saveAnswer);
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}