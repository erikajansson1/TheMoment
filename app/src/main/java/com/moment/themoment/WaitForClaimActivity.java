package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;


/**
 * We check is everybody has answered a claim by checking if all our players are on the same round,
 * After this we should update the room and grab the current claim from the claims in the room.
 * The claim is determined on the player list in the room, so it starts on 0.
 */
public class WaitForClaimActivity extends AppCompatActivity implements WaitForClaimCallback{
    TextView timeCount;
    private static final String FORMAT = "%d:%02d";
    Player clientPlayer;
    Room currentRoom;
    Claim currentClaim;

    ProgressBar claimProgress;
    Boolean isClaimsDone;
    Long seconds , minutes, numberOfPlayers, roomSize;

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

        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.imInTheGame(this.currentRoom.getID(),this.clientPlayer.getID(), this);
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * Gets if client is still in the game and reacts accordingly.
     * If still in game starts the wait timer for the other players.
     * @param reply boolean telling if player been kicked or not
     */
    public void stillInTheGame(Boolean reply) {
       // Log.e("log:","im in the game");
        if(reply) {
            this.startTimer();
        } else {
            this.exitGame();
        }
    }

    /**
     * if players decides to quit he or she presses the button quit and fires up this function.
     * Returns the user to the main menu, needs to be updated with a scrub method for player from rroom.
     */
    public void exitGame() {
        //TODO potential bug! if player exits, claim count will be of in cases
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.removePlayerFromDb(currentRoom.getID(),clientPlayer.getID(),this);
    }

    /**
     * Callback function which checks if server succeded in removing player and if not retries, otherwise it goes to main menu
     * @param output string that contains a boolean
     */
    public void JumptoMainMenu(String output) {
        if(output != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            ServerCommunication serverCom = new ServerCommunication(this);
            serverCom.removePlayerFromDb(currentRoom.getID(),clientPlayer.getID(),this);
        }
    }


    // TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
     //                           TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
    //"" + String.format(FORMAT, millisUntilFinished/1000)
    /**
     * Starts a timer waiting for other players to finish their claim writing.
     * If people timer reaches end client starts kicking idling players
     */
    private void startTimer(){
        final WaitForClaimActivity thisObject = this;
        new CountDownTimer(90000, 1000) {
            public void onTick(long millisUntilFinished) {
                minutes = (millisUntilFinished / (60 * 1000));
                seconds = (millisUntilFinished / 1000) % 60;
                timeCount.setText( String.format(FORMAT, minutes, seconds));


                claimProgress.setProgress(claimProgress.getProgress() + 1);
                if (isClaimsDone) {
                    cancel();
                    thisObject.getUpdatedClaimsRoom();
                } else {
                    askServer();
                }
            }

            public void onFinish() {
                ServerCommunication serverCom = new ServerCommunication(thisObject);
                serverCom.removeStragglers(currentRoom.getID(),clientPlayer.getRound(),thisObject);
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
    public void updateWaitForClaim(Boolean response){
        if (response) {
            this.isClaimsDone = true;
        }
    }

    /**
     * Ask server to update the Room
     */
    public void getUpdatedClaimsRoom() {
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.updateClaimsRoom(this.currentRoom.getID(), this);
    }

    /**
     * Updates the room from the response of the server. Then grabs first current claim
     * that will then be sent to voteOnClaim.
     * @param room
     */
    public void updateRoom(Room room) {
        int claimNo = currentRoom.getCurrentClaimNo();
        this.currentRoom = room;
        this.currentRoom.setCurrentClaimNo(claimNo);
        this.currentRoom.replaceCurrPlayer(this.clientPlayer);
        this.currentClaim = this.currentRoom.getCurrentClaim();
        Integer claimID =  this.currentClaim.getID();
        Integer playerClaimID = this.clientPlayer.getClaim().getID();
        if (claimID.intValue() == playerClaimID.intValue()) {
            //Log.e("sendingTO:","TORESULT");
            this.jumpToResult();
            return;
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
