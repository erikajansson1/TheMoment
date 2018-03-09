package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
       // Log.e("answer ===",this.clientPlayer.toString());
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * Checking whether a user has wrote a claim and chose a correct answer or not
     * When claim and answer is picked it checks if the player has created a claim before, if not it creates a new claim attached to the player and saves it and calls a send to server function.
     * If the player already has a claim that claim will be updated with the new one, then a send to server method is called to update the DB.
     * @param view the view where the function call is made from
     */

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

    /**
     * Creates a server communication and sends the claim that is created together with the player that belongs to the claim, to a function that makes the actual server call
     * @param claim the claim that is to be sent to the server
     */

    private void sendNewClaimToServer(Claim claim){
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.newClaimAndAnswer(claim, this.clientPlayer, this);
    }


    /**
     * Creates a server communication and sends the claim that is to be updated to a function that makes the actual server call
     * @param claim the claim that is to be sent to the server
     */

    private void sendUpdateToServer(Claim claim){
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.updateClaimAndAnswer(claim, this);
    }

    /**
     * calls a server update declaring player now has
     * given new claim and updates round counter to signal it
     */
    public void updatePlayerRound(int id) {
        clientPlayer.getClaim().setID(id);
        clientPlayer.incrementRound();
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.declareClaimWritten(clientPlayer,this);
    }


    /**
     * To convert a string to a Boolean
     * @param message the string to be converted
     * @return the converted boolean
     */

    private Boolean setBool(String message){
        return message.equals("True");
    }


    /**
     * Function to send information and start the new activity
     */
    public void goToWaitForClaim(){
        Intent intent = new Intent(this, WaitForClaimActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }
}