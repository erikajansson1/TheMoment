package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServerCommunication serverCom = new ServerCommunication();
        checkServerConnection(serverCom);
    }

    private void checkServerConnection(ServerCommunication serverCom) {
        Button JoinRoomBtn = findViewById(R.id.JoinRoom);
        Button JRRBtn = findViewById(R.id.JRR);
        Button CreateRoomBtn = findViewById(R.id.CreateRoom);

        JoinRoomBtn.setEnabled(false);
        JRRBtn.setEnabled(false);
        CreateRoomBtn.setEnabled(false);

        //while not serverCom.checkConnection()
    }

    public void joinRoom(View view) {
        Intent intent = new Intent(this, JoinRoomActivity.class);
        startActivity(intent);
    }

    public void joinRandomRoom(View view) {
        Intent intent = new Intent(this, JoinRandomRoomActivity.class);
        startActivity(intent);
    }

    public void createRoom(View view) {
        Intent intent = new Intent(this, CreateRoomActivity.class);
        startActivity(intent);
        //TODO who are we as a player? Never created
        //Room newRoom = new Room();
    }

    //Debug: NOT PERMANENT!
    public void jumpToResult(View view) {
        Intent intent = new Intent(this, ResultPageActivity.class);
        startActivity(intent);
    }

    public void writeClaimRoom(View view) {
        Intent intent = new Intent(this, WriteClaim.class);
        startActivity(intent);
    }

}
