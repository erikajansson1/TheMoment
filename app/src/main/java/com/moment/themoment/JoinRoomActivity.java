package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JoinRoomActivity extends AppCompatActivity implements JoinRoomCallback{
    private Player clientPlayer;
    private Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_room);
    }

    @Override
    public void onBackPressed() {

    }

    /**
     * sets the room for the player
     * @param room which is to be used by the player
     */
    public void setPlayersRoom(Room room) {
        if (room == null) {
            TextView execfail = findViewById(R.id.execfail);
            execfail.setText(R.string.notLegitRoomToJoin);
            ServerCommunication serverCom = new ServerCommunication(this);
            serverCom.removePlayerFromDb(clientPlayer.getID(),this);
        }else{
        Log.e("test", String.valueOf(room.getNumOfPlayers()));
        this.currentRoom = room;
        this.currentRoom.replaceCurrPlayer(this.clientPlayer);
        jumpToWaitForClaim();
    }
    }

    /**
     * sets the client players id
     * @param id is the new id of the player
     */
    public void setClientPlayerID(int id) {
        this.clientPlayer.setID(id);
        //Toast.makeText(this, "Saved username", Toast.LENGTH_SHORT).show();
        Log.i("Server gave:",String.valueOf(id));
        EditText roomNumberString = findViewById(R.id.roomNumberInput);

        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.joinRoom(Integer.parseInt(roomNumberString.getText().toString()), id, this);
    }


    public void confirmRoomSelect(View view) {
        //TODO: Set the button in offline mode so one can not create many players.
        EditText userName = findViewById(R.id.userNameInput);
        this.clientPlayer = new Player(userName.getText().toString());
        //TODO add guard for empty input
        ServerCommunication serverCom = new ServerCommunication(this);
        Log.e("name:", userName.getText().toString());
        serverCom.savePlayerToDB(clientPlayer,this);
        //TODO Query server for room existence and if possible add client to ROOM. If successful do a callback to jumpToWaitForClaim
        // TODO if failure, handle failure, error message? response from server error message maybe?
    }

    private void jumpToWaitForClaim() {
        Intent intent = new Intent(this, WaitForPlayersActivity.class);
        intent.putExtra("playerData", this.clientPlayer);
        Log.e("answer ===",this.clientPlayer.toString());
        intent.putExtra("roomData", this.currentRoom);
        startActivity(intent);
    }
}
