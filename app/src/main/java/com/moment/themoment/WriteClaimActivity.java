package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class WriteClaimActivity extends AppCompatActivity implements WriteClaimCallback {
    Player clientPlayer;
    Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_claim);

        this.clientPlayer = (Player) getIntent().getSerializableExtra("playerData");
        this.currentRoom = (Room) getIntent().getSerializableExtra("roomData");
        Log.e("answer ===",this.clientPlayer.toString());
    }

    public void saveClaim(View view) {
        EditText claim = findViewById(R.id.theClaim);
        String message = claim.getText().toString();

        RadioGroup theGroup = findViewById(R.id.TrueFalseGroup);

        if(theGroup.getCheckedRadioButtonId()==-1)
        {
            Toast.makeText(getApplicationContext(), "Please pick a correct answer", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(message)){
            Toast.makeText(getApplicationContext(), "Please write a claim", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int selectedId = theGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);
            String answer = selectedRadioButton.getText().toString();
            Boolean boolansw = setBool(answer);
            if (this.clientPlayer.getClaim() == null) {
                this.clientPlayer.setClaim(new Claim(message, boolansw));
                Claim theClaim = this.clientPlayer.getClaim();
                //create new claim in DB
                //TODO: Guard for server failure
                sendNewClaimToServer(theClaim);
            }
            else {
                Claim newClaim = clientPlayer.getClaim();
                newClaim.setClaim(message);
                newClaim.setCorrectAnswer(boolansw);
                // TODO: Guard for server failure
                sendUpdateToServer(newClaim);
            }
        }
    }

    private void sendNewClaimToServer(Claim claim){
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.newClaimAndAnswer(claim, this.clientPlayer, this);
    }

    private void sendUpdateToServer(Claim claim){
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.updateClaimAndAnswer(claim, this);
    }

    /**
     * calls a server update declaring player now has
     * given new claim and updates round counter to signal it
     */
    public void updatePlayerRound() {
        clientPlayer.incrementRound();
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.declareClaimWritten(clientPlayer,this);
    }

    private Boolean setBool(String message){
        return message.equals("True");
    }


    public void goToWaitForClaim(){
        Intent intent = new Intent(this, WaitForClaimActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }
}