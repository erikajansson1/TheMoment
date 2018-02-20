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

    public void checkIfRoundIsFinished (String reply) {
        Log.e("reply: ",reply);
        //TODO guard for "failed" response
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.checkIfRoundComplete(currentRoom.getID(),clientPlayer.getRound(),this);
    }

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

    public void updateResultList(Room updatedRoom) {
        findViewById(R.id.progressWait).setVisibility(View.GONE);
        findViewById(R.id.textViewWait).setVisibility(View.GONE);
        Collections.sort(updatedRoom.getPlayerList(), new PlayerComparator());
        for (Player player: updatedRoom.getPlayerList()) {
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

    private void addPlayerResult(Player player, Boolean clientCorrect) {
        //TODO will we need name ID again, change in this function
        TextView nameTV = createPlayerName(player.getName(), View.generateViewId());
        //TODO will we need score ID again, change in this function
        TextView scoreTV = createPlayerScore(player.getScore(), View.generateViewId(),clientCorrect);
        ((LinearLayout) findViewById(R.id.NameList)).addView(nameTV);
        ((LinearLayout) findViewById(R.id.ScoreList)).addView(scoreTV);
    }

    private TextView createPlayerName(String name, Integer id) {
        TextView playerNameTV = new TextView(this);
        playerNameTV.setText(String.valueOf(name));
        playerNameTV.setId(id);
        playerNameTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        playerNameTV.setGravity(Gravity.CENTER);
        return playerNameTV;
    }

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

    public void newRound(View view) {
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

    public void JumptoMainMenu(View view) {
        //TODO clean player from room at DB
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
