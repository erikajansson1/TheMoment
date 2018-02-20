package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ResultPageActivity extends AppCompatActivity implements ResultPageActivityCallback {
    Player clientPlayer;
    Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        findViewById(R.id.Quit).setEnabled(false);
        findViewById(R.id.NewRound).setEnabled(false);

        this.clientPlayer = (Player) getIntent().getSerializableExtra("playerData");
        this.currentRoom = (Room) getIntent().getSerializableExtra("roomData");

        clientPlayer.incrementRound();
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.declareRoundAnswered(clientPlayer,this);
        //callRoomUpdate();
    }

    /**
     * Callback function next in line after player declared himself done. Starts the chain of calls to see if round is complete.
     * @param reply from the server telling if call succeded
     */
    public void checkIfRoundIsFinished (String reply) {
        Log.e("reply: ",reply);
        //TODO guard for "failed" response
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.checkIfRoundComplete(currentRoom.getID(),clientPlayer.getRound(),this);
    }

    /**
     * Callback function which recieves response if round is done. Job is to intepret answer.
     * If done it calls server for a room update
     * If not done it repeats check if round is complete after 1.5 seconds of delay
     * @param result response from server if round is done
     */
    public void ifDoneCallRoomUpdate(String result) {
        Log.e("reply if done: ",result);
        final ResultPageActivity thisObject = this;
        if(Boolean.parseBoolean(result)) {
            ServerCommunication serverCom = new ServerCommunication(this);
            serverCom.updateResultRoom(currentRoom.getID(),this);
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("ifDoneCallRoom","FIRE NEW CALL");
                    ServerCommunication serverCom = new ServerCommunication(thisObject);
                    serverCom.checkIfRoundComplete(currentRoom.getID(),clientPlayer.getRound(),thisObject);
                }
            }, 1500);

        }
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
        this.currentRoom = updatedRoom;
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
            intent = new Intent(this, VoteOnClaim.class);
            intent.putExtra("claimData", currentRoom.getCurrentClaim());
        } else {
            intent = new Intent(this, WriteClaim.class);
        }
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }

    /**
     * if players decides to quit he or she presses the button quit and fires up this function.
     * Returns the user to the main menu, needs to be updated with a scrub method for player from rroom.
     * @param view another neat view
     */
    public void JumptoMainMenu(View view) {
        //TODO clean player from room at DB
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
