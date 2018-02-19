package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Boolean.FALSE;

public class JoinRoomActivity extends AppCompatActivity implements JoinRoomCallback{
    private Player clientPlayer;
    private Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);
    }

    /**
     * sets the room for the player
     * @param room which is to be used by the player
     */
    public void setPlayersRoom(Room room) {
        Log.e("test",String.valueOf(room.getNumOfPlayers()));
        this.currentRoom = room;
        this.currentRoom.replaceCurrPlayer(this.clientPlayer);
        jumpToWaitForClaim();

    }

    /**
     * sets the client players id
     * @param id is the new id of the player
     */
    public void setClientPlayerID(int id) {
        clientPlayer.setID(id);
        Toast.makeText(this, "Saved username", Toast.LENGTH_SHORT).show();
        Log.i("Server gave:",String.valueOf(id));
        EditText roomNumberString = findViewById(R.id.roomNumberInput);

        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.joinRoom(Integer.parseInt(roomNumberString.getText().toString()), this);
    }


    public void confirmRoomSelect(View view) {
        //TODO: Set the button in offline mode so one can not create many players.
        EditText userName = findViewById(R.id.userNameInput);
        Player clientPlayer = new Player(userName.getText().toString());
        this.clientPlayer = clientPlayer;
        //TODO add guard for empty input
        ServerCommunication serverCom = new ServerCommunication(this);
        Log.e("name:", userName.getText().toString());
        serverCom.savePlayerToDBJoinRoom(clientPlayer,this);
        //TODO Query server for room existence and if possible add client to ROOM. If successful do a callback to jumpToWaitForClaim
        // TODO if failure, handle failure, error message? response from server error message maybe?
    }

    private void jumpToWaitForClaim() {
        //TODO depending on if the room is full or not should go to either waitForPlayers or writeClaim
        Intent intent = new Intent(this, WaitForPlayersActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }
}
