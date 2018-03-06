package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


/**
 * We check is everybody has answered a claim by checking if all our players are on the same round,
 * After this we should update the room and grab the current claim from the claims in the room.
 * The claim is determined on the player list in the room, so it starts on 0.
 */
public class WaitForClaimActivity extends AppCompatActivity implements WaitForClaimCallback{
    TextView timeCount;
    private static final String FORMAT = "%02d";
    Player clientPlayer;
    Room currentRoom;
    Claim currentClaim;

    ProgressBar claimProgress;
    Boolean isClaimsDone;
    int seconds , minutes, numberOfPlayers, roomSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_claim);

        this.clientPlayer = (Player) getIntent().getSerializableExtra("playerData");
        this.currentRoom = (Room) getIntent().getSerializableExtra("roomData");

        TextView roomNumber = findViewById(R.id.roomNumberWaitFClaim);
        roomNumber.setText(String.valueOf(this.currentRoom.getID()));

        this.timeCount = findViewById(R.id.timeCountProgress);
        this.claimProgress = findViewById(R.id.progress);
        this.isClaimsDone = false;
        startTimer();

    }

    @Override
    public void onBackPressed() {

    }

    private void startTimer(){
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeCount.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                claimProgress.setProgress(claimProgress.getProgress() + 1);
                if (isClaimsDone) {
                    cancel();
                    getUpdatedClaimsRoom();
                } else {
                    askServer();
                }
            }

            public void onFinish() {
                getUpdatedClaimsRoom();
            }
        }.start();

    }

    /**
     * Ask server to check if all players have entered a claim
     */
    private void askServer(){
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.isClaimsDone(this.currentRoom.getID(), this.clientPlayer.getRound() , this);
    }

    /**
     * Recieves answer if everyone has gotten to the same round
     * @param response
     */
    public void updateWaitForClaim(String response){
        if (response.equals("")) {
            this.isClaimsDone = true;
        }
    }

    /**
     * Ask server to update the Room
     */
    private void getUpdatedClaimsRoom() {
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.updateClaimsRoom(this.currentRoom.getID(), this);
    }

    /**
     * Updates the room from the response of the server. Then grabs first current claim
     * that will then be sent to voteOnClaim.
     * @param room
     */
    public void updateRoom(Room room) {
        this.currentRoom = room;
        this.currentRoom.replaceCurrPlayer(this.clientPlayer);
        this.currentClaim = this.currentRoom.getCurrentClaim();
        if (this.currentClaim.getID() == clientPlayer.getClaim().getID()) {
            Boolean notAlone = this.currentRoom.setNextClaim();
            if(!notAlone) {
                this.jumpToResult();
                return;
            } else {
                this.currentClaim = this.currentRoom.getCurrentClaim();
            }
        }
        this.jumpToVoteOnClaim();
    }

    /**
     * Jumps to voteOnClaim and with that sends forward the player, currentRoom and currentClaim
     */
    private void jumpToVoteOnClaim () {
        Intent intent = new Intent(this, VoteOnClaimActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        intent.putExtra("claimData", currentClaim);
        startActivity(intent);
    }

    /**
     * Jumps to ResultPageActivity and with that sends forward the player, currentRoom and currentClaim
     */
    private void jumpToResult () {
        Intent intent = new Intent(this, ResultPageActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        intent.putExtra("myClaim", true);
        startActivity(intent);
    }
}
