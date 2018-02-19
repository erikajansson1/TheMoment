package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class JoinRandomRoomActivity extends AppCompatActivity implements JoinRandomRoomCallback {
    Player clientPlayer;
    Room currentRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_random_room);
        findViewById(R.id.joinButton).setEnabled(false);
        //TODO Guard for if the user uses back button!!
    }

    /**
     * sets the client players id
     * @param id is the new id of the player
     */
    public void setClientPlayerID(int id) {
        clientPlayer.setID(id);
        Toast.makeText(this, "Saved username", Toast.LENGTH_SHORT).show();
        Log.i("Server gave:",String.valueOf(id));
        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.getRandomRoom(this);
    }

    /**
     * sets the room for the player
     * @param room which is to be used by the player
     */
    public void setPlayersRoom(Room room) {
        this.currentRoom = room;
        this.currentRoom.replaceCurrPlayer(this.clientPlayer);

        TextView roomNumber = findViewById(R.id.roomNumber);
        roomNumber.setText(String.valueOf(currentRoom.getID()));
        findViewById(R.id.joinButton).setEnabled(true);
    }

    /**
     * sends the name player has choosen to the server
     * @param view current
     */
    public void addUsername(View view) {
        findViewById(R.id.saveUsername).setEnabled(false);
        EditText userNameToSave = findViewById(R.id.NameInput);
        //TODO gurad for empty input
        Player clientPlayer = new Player(userNameToSave.getText().toString());
        this.clientPlayer = clientPlayer;
        ServerCommunication serverCom = new ServerCommunication(this);
        Log.e("name:",userNameToSave.getText().toString());
        serverCom.savePlayerToDB(clientPlayer,this);
    }

    /**
     * changes intent to WaitForClaim and sends the player object and the roomData
     * @param view
     */
    public void jumpToWaitForClaim(View view) {
        Intent intent = new Intent(this, WaitForClaim.class);
        intent.putExtra("playerData", clientPlayer);
        intent.putExtra("roomData", currentRoom);
        startActivity(intent);
    }


}
