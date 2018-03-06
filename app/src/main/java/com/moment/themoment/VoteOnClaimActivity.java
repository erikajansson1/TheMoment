package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class VoteOnClaimActivity extends AppCompatActivity implements VoteOnClaimCallback {
    Player clientPlayer;
    Room currentRoom;
    Claim currentClaim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_on_claim);

        this.clientPlayer = (Player) getIntent().getSerializableExtra("playerData");
        this.currentRoom = (Room) getIntent().getSerializableExtra("roomData");
        this.currentClaim = (Claim) getIntent().getSerializableExtra("claimData");

        String message = currentClaim.getClaim();
        TextView ClaimToVote = findViewById(R.id.ClaimToVote);
        ClaimToVote.setText(message);
    }

    @Override
    public void onBackPressed() {

    }


    /**
     * Checks if a user has picked an answer, if one has, the answer will be compared to the correct answer.
     * If the answer is correct the players score will be updated.
     * @param view the view where the function call is made from
     */

    public void compareAnswer(View view) {

        RadioGroup stateGroup = findViewById(R.id.state);

        if (stateGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please pick a correct answer", Toast.LENGTH_SHORT).show();
        } else {
            int selectedId = stateGroup.getCheckedRadioButtonId();
            RadioButton selectedAnswButton = findViewById(selectedId);
            String answer = selectedAnswButton.getText().toString();
            Boolean boolansw = setBool(answer);
            clientPlayer.setAnswer(boolansw);
            if(boolansw.equals(currentClaim.getAnsw())) {
                int currScore = clientPlayer.getScore();
                //If correct answ. 5 points will be given.
                clientPlayer.updateScore(currScore + 5);
                int newScore = clientPlayer.getScore();
                ServerCommunication serverCom = new ServerCommunication(this);
                //TODO Potential bug if player is very quick and manages to update his score for the next round before other players have managed to update their rooms from server. i.e he will be a round ahead with his answer and points.
                serverCom.updateScore(clientPlayer.getID(), newScore, this);
            }
            else {
                goToResult();
            }

        }
     }

    /**
     * Function to send information and start the new activity
     */

    public void goToResult() {
        Intent intent = new Intent(this, ResultPageActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }


    /**
     * To convert a string to a Boolean
     * @param message the string to be converted
     * @return the converted boolean
     */

    private Boolean setBool(String message){
        return message.equals("True");
    }


}
