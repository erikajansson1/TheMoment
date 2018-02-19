package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class VoteOnClaim extends AppCompatActivity implements VoteOnClaimCallback {
    Player clientPlayer;
    Room currentRoom;
    Claim currentClaim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_on_claim);

        this.clientPlayer = (Player) getIntent().getSerializableExtra("PlayerName");
        this.currentRoom = (Room) getIntent().getSerializableExtra("roomData");
        this.currentClaim = (Claim) getIntent().getSerializableExtra("claimData");

        String message = currentClaim.getClaim();
        TextView ClaimToVote = findViewById(R.id.ClaimToVote);
        ClaimToVote.setText(message);
    }


    public void compareAnswer(View view) {

        RadioGroup stateGroup = findViewById(R.id.state);

        if (stateGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getApplicationContext(), "Please pick a correct answer", Toast.LENGTH_SHORT).show();
        } else {
            int selectedId = stateGroup.getCheckedRadioButtonId();
            RadioButton selectedAnswButton = (RadioButton) findViewById(selectedId);
            String answer = selectedAnswButton.getText().toString();
            Boolean boolansw = setBool(answer);
            clientPlayer.setAnswer(boolansw);
            if(boolansw == currentClaim.getAnsw()) {
                int currScore = clientPlayer.getScore();
                //If correct answ. 5 points will be given.
                clientPlayer.updateScore((currScore + 5));
                ServerCommunication serverCom = new ServerCommunication(this);
                serverCom.updateScore(clientPlayer, this);
            }
            else {
                goToResult();
            }

        }
     }

    public void goToResult() {
        Intent intent = new Intent(this, ResultPageActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }

    private Boolean setBool(String message){
        if (message.equals("true")){
            return true;
        }
        else {return false; }
    }


}
