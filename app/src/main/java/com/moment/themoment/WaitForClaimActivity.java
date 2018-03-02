package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;


/**
 * Wait for claim should work on Two different occasion:
 * 1. In the beginning men all players have entered the room and each should write a claim
 * For this we need to check if all players have answered, if they have we continue to the
 * voteOnClaim. With this we should send a list of what order the claims should be displayed
 * 2. -
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

        timeCount = findViewById(R.id.timeCount);
        claimProgress = findViewById(R.id.progress);
        isClaimsDone = false;

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
                    getCurrentClaim();
                } else {
                    askServer();
                }
            }

            public void onFinish() {
                timeCount.setText("Starting!");
                getCurrentClaim();
            }
        }.start();

    }

    /**
     * Ask server to check if all players have entered a claim
     */
    private void askServer(){
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.isClaimsDone(this.currentRoom.getID(), this);
    }

    /**
     * Recieves answer if everyone has entered a claim or not
     * @param response
     */
    public void updateWaitForClaim(String response){
        if (response != null) {
            this.isClaimsDone = Boolean.valueOf(response);
        }
    }

    /**
     * Ask server to get the current claim for the game
     */
    private void getCurrentClaim(){
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.getCurrentClaim(this.currentRoom.getID(), this);

    }

    /**
     * Recieves the current claim for the game
     * @param claim
     */
    public void updateCurrentClaim(Claim claim){
        this.currentClaim = claim;
        this.currentRoom.setCurrentClaimNo(claim.getID());
        jumpToVoteOnClaim();
    }

    private void jumpToVoteOnClaim () {
        Intent intent = new Intent(this, VoteOnClaimActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        intent.putExtra("claimData", currentClaim);
        startActivity(intent);
    }
}
