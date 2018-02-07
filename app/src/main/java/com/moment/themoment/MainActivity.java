package com.moment.themoment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    public void jumpToResult(View view) {
        Intent intent = new Intent(this, ResultPageActivity.class);
        startActivity(intent);
    }

    public void talkToServer(View view) {
        BackgroundTask talk = new BackgroundTask(this);
        talk.execute();
    }

    public void writeClaimRoom(View view) {
        Intent intent = new Intent(this, WriteClaim.class);
        startActivity(intent);
    }

}
