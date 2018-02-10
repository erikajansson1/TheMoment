package com.moment.themoment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class WaitForPlayersActivity extends AppCompatActivity {
    TextView timeCount, playerCount;
    private static final String FORMAT = "%02d";
    int seconds , minutes, numberOfPlayers, roomSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_players);

        timeCount= findViewById(R.id.countDown);
        playerCount= findViewById(R.id.numberOfPlayersJoined);
        numberOfPlayers = 0;
        roomSize = 10;
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeCount.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                //TODO query server for number of joined players and save into numberOfPlayers, if querys are to intense or callback missmatch increase to maybe every other second.
                numberOfPlayers = numberOfPlayers + 1;
                playerCount.setText(""+numberOfPlayers);
                if (numberOfPlayers >= roomSize) {
                    cancel();
                    jumpToWaitForClaim();
                }

            }
            public void onFinish() {
                timeCount.setText("Starting!");
                //TODO: When if statement above works i.e. one can read from server how many player has connected and if the room is full the game starts, the out commentation can be removed.
              //  jumpToWaitForClaim();
            }
        }.start();
    }

    private void jumpToWaitForClaim() {
        Intent intent = new Intent(this, WriteClaim.class);
        startActivity(intent);
    }

}
