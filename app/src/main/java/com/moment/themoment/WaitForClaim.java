package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class WaitForClaim extends AppCompatActivity {
    Player clientPlayer;
    Room currentRoom;
    TextView timeCount;
    ProgressBar claimProgress;
    private static final String FORMAT = "%02d";
    int seconds , minutes, numberOfPlayers, roomSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_claim);

        //To get the object from previous activity, must be sendt!
        this.clientPlayer = (Player) getIntent().getSerializableExtra("playerData");
        this.currentRoom = (Room) getIntent().getSerializableExtra("roomData");

        timeCount = findViewById(R.id.timeCount);
        claimProgress = findViewById(R.id.progress);
        //TODO queryDBWriteClaim();
        new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                timeCount.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

                claimProgress.setProgress(claimProgress.getProgress()+1);
            }
            public void onFinish() {
                //TODO if countdown reaches zero probably call server for another check on whom are to writeclaim
                timeCount.setText("Starting!");
            }
        }.start();

    }

    private void queryDBWriteClaim() {
        //TODO query db for if player is tasked to write claim. callback to queryDBClaimReady if not otherwise jumptoWriteClaim
       // new CallServer(packager(Claim),"storeToDB","storeClaim",this).execute();


    }

    private void jumptoWriteClaim() {
        Intent intent = new Intent(this, WriteClaim.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }



    private void queryDBClaimReady() {
        //TODO query function with a callback to jumpToClaim?

        serverCom.savePlayerToDB(clientPlayer,this);


    }



    private void jumpToClaim () {
        Intent intent = new Intent(this, Claim.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
        //skicka med det aktuella claimet, rummet och sidorna?

    }
}
