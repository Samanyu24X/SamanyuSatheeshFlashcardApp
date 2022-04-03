package com.example.samanyusatheeshflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.plattysoft.leonids.ParticleSystem;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashCards;
    TextView flashcardQuestion;
    TextView flashcardAnswer;
    TextView answerChoiceOne, answerChoiceTwo, answerChoiceThree;
    int currentCardDisplayedIndex = 0;
    Flashcard cardToEdit;

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
        ImageView flashcardNext = findViewById(R.id.flashcard_next_button);
        ImageView flashcardDelete = findViewById(R.id.flashcard_delete_button);

        flashcardDatabase = new FlashcardDatabase(this);
        allFlashCards = flashcardDatabase.getAllCards();

        if (allFlashCards != null && allFlashCards.size() > 0)
        {
            flashcardQuestion.setText(allFlashCards.get(0).getQuestion());
            flashcardAnswer.setText(allFlashCards.get(0).getAnswer());
            answerChoiceOne.setText(allFlashCards.get(0).getWrongAnswer1());
            answerChoiceTwo.setText(allFlashCards.get(0).getWrongAnswer2());
            answerChoiceThree.setText(allFlashCards.get(0).getAnswer());
        }
        else
        {
            flashcardQuestion.setText("Add a question!");
            flashcardAnswer.setText("Add an answer!");
            answerChoiceOne.setText("Add answer choices!");
        }

        flashcardQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);

                // get the center for the clipping circle

                int cx = flashcardAnswer.getWidth() / 2;
                int cy = flashcardAnswer.getHeight() / 2;

                // get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

                // create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(flashcardAnswer, cx, cy, 0f, finalRadius);

                // hide the question and show the answer to prepare for playing the animation!
                flashcardQuestion.setVisibility(View.INVISIBLE);
                flashcardAnswer.setVisibility(View.VISIBLE);

                anim.setDuration(3000);
                anim.start();

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
                new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.answer_option_three), 100);
            }
        });

        flashcardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent,100);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        flashcardEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);

                if (allFlashCards.size() == 0)
                {
                    intent.putExtra("question", "");
                    intent.putExtra("rightAnswer", "");
                    intent.putExtra("wrongAnswer1", "");
                    intent.putExtra("wrongAnswer2", "");
                    MainActivity.this.startActivityForResult(intent, 200);
                    overridePendingTransition(R.anim.right_in, R.anim.left_out);
                    return;
                }

                intent.putExtra("question", flashcardQuestion.getText().toString());
                intent.putExtra("rightAnswer", flashcardAnswer.getText().toString());
                intent.putExtra("wrongAnswer1", answerChoiceOne.getText().toString());
                intent.putExtra("wrongAnswer2", answerChoiceTwo.getText().toString());

                for (int index = 0; index < allFlashCards.size(); index++)
                {
                    if (allFlashCards.get(index).getQuestion().equals(flashcardQuestion.getText().toString()))
                    {
                        cardToEdit = allFlashCards.get(index);
                        break;
                    }
                }
                MainActivity.this.startActivityForResult(intent, 200);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        flashcardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation leftOutAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in);

                answerChoiceOne.setBackgroundColor(getResources().getColor(R.color.my_tan, null));
                answerChoiceTwo.setBackgroundColor(getResources().getColor(R.color.my_tan, null));
                answerChoiceThree.setBackgroundColor(getResources().getColor(R.color.my_tan, null));
                flashcardQuestion.startAnimation(leftOutAnim);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        flashcardQuestion.startAnimation(rightInAnim);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                if (allFlashCards.size() == 0)
                    return;
                currentCardDisplayedIndex++;

                if (currentCardDisplayedIndex >= allFlashCards.size())
                {
                    Snackbar.make(flashcardQuestion,"Reached the last card, going back to start.",
                        Snackbar.LENGTH_SHORT).show();
                    currentCardDisplayedIndex = 0;
                }

                allFlashCards = flashcardDatabase.getAllCards();
                Flashcard flashcard = allFlashCards.get(currentCardDisplayedIndex);
                flashcardQuestion.setText(flashcard.getQuestion());
                flashcardAnswer.setText(flashcard.getAnswer());
                answerChoiceOne.setText(flashcard.getWrongAnswer1());
                answerChoiceTwo.setText(flashcard.getWrongAnswer2());
                answerChoiceThree.setText(flashcard.getAnswer());
            }
        });

        flashcardDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flashcardDatabase.deleteCard(flashcardQuestion.getText().toString());
                allFlashCards = flashcardDatabase.getAllCards();
                if (allFlashCards.size() == 0)
                {
                    flashcardQuestion.setText("Add a question!");
                    flashcardAnswer.setText("Add an answer!");
                    answerChoiceOne.setText("Add answer choices!");
                    answerChoiceTwo.setText("");
                    answerChoiceThree.setText("");
                    return;
                }
                else if (currentCardDisplayedIndex == 0)
                {
                    currentCardDisplayedIndex++; // offset the subtraction
                }
                currentCardDisplayedIndex--; // go previous index
                Flashcard flashcard = allFlashCards.get(currentCardDisplayedIndex);
                flashcardQuestion.setText(flashcard.getQuestion());
                flashcardAnswer.setText(flashcard.getAnswer());
            }
        });

    }

    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        answerChoiceOne.setBackgroundColor(getResources().getColor(R.color.my_tan, null));
        answerChoiceTwo.setBackgroundColor(getResources().getColor(R.color.my_tan, null));
        answerChoiceThree.setBackgroundColor(getResources().getColor(R.color.my_tan, null));

        /* if (requestCode == 100)
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
        } */
        if (requestCode == 100 && resultCode == RESULT_OK)
        {
            String question = data.getExtras().get("QUESTION_KEY").toString();
            String answer = data.getExtras().get("RIGHTANSWER_KEY").toString();
            String wrongAnswer1 = data.getExtras().get("WRONGANSWER1_KEY").toString();
            String wrongAnswer2 = data.getExtras().get("WRONGANSWER2_KEY").toString();
            flashcardQuestion.setText(question);
            flashcardAnswer.setText(answer);
            answerChoiceOne.setText(wrongAnswer1);
            answerChoiceTwo.setText(wrongAnswer2);
            answerChoiceThree.setText(answer);

            flashcardDatabase.insertCard(new Flashcard(question, answer, wrongAnswer1, wrongAnswer2));
            allFlashCards = flashcardDatabase.getAllCards();
        }
        else if (requestCode == 200 && resultCode == RESULT_OK)
        {
            String question = data.getExtras().get("QUESTION_KEY").toString();
            String answer = data.getExtras().get("RIGHTANSWER_KEY").toString();
            String wrongAnswer1 = data.getExtras().get("WRONGANSWER1_KEY").toString();
            String wrongAnswer2 = data.getExtras().get("WRONGANSWER2_KEY").toString();
            flashcardQuestion.setText(question);
            flashcardAnswer.setText(answer);
            answerChoiceOne.setText(wrongAnswer1);
            answerChoiceTwo.setText(wrongAnswer2);
            answerChoiceThree.setText(answer);

            cardToEdit.setQuestion(question);
            cardToEdit.setAnswer(answer);
            cardToEdit.setWrongAnswer1(wrongAnswer1);
            cardToEdit.setWrongAnswer2(wrongAnswer2);
            flashcardDatabase.updateCard(cardToEdit);
        }
        else
            return;
        currentCardDisplayedIndex++;
        Snackbar.make(flashcardQuestion,
                "Successfully saved card!",
                Snackbar.LENGTH_SHORT)
                .show();

    }
}