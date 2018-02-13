package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
    }

    /**
     * Will read the input from the editText lines and use this to create a new room
     * after that if will send you to the WaitForPlayerActivity
     * //TODO Add to notice if a editText is empty and complain about that
     * @param view
     */
    public void ConfirmCreateRoom(View view) {
        EditText numOfPlayers = (EditText)findViewById(R.id.editText4);
        String numOfPlay = numOfPlayers.getText().toString();

        EditText userName = (EditText)findViewById(R.id.editText);
        String usrnm = userName.getText().toString();

        Player roomLeader = new Player(usrnm);
        Room room = new Room(roomLeader);

        Intent intent = new Intent(this, WaitForPlayersActivity.class);
        startActivity(intent);
    }
}
