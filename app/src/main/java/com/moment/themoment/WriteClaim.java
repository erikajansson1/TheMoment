package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class WriteClaim extends AppCompatActivity {
    Player clientPlayer;
    Room currentRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_claim);
    }

    public void saveClaim(View view) {
        EditText claim = findViewById(R.id.theClaim);
        String message = claim.getText().toString();

        RadioGroup theGroup = findViewById(R.id.TrueFalseGroup);

        if(theGroup.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getApplicationContext(), "Please pick a correct answer", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int selectedId = theGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = (RadioButton) findViewById(selectedId);
            String answer = selectedRadioButton.getText().toString();
            Boolean boolansw = setBool(answer);
            clientPlayer.setClaim(new Claim(message, boolansw));
            sendToServer(clientPlayer);
            goToVoteOnClaim(view);
        }

        //TODO: goToVoteOnClaim should link to the, by server, chosen claim to answer at, randomly picked out of all players claims, all players should get the claims in the same order.
        //TODO: Fix saveClaimAndAnswer


    }


    private void sendToServer(Player clientPlayer){
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.saveClaimAndAnswer(clientPlayer);
    }

    private void goToVoteOnClaim(View view) {
        Intent intent = new Intent(this, VoteOnClaim.class);
        startActivity(intent);
    }
    private Boolean setBool(String message){
        if (message.equals("true")){
            return true;
        }
        else {return false; }
    }
}


