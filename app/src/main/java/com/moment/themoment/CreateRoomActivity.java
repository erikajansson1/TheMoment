package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreateRoomActivity extends AppCompatActivity implements CreateRoomCallback{
    private Player clientPlayer;
    private Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.createRoom(this);
    }

    /**
     * sets the client players id
     * @param id is the new id of the player
     */
    public void setClientPlayerID(int id) {
        clientPlayer.setID(id);
        //Toast.makeText(this, "Saved username", Toast.LENGTH_SHORT).show();
        Log.i("Server gave:",String.valueOf(id));

        EditText numOfPlayers = (EditText)findViewById(R.id.editText4);
        String numOfPlayersString = numOfPlayers.getText().toString();
        int numOfPlayersInt = Integer.parseInt(numOfPlayersString);
        this.currentRoom.setNumOfPlayers(numOfPlayersInt);

        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.updateRoom(this.currentRoom, this);
        return;
    }

    /**
     * sets the room for the player
     * @param room which is to be used by the player
     */
    public void setPlayersRoom(Room room) {
        if (this.currentRoom == null) {
            this.currentRoom = room;
            TextView roomNumber = findViewById(R.id.roomNumber);
            roomNumber.setText(String.valueOf(currentRoom.getID()));
        }else{
            this.currentRoom = room;
        }
    }

    /**
     * Will read the input from the editText lines and use this to create a new player
     * and room and after that if will send you to the WaitForPlayerActivity
     * @param view
     */
    public void ConfirmCreateRoom(View view) {
        findViewById(R.id.editText).setEnabled(false);
        EditText userNameToSave = findViewById(R.id.editText);
        Player roomLeader = new Player(userNameToSave.getText().toString());
        this.clientPlayer = roomLeader;
        ServerCommunication serverCom = new ServerCommunication(this);
        Log.e("name:",userNameToSave.getText().toString());
        serverCom.savePlayerToDBCreateRoom(roomLeader,this);
        //TODO change the restrains of the looks
        //TODO Add to notice if a editText is empty and complain about that

        Intent intent = new Intent(this, WaitForPlayersActivity.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }
}
