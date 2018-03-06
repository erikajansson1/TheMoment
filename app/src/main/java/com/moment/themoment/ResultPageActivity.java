package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ResultPageActivity extends AppCompatActivity implements ResultPageCallback {
    Player clientPlayer;
    Room currentRoom;
    Boolean activityStopped;
    Boolean roundComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        findViewById(R.id.Quit).setEnabled(false);
        findViewById(R.id.NewRound).setEnabled(false);

        this.clientPlayer = (Player) getIntent().getSerializableExtra("playerData");
        this.currentRoom = (Room) getIntent().getSerializableExtra("roomData");
        this.activityStopped = false;
        this.roundComplete = false;

        if (getIntent().getSerializableExtra("myClaim") != null) {
            this.myClaimIsNow();
        } else {
            clientPlayer.incrementRound();
            ServerCommunication serverCom = new ServerCommunication(this);
            serverCom.declareRoundAnswered(clientPlayer,this);
        }



    }

    @Override
    public void onBackPressed() {

    }

    /**
     * Callback function next in line after player declared himself done. Starts the chain of calls to see if round is complete.
     * @param reply from the server telling if call succeed
     */
    public void checkIfRoundIsFinished(String reply) {
        final ResultPageActivity thisObject = this;
        new CountDownTimer(90000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (roundComplete) {
                    cancel();
                    thisObject.callForRoomUpdate();
                } else if (!activityStopped) {
                    Log.e("ifDoneCallRoom","FIRE NEW CALL");
                    ServerCommunication serverCom = new ServerCommunication(thisObject);
                    serverCom.checkIfRoundComplete(currentRoom.getID(),clientPlayer.getRound(),thisObject);
                }
            }
            public void onFinish() {
                ServerCommunication serverCom = new ServerCommunication(thisObject);
                serverCom.removeStragglers(currentRoom.getID(),clientPlayer.getRound(),thisObject);
            }
        }.start();
    }

    /**
     * Calls server for a update of room object
     */
    public void callForRoomUpdate() {
        //TODO implement if removeStragglers fails
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.updateResultRoom(this.currentRoom.getID(),this);
    }

    /**
     * set method for roundComplete
     * @param output containing 1 if true and "" if false
     */
    public void setRoundComplete(String output) {
        if(!output.equals("")) {
            this.roundComplete = true;
        }
    }

    /**
     * The extremely cheapo way to stop the threads actions if looping
     */
    @Override
    protected void onPause(){
        super.onPause();
        this.activityStopped = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.activityStopped = false;
    }

    /**
     * Callback function which processes the now updated room from the server and display it for the client
     * @param updatedRoom from server
     */
    public void updateResultList(Room updatedRoom) {
        findViewById(R.id.progressWait).setVisibility(View.GONE);
        findViewById(R.id.textViewWait).setVisibility(View.GONE);
        ArrayList<Player> playerListClone = new ArrayList(updatedRoom.getPlayerList());
        Collections.sort(playerListClone, new PlayerComparator());
        for (Player player: playerListClone) {
            if(player.getIsPlayer() && player.answeredCorrect()) {
                addPlayerResult(player,true);
            } else {
                addPlayerResult(player,false);
            }
        }
        int claimCount = this.currentRoom.getCurrentClaimNo();
        this.currentRoom = updatedRoom;
        this.currentRoom.setCurrentClaimNo(claimCount);
        this.currentRoom.replaceCurrPlayer(this.clientPlayer);
        findViewById(R.id.Quit).setEnabled(true);
        findViewById(R.id.NewRound).setEnabled(true);
    }

    /**
     * prepares and adds the textviews for both player name and score
     * @param player is one to be showed
     * @param clientCorrect a flag to show if client answered correct... and which one is client
     */
    private void addPlayerResult(Player player, Boolean clientCorrect) {
        //TODO will we need name ID again, change in this function
        TextView nameTV = createPlayerName(player.getName(), View.generateViewId());
        //TODO will we need score ID again, change in this function
        TextView scoreTV = createPlayerScore(player.getScore(), View.generateViewId(),clientCorrect);
        ((LinearLayout) findViewById(R.id.NameList)).addView(nameTV);
        ((LinearLayout) findViewById(R.id.ScoreList)).addView(scoreTV);
    }

    /**
     * creates a textview containing players name.
     * @param name to be displayed
     * @param id to assign to textview
     * @return a nice textview
     */
    private TextView createPlayerName(String name, Integer id) {
        TextView playerNameTV = new TextView(this);
        playerNameTV.setText(String.valueOf(name));
        playerNameTV.setId(id);
        playerNameTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        playerNameTV.setGravity(Gravity.CENTER);
        return playerNameTV;
    }

    /**
     * Creates a textview containg a score given.
     * @param score int value representing score
     * @param id to assign to textview
     * @param clientCorrect if its client and client answered correct this will be true.
     * @return a nice textview
     */
    private TextView createPlayerScore(Integer score, Integer id, Boolean clientCorrect) {
        TextView playerScoreTV = new TextView(this);
        if(clientCorrect) {
            playerScoreTV.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.arrowup, 0);
        } else {
            playerScoreTV.setCompoundDrawablesWithIntrinsicBounds(0, 0,R.drawable.invisiblearrow, 0);
        }

        playerScoreTV.setText(String.valueOf(score+" pts"));
        playerScoreTV.setId(id);
        playerScoreTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        playerScoreTV.setGravity(Gravity.CENTER);
        return playerScoreTV;
    }

    /**
     * called when client presses button new round.
     * checks if all claims have been said, if so sends client to write a new claim.
     * if claims are left, player is send to answer a new one.
     * if claim to be presented is players, he will be locked here while waiting for players to respond, ie he will be looped insided class.
     * @param view a nice view!
     */
    public void newRound(View view) {
        //TODO if its ClientPlayers claim next user will be blocked here!

        Intent intent;
        if (currentRoom.setNextClaim()) {
            if (clientPlayer.claimIsClients(currentRoom.getCurrentClaim().getID())) {
                this.myClaimIsNow();
                return;
            }
            intent = new Intent(this, VoteOnClaimActivity.class);
            intent.putExtra("claimData", currentRoom.getCurrentClaim());
        } else {
            intent = new Intent(this, WriteClaimActivity.class);
        }
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }

    /**
     * method which loops the player if its his claim being voted on.
     */
    private void myClaimIsNow() {
        TextView waitText = findViewById(R.id.textViewWait);
        waitText.setText(R.string.myClaim);
        waitText.setVisibility(View.VISIBLE);

        findViewById(R.id.progressWait).setVisibility(View.VISIBLE);

        ((LinearLayout) findViewById(R.id.NameList)).removeAllViews();
        ((LinearLayout) findViewById(R.id.ScoreList)).removeAllViews();

        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.declareRoundAnswered(clientPlayer,this);

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

    /**
     * if players decides to quit he or she presses the button quit and fires up this function.
     * Returns the user to the main menu, needs to be updated with a scrub method for player from rroom.
     */
    public void exitGame(View view) {
        //TODO potential bug! if player exits, claim count will be of in cases
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.removePlayerFromDb(currentRoom.getID(),clientPlayer.getID(),this);
    }
}
