package com.moment.themoment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class JoinRandomRoomActivity extends AppCompatActivity implements JoinRandomRoomCallback, View.OnClickListener {
    Player clientPlayer;
    Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_random_room);
        findViewById(R.id.joinButton).setEnabled(false);

        Button saveUserButton = findViewById(R.id.saveUsername);
        saveUserButton.setOnClickListener(this);
        //TODO Make server call for random room
    }

    @Override
    public void onClick(final View v) {
        switch(v.getId()){
            case R.id.saveUsername:
                    addUsername();
                    break;
            }

    }


    public void setClientPlayerID(int id) {
        clientPlayer.setID(id);
        Toast.makeText(this, "Saved username", Toast.LENGTH_SHORT).show();
        Log.i("Server gave:",String.valueOf(id));
        //TODO call server for room, once we have player id confirmed
    }

    private void addUsername() {
        findViewById(R.id.saveUsername).setEnabled(false);
        EditText userNameToSave = findViewById(R.id.NameInput);
        //TODO gurad for empty input
        Player clientPlayer = new Player(userNameToSave.getText().toString());
        this.clientPlayer = clientPlayer;
        ServerCommunication serverCom = new ServerCommunication(this);
        Log.e("name:",userNameToSave.getText().toString());
        serverCom.savePlayerToDB(clientPlayer,this);
    }

    private void jumpToWaitForClaim() {
        //TODO Intent intent = new Intent(this, WaitForClaim.class);
        //startActivity(intent);
    }


}
