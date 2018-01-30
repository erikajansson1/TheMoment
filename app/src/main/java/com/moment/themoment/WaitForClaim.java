package com.moment.themoment;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import static java.lang.Boolean.FALSE;

public class WaitForClaim extends AppCompatActivity {
    TextView timeCount;
    ProgressBar claimProgress;
    private static final String FORMAT = "%02d";
    int seconds , minutes, numberOfPlayers, roomSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_claim);
        timeCount = findViewById(R.id.countDown);
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
    }

    private void jumptoWriteClaim() {
        //TODO Intent intent = new Intent(this, writeClaim.class);
        //startActivity(intent);
    }

    private void queryDBClaimReady() {
        //TODO query function with a callback to jumpToClaim?
    }

    private void jumpToClaim () {
        //TODO Intent intent = new Intent(this, writeClaim.class);
        //startActivity(intent);
    }
}
