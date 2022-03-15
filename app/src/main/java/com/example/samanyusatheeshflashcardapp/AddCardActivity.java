package com.example.samanyusatheeshflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        ImageView flashcardCancel = findViewById(R.id.flashcard_cancel_button);
        ImageView flashcardSave = findViewById(R.id.flashcard_save_button);

        flashcardCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        flashcardSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data = new Intent();
                data.putExtra("QUESTION_KEY",((EditText) findViewById(R.id.flashcard_edit_question)).getText().toString());
                data.putExtra("ANSWER_KEY", ((EditText) findViewById(R.id.flashcard_edit_answer)).getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}