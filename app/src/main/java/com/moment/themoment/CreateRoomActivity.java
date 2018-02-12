package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
    }

    public void ConfirmCreateRoom(View view) {
        Intent intent = new Intent(this, WaitForPlayersActivity.class);
        startActivity(intent);
    }

    public void CreateRoom(){
        //TODO grab information from the input to create the room
        //TODO create a room connected to the player
        return;
    }

}
