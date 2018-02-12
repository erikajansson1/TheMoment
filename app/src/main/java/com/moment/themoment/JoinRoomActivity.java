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

        //TODO Query server for room existence and if possible add client to ROOM. If succesfull do a callback to jumpToWaitForClaim
        // TODO if failure, handle failure, error message? response from server error message maybe?

        jumpToWaitForClaim(view);
    }

    private void jumpToWaitForClaim(View view) {
        Intent intent = new Intent(this, WaitForClaim.class);
        startActivity(intent);
    }

    public void joinRoom(){
        ServerCommunication server = new ServerCommunication();
        //server.JoinRoomInServer(player, ID);
        //TODO take in the arguments
        //TODO get back room
        return;
    }
}
