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
        //TODO Query server for room existence and if possible add client to Room. If succesfull return true, do a callback to jumpToWaitForClaim
        if (joinedSuccesfully) {
            jumpToWaitForClaim(view);
        }
        else {
            //TODO if failure, handle failure, error message? response from server error message maybe?
        }
        jumpToWaitForClaim(view);
    }
    private void jumpToWaitForClaim(View view) {
        Intent intent = new Intent(this, waitForClaim.class);
        startActivity(intent);
    }
}
