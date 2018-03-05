package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateRoomActivity extends AppCompatActivity implements CreateRoomCallback{
    private Player clientPlayer;
    private Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        findViewById(R.id.btnCreateRoom).setEnabled(false);
        currentRoom = new Room();
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.createRoom(this);
    }

    /**
     * sets the client players id
     * @param id is the new id of the player
     */
    public void setClientPlayerID(int id) {
        this.clientPlayer.setID(id);
        //Log.i("Server gave:",String.valueOf(id));
        EditText numOfPlayers = findViewById(R.id.roomSize);
        String numOfPlayersString = numOfPlayers.getText().toString();
        int numOfPlayersInt = Integer.parseInt(numOfPlayersString);
        this.currentRoom.setNumOfPlayers(numOfPlayersInt);

        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.updateRoomSize(this.currentRoom, this);
    }

    public void setRoomID(int id) {
        currentRoom.setID(id);
        TextView roomNumber = findViewById(R.id.roomNumber);
        roomNumber.setText(String.valueOf(currentRoom.getID()));
        findViewById(R.id.btnCreateRoom).setEnabled(true);
    }

    public void confirmDone(String response) {
        //Log.i("response on save was!",response);
        Intent intent = new Intent(this, WaitForPlayersActivity.class);
        intent.putExtra("clientPlayer", this.clientPlayer);
        intent.putExtra("roomData", this.currentRoom);
        startActivity(intent);
    }

    /**
     * Will read the input from the editText lines and use this to create a new player
     * and room and after that if will send you to the WaitForPlayerActivity
     * @param view
     */
    public void ConfirmCreateRoom(View view) {
        findViewById(R.id.btnCreateRoom).setEnabled(false);
        EditText userNameToSave = findViewById(R.id.editText);
        EditText sizeOfRoom = findViewById(R.id.roomSize);
        Player roomLeader = new Player(userNameToSave.getText().toString());
        this.clientPlayer = roomLeader;
        currentRoom.addPlayer(roomLeader);
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.savePlayerToDB(roomLeader,currentRoom.getID(),this);
        //TODO change the restrains of the looks
        //TODO Add to notice if a editText is empty and complain about that
    }
}
