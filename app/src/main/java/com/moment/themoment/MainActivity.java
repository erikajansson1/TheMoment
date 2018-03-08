package com.moment.themoment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MainCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkServerConnection();
    }
    @Override
    public void onBackPressed() {

    }

    /**
     * Locks inteface buttons until its confirmed server and db is up and running.
     */
    private void checkServerConnection() {

        findViewById(R.id.JoinRoom).setEnabled(false);
        findViewById(R.id.JRR).setEnabled(false);
        findViewById(R.id.CreateRoom).setEnabled(false);

        ServerCommunication serverCom = new ServerCommunication(this);
        serverCom.checkConnection(this);
    }

    /**
     * changes view to joinRoom
     * @param view v
     */
    public void joinRoom(View view) {
        Intent intent = new Intent(this, JoinRoomActivity.class);
        startActivity(intent);
    }

    /**
     * changes view to joinRandomRoom
     * @param view v
     */
    public void joinRandomRoom(View view) {
        Intent intent = new Intent(this, JoinRandomRoomActivity.class);
        startActivity(intent);
    }

    /**
     * changes view to createRoom
     * @param view v
     */
    public void createRoom(View view) {
        Intent intent = new Intent(this, CreateRoomActivity.class);
        startActivity(intent);
        //TODO who are we as a player? Never created
        //Room newRoom = new Room();
    }

    //Debug: NOT PERMANENT!
    public void jumpToResult(View view) {
        Room currentRoom = new Room(new Player("TorkelKonkel"));
        currentRoom.setID(58);
        Player clientPlayer = new Player("flufff");
        clientPlayer.setID(72);
        Intent intent = new Intent(this, ResultPageActivity.class);
        intent.putExtra("roomData", currentRoom);
        intent.putExtra("playerData", clientPlayer);
        startActivity(intent);
    }

    /**
     * changes view to writeClaim
     * @param view v
     */
    public void writeClaimRoom(View view) {
        //Creates a player only for test
        Player currentPlayer = new Player(("knasboll"));
        Intent intent = new Intent(this, WriteClaimActivity.class);
        intent.putExtra("PlayerName", currentPlayer);
        startActivity(intent);
    }


    public void voteOnClaimRoom(View view){
        Player currentPlayer = new Player (("Noffff"));
        currentPlayer.setClaim(new Claim("hej", true));
        Room currRoom = new Room ();
        Claim currClaim = new Claim("Solen är gul", true);
        Intent intent = new Intent(this, VoteOnClaimActivity.class);
        intent.putExtra("PlayerName", currentPlayer);
        intent.putExtra("roomData", currRoom);
        intent.putExtra("claimData", currClaim);
        startActivity(intent);
    }

    public void waitForPlayers(View view){
       // Log.i("i funk", "i funk         ");
        Player currentPlayer = new Player (("Noffff"));
        Room currRoom = new Room ();
        currRoom.addPlayer(currentPlayer);
        Intent intent = new Intent(this, WaitForPlayersActivity.class);
        intent.putExtra("clientPlayer", currentPlayer);
        intent.putExtra("roomData", currRoom);
      //  Log.i("skapat", "skapat");
        startActivity(intent);

    }

    public void unlockMenu() {
        findViewById(R.id.JoinRoom).setEnabled(true);
        findViewById(R.id.JRR).setEnabled(true);
        findViewById(R.id.CreateRoom).setEnabled(true);
    }

    public void serverNotReachable() {
        Toast.makeText(this, "Server connection failed", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Reconnecting...", Toast.LENGTH_SHORT).show();
    }
}
