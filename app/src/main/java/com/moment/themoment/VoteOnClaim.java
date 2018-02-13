package com.moment.themoment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import static android.media.MediaExtractor.MetricsConstants.FORMAT;

public class VoteOnClaim extends AppCompatActivity {
   // TextView Counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_on_claim);

     /*   Counter = findViewById(R.id.Counter);
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                Counter.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                //TODO query server for number of joined players and save into numberOfPlayers, if querys are to intense or callback missmatch increase to maybe every other second.
                goToCorrectAnswer();

                */
             /*   if (numberOfPlayers >= roomSize) {
                    cancel();
                    jumpToWaitForClaim();
                } */
        /*    }
            public void onFinish() {
                Counter.setText("Starting!");
                //TODO: When if statement above works i.e. one can read from server how many player has connected and if the room is full the game starts, the out commentation can be removed.
                //  jumpToWaitForClaim();
            }
        }.start();
*/

        //TODO: Textsträngen ska läsa in claimen som den som fyller i claimen skrivit.

        // Get the Intent that started this activity and extract the string
      /*
      Intent intent = getIntent();
      String message = intent.getStringExtra(WriteClaim.EXTRA_MESSAGE);
       */

        // Capture the layout's TextView and set the string as its text
      /*
      TextView textView = findViewById(R.id.ClaimToVote);
        textView.setText(message);
        */
    }
    public void saveAnswer(View view) {

        RadioGroup stateGroup = findViewById(R.id.state);

        if (stateGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please pick a correct answer", Toast.LENGTH_SHORT).show();
        } else {
            int selectedId = stateGroup.getCheckedRadioButtonId();
            RadioButton selectedAnswButton = (RadioButton) findViewById(selectedId);
            //TODO: Send selectedAnswButton to server as picked answer.
            goToCorrectAnswer();
        }
     }

    private void goToCorrectAnswer() {
        Intent intent = new Intent(this, CorrectAnswer.class);
        startActivity(intent);
    }


}
