package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

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

        callRoomUpdate();
    }

    private void callRoomUpdate() {
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.updateResultRoom(currentRoom.getID(),this);
    }

    public void updateResultList(Room updatedRoom) {
        Gson g = new Gson();
        Collections.sort(updatedRoom.getPlayerList(), new PlayerComparator());
        for (Player player: updatedRoom.getPlayerList()) {
            addPlayerResult(player);
        }
        this.currentRoom = updatedRoom;
        this.currentRoom.replaceCurrPlayer(this.clientPlayer);
        findViewById(R.id.Quit).setEnabled(true);
        findViewById(R.id.NewRound).setEnabled(true);
    }

    private void addPlayerResult(Player player) {
        //TODO will we need name ID again, change in this function
        TextView nameTV = createPlayerName(player.getName(), View.generateViewId());
        //TODO will we need score ID again, change in this function
        TextView scoreTV = createPlayerScore(player.getScore(), View.generateViewId());
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

    private TextView createPlayerScore(Integer score, Integer id) {
        TextView playerScoreTV = new TextView(this);
        playerScoreTV.setText(String.valueOf(score+" pts"));
        playerScoreTV.setId(id);
        playerScoreTV.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));
        playerScoreTV.setGravity(Gravity.CENTER);
        return playerScoreTV;
    }

    public void JumptoWriteClaim(View view) {
        Intent intent = new Intent(this, WriteClaim.class);
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
