package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class WaitForPlayersActivity extends AppCompatActivity implements WaitForPlayersCallback {
    TextView timeCount, playerCount;
    private static final String FORMAT = "%02d";
    Player clientPlayer;
    Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_players);

        timeCount = findViewById(R.id.timeCountProgress);
        playerCount = findViewById(R.id.numberOfPlayersJoined);

        this.clientPlayer = (Player) getIntent().getSerializableExtra("playerData");
        this.currentRoom = (Room) getIntent().getSerializableExtra("roomData");
        startTimer();

    }

    @Override
    public void onBackPressed() {

    }

    private void startTimer() {
    Log.e("NUMOFPLAYERS", String.valueOf(currentRoom.getNumOfPlayers()));
    Log.e("ROOOOOOM", String.valueOf(currentRoom.getID()));
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeCount.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                playerCount.setText("" + currentRoom.getAmountOfPlayers());
                if (currentRoom.getAmountOfPlayers() >= currentRoom.getNumOfPlayers()) {
                    cancel();
                    jumpToWriteClaim();
                } else {
                    toServer(currentRoom.getID());
                }

            }

            public void onFinish() {
                timeCount.setText("Starting!");
                jumpToWriteClaim();
            }
        }.start();
    }


    private void jumpToWriteClaim() {
        Intent intent = new Intent(this, WriteClaimActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }

    private void toServer(int currentRoomID) {
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.countPlayers(currentRoomID, this);
    }

    public void updateNbrOfPlayers(Room roomFromServer) {
        //Log.i("fromServer", String.valueOf(roomFromServer.getID()));
        //Log.i("NbrFromServer", String.valueOf(roomFromServer.getAmountOfPlayers()));

        this.currentRoom = roomFromServer;
    }
}