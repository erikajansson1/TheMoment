package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static java.lang.Boolean.FALSE;

public class JoinRandomRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_random_room);
        //TODO Add loading symbol
        //TODO Make server call for random room

        //TODO Handle failure by reacting on error message and kick user back to main menu
        Boolean foundRoom = FALSE;

        if (foundRoom) {
            jumpToWaitForClaim();
        }
        else {
            finish();
        }
    }

    private void jumpToWaitForClaim() {
        //TODO Intent intent = new Intent(this, WaitForClaim.class);
        //startActivity(intent);
    }
}
