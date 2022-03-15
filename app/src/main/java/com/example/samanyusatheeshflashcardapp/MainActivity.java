package com.example.samanyusatheeshflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView flashcardQuestion;
    TextView flashcardAnswer;
    TextView answerChoiceOne, answerChoiceTwo, answerChoiceThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardQuestion = findViewById(R.id.flashcard_question_textview);
        flashcardAnswer = findViewById(R.id.flashcard_answer_textview);
        answerChoiceOne = findViewById(R.id.answer_option_one);
        answerChoiceTwo = findViewById(R.id.answer_option_two);
        answerChoiceThree = findViewById(R.id.answer_option_three);
        ImageView flashcardAdd = findViewById(R.id.flashcard_add_button);
        ImageView flashcardEdit = findViewById(R.id.flashcard_edit_button);

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);
            }
        });
        flashcardAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardAnswer.setVisibility(View.INVISIBLE);
                flashcardQuestion.setVisibility(View.VISIBLE);
            }
        });

        answerChoiceOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.answer_option_one).setBackgroundColor(getResources().getColor(R.color.my_red_color, null));
                findViewById(R.id.answer_option_three).setBackgroundColor(getResources().getColor(R.color.my_green_color, null));
            }
        });

        answerChoiceTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.answer_option_two).setBackgroundColor(getResources().getColor(R.color.my_red_color, null));
                findViewById(R.id.answer_option_three).setBackgroundColor(getResources().getColor(R.color.my_green_color, null));
            }
        });

        answerChoiceThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.answer_option_three).setBackgroundColor(getResources().getColor(R.color.my_green_color, null));
            }
        });

        flashcardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);
            }
        });

        flashcardEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("question", flashcardQuestion.getText().toString());
                intent.putExtra("rightAnswer", flashcardAnswer.getText().toString());
                intent.putExtra("wrongAnswer1", answerChoiceOne.getText().toString());
                intent.putExtra("wrongAnswer2", answerChoiceTwo.getText().toString());
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        answerChoiceOne.setBackgroundColor(getResources().getColor(R.color.my_tan, null));
        answerChoiceTwo.setBackgroundColor(getResources().getColor(R.color.my_tan, null));
        answerChoiceThree.setBackgroundColor(getResources().getColor(R.color.my_tan, null));


        if (requestCode == 100)
        {
            if (data != null) {
                String questionString = data.getExtras().getString("QUESTION_KEY");
                String rightAnswerString = data.getExtras().getString("RIGHTANSWER_KEY");
                String wrongAnswer1String = data.getExtras().getString("WRONGANSWER1_KEY");
                String wrongAnswer2String = data.getExtras().getString("WRONGANSWER2_KEY");
                flashcardQuestion.setText(questionString);
                flashcardAnswer.setText(rightAnswerString);
                answerChoiceOne.setText(wrongAnswer1String);
                answerChoiceTwo.setText(wrongAnswer2String);
                answerChoiceThree.setText(rightAnswerString);

            }
        }
        Snackbar.make(flashcardQuestion,
                "Successfully saved card!",
                Snackbar.LENGTH_SHORT)
                .show();
    }
}