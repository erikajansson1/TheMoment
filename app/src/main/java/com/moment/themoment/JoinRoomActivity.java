package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static java.lang.Boolean.FALSE;

public class JoinRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);
    }

    public void confirmRoomSelect(View view) {
        EditText roomNumber = findViewById(R.id.roomNumberInput);
        String roomNumberString = roomNumber.getText().toString();

        EditText userName = findViewById(R.id.userNameInput);
        String userNameString = userName.getText().toString();

        Boolean joinedSuccesfully = FALSE;
        Boolean myTurn = FALSE;
        //TODO Query server for room existence and if possible add client to Room. If succesfull return true
        //TODO check if user is to be prompted for input or awaits other users input, true or false

        if (joinedSuccesfully && myTurn) {
            jumpToWriteClaim(view);
        }
        else if (joinedSuccesfully && !myTurn) {
            jumpToWaitForClaim(view);
        }
        else {
            //TODO if failure, handle failure, error message? response from server error message maybe?
        }


    }

    private void jumpToWaitForClaim(View view) {
        //TODO Intent intent = new Intent(this, waitForClaim.class);
        //startActivity(intent);
    }

    private void jumpToWriteClaim (View view) {
        //TODO Intent intent = new Intent(this, writeClaim.class);
        //startActivity(intent);
    }
}
