package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class VoteOnClaim extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_on_claim);

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

    //TODO: Textsträngen ska läsa in claimen som den som fyller i claimen skrivit.
}
