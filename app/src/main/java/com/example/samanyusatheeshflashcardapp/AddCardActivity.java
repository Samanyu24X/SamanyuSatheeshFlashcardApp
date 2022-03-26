package com.example.samanyusatheeshflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    String originalQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ImageView flashcardCancel = findViewById(R.id.flashcard_cancel_button);
        ImageView flashcardSave = findViewById(R.id.flashcard_save_button);
        EditText flashcardEditQuestion = findViewById(R.id.flashcard_edit_question);
        EditText flashcardEditRightAnswer = findViewById(R.id.flashcard_edit_rightanswer);
        EditText flashcardEditWrongAnswer1 = findViewById(R.id.flashcard_edit_wronganswer1);
        EditText flashcardEditWrongAnswer2 = findViewById(R.id.flashcard_edit_wronganswer2);

        String question = getIntent().getStringExtra("question");
        originalQuestion = question;
        String rightAnswer = getIntent().getStringExtra("rightAnswer");
        String wrongAnswer1 = getIntent().getStringExtra("wrongAnswer1");
        String wrongAnswer2 = getIntent().getStringExtra("wrongAnswer2");

        flashcardEditQuestion.setText(question);
        flashcardEditRightAnswer.setText(rightAnswer);
        flashcardEditWrongAnswer1.setText(wrongAnswer1);
        flashcardEditWrongAnswer2.setText(wrongAnswer2);


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
                String saveRightAnswer = flashcardEditRightAnswer.getText().toString();
                String saveWrongAnswer1 = flashcardEditWrongAnswer1.getText().toString();
                String saveWrongAnswer2 = flashcardEditWrongAnswer2.getText().toString();

                if (saveQuestion.matches("") || saveRightAnswer.matches("")
                    || saveWrongAnswer1.matches("") || saveWrongAnswer2.matches(""))
                {
                    Toast.makeText(getApplicationContext(), "Must enter question and answers!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // find the flashcard these belong to

                Intent data = new Intent();
                data.putExtra("QUESTION_KEY", saveQuestion);
                data.putExtra("RIGHTANSWER_KEY", saveRightAnswer);
                data.putExtra("WRONGANSWER1_KEY", saveWrongAnswer1);
                data.putExtra("WRONGANSWER2_KEY", saveWrongAnswer2);
                setResult(RESULT_OK, data);
                System.out.println("Packed everything up!");
                finish();
            }
        });
    }
}